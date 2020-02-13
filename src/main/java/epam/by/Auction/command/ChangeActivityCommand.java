package epam.by.Auction.command;

import epam.by.Auction.service.UserService;
import epam.by.Auction.exception.DaoException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        return CommandResult.redirect("?command=getUsers");
    }
}
