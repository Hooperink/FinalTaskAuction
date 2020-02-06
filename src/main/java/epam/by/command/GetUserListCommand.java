package epam.by.command;

import epam.by.entity.User;
import epam.by.entity.UserRole;
import epam.by.exception.DaoException;
import epam.by.exception.ServiceException;
import epam.by.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
