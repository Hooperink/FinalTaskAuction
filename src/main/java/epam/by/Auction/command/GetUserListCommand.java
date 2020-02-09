package epam.by.Auction.command;

import epam.by.Auction.service.UserService;
import epam.by.Auction.entity.User;
import epam.by.Auction.exception.DaoException;

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
            request.setAttribute("users", users);
            return CommandResult.forward("/jsp/admin/usersMenuList.jsp");
    }
}
