package epam.by.command;

import epam.by.entity.UserRole;
import epam.by.exception.DaoException;
import epam.by.exception.ServiceException;
import epam.by.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangeActivityCommand implements Command {

    private UserService userService;

    public ChangeActivityCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        String userIdString = request.getParameter("id");
        long id = Long.parseLong(userIdString);
        userService.changeActivity(id);
        return CommandResult.redirect("controller?command=getUsers");
    }
}
