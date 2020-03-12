package by.epam.auction.command;

import by.epam.auction.service.UserService;
import by.epam.auction.constants.ConstantForCommands;
import by.epam.auction.dto.User;
import by.epam.auction.exception.DaoException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetUserListCommand implements Command {

    private UserService userService;

    public GetUserListCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
            List<User> users = userService.getAllUsers();
            request.setAttribute(ConstantForCommands.USERS, users);
            return CommandResult.forward("/jsp/admin/usersMenuList.jsp");
    }
}
