package by.epam.auction.command;

import by.epam.auction.service.UserService;
import by.epam.auction.constants.ConstantForCommands;
import by.epam.auction.dto.User;
import by.epam.auction.exception.DaoException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
