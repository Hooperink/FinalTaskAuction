package epam.by.Auction.command;

import epam.by.Auction.constants.ConstantForCommands;
import epam.by.Auction.entity.Bet;
import epam.by.Auction.entity.Lot;
import epam.by.Auction.entity.User;
import epam.by.Auction.entity.UserRole;
import epam.by.Auction.service.BetService;
import epam.by.Auction.service.LotService;
import epam.by.Auction.service.UserService;
import epam.by.Auction.exception.DaoException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class GetSingleLotCommand implements Command {

    private LotService lotService;
    private BetService betService;
    private UserService userService;

    public GetSingleLotCommand(LotService lotService, BetService betService, UserService userService) {
        this.lotService = lotService;
        this.betService = betService;
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        HttpSession session = request.getSession();
        UserRole role = (UserRole) session.getAttribute(ConstantForCommands.ROLE);
        String lotIdString = request.getParameter(ConstantForCommands.ID);
        String message = request.getParameter(ConstantForCommands.MESSAGE);
        request.setAttribute(ConstantForCommands.MESSAGE, message);
        long lotId = Long.parseLong(lotIdString.trim());
        Optional<Lot> lot = lotService.findLotById(lotId);
        Optional<Bet> bet = betService.findBetByLotId(lotId);
        if (bet.isPresent()) {
            request.setAttribute(ConstantForCommands.BET, bet.get());
            Optional<User> bidder = userService.findById(bet.get().getBuyerId());
            bidder.ifPresent(user -> request.setAttribute(ConstantForCommands.BIDDER, user));
        }
        lot.ifPresent(x -> request.setAttribute(ConstantForCommands.LOT, x));
        if (role == UserRole.ADMIN) {
            return CommandResult.forward("/jsp/admin/singleLotPageAdminEdit.jsp");
        }
        return CommandResult.forward("/jsp/user/singleLotPage.jsp");
    }
}