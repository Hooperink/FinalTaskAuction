package epam.by.Auction.command;

import epam.by.Auction.constants.ConstantForCommands;
import epam.by.Auction.entity.Bet;
import epam.by.Auction.service.BetService;
import epam.by.Auction.service.LotService;
import epam.by.Auction.service.UserService;
import epam.by.Auction.entity.Lot;
import epam.by.Auction.entity.LotStatus;
import epam.by.Auction.entity.User;
import epam.by.Auction.exception.DaoException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class GetBalanceInfoCommand implements Command {
    private UserService userService;

    public GetBalanceInfoCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        HttpSession session = request.getSession();
        long userId = (long)session.getAttribute(ConstantForCommands.ID);
        Optional<User> optionalUser = userService.findById(userId);
        optionalUser.ifPresent(user -> request.setAttribute(ConstantForCommands.USER, user));
        return CommandResult.forward("/jsp/user/accountBalanceInfo.jsp");
    }
}
