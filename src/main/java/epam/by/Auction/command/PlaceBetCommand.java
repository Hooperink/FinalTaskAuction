package epam.by.Auction.command;

import epam.by.Auction.constants.ConstantForBetService;
import epam.by.Auction.service.BetService;
import epam.by.Auction.exception.DaoException;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

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
            message = ConstantForBetService.NO_BET_SET;
        }
        return CommandResult.redirect("r?command=getSingleLot&id=" + lotIdString +"&message=" + message);
    }
}
