package epam.by.Auction.command;

import epam.by.Auction.constants.ConstantForCommands;
import epam.by.Auction.errors.ErrorCodeForMessage;
import epam.by.Auction.exception.MessageErrorException;
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
        String lotIdString = request.getParameter(ConstantForCommands.LOT_ID);
        long lotId = Long.parseLong(lotIdString.trim());
        HttpSession session = request.getSession();
        long newBidderUserId = (long) session.getAttribute(ConstantForCommands.ID);
        String bidderBetString = request.getParameter(ConstantForCommands.BET_AMOUNT);
        String message = null;
        String error = null;
        if (NumberUtils.isCreatable(bidderBetString)) {
            BigDecimal newBidderBet = new BigDecimal(bidderBetString);
            try {
                if (betService.placeBet(lotId, newBidderUserId, newBidderBet)) {
                    message = "Success";
                } else {
                    error = ErrorCodeForMessage.ERROR_6.name();
                }
            } catch (MessageErrorException e) {
                error = e.getMessage();
            }
        } else {
            error = ErrorCodeForMessage.ERROR_7.name();
        }
        if (error != null) {
            return CommandResult.redirect("?command=getSingleLot&id=" + lotIdString +"&error=" + error);
        } else {
            return CommandResult.redirect("?command=getSingleLot&id=" + lotIdString +"&message=" + message);
        }
    }
}
