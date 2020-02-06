package epam.by.command;

import epam.by.exception.DaoException;
import epam.by.exception.ServiceException;
import epam.by.service.BetService;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

import static epam.by.constants.ConstantForBetService.NO_BET_SET;

public class PlaceBetCommand implements Command {
    private BetService betService;

    public PlaceBetCommand(BetService betService) {
        this.betService = betService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        String lotIdString = request.getParameter("lotId");
        long lotId = Long.parseLong(lotIdString.trim());
        HttpSession session = request.getSession();
        long newBidderUserId = (long) session.getAttribute("id");
        String bidderBetString = request.getParameter("betAmount");
        String message;
        if (NumberUtils.isCreatable(bidderBetString)) {
            BigDecimal newBidderBet = new BigDecimal(bidderBetString);
            message = betService.placeBet(lotId, newBidderUserId, newBidderBet);
        } else {
            message = NO_BET_SET;
        }
        return CommandResult.redirect("controller?command=getSingleLot&id=" + lotIdString +"&message=" + message);
    }
}
