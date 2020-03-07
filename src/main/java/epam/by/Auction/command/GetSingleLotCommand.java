package epam.by.Auction.command;

import epam.by.Auction.constants.ConstantForCommands;
import epam.by.Auction.dto.Bet;
import epam.by.Auction.dto.Lot;
import epam.by.Auction.dto.User;
import epam.by.Auction.dto.enums.UserRole;
import epam.by.Auction.exception.PageNotFoundException;
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
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DaoException, PageNotFoundException {
        HttpSession session = request.getSession();
        UserRole role = (UserRole) session.getAttribute(ConstantForCommands.ROLE);
        String lotIdString = request.getParameter(ConstantForCommands.ID);

        //Message for result output of setting bet
        String message = request.getParameter(ConstantForCommands.MESSAGE);
        String error = request.getParameter(ConstantForCommands.ERROR);
        if (message != null) {
            request.setAttribute(ConstantForCommands.MESSAGE, message);
        } else if (error != null) {
            request.setAttribute(ConstantForCommands.ERROR, error);
        }

        request.setAttribute(ConstantForCommands.MESSAGE, message);
        long lotId = Long.parseLong(lotIdString.trim());
        Optional<Lot> lot = lotService.findLotById(lotId);
        Optional<Bet> bet = betService.findBetByLotId(lotId);
        if (bet.isPresent()) {
            request.setAttribute(ConstantForCommands.BET, bet.get());
            Optional<User> bidder = userService.findById(bet.get().getBuyerId());
            bidder.ifPresent(user -> request.setAttribute(ConstantForCommands.BIDDER, user));
        }
        if (lot.isPresent()) {
            request.setAttribute(ConstantForCommands.LOT, lot.get());
        } else {
            throw new PageNotFoundException();
        }
        if (role == UserRole.ADMIN) {
            return CommandResult.forward("/jsp/admin/singleLotPageAdminEdit.jsp");
        }
        return CommandResult.forward("/jsp/user/singleLotPage.jsp");
    }
}