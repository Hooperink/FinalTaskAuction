package by.epam.auction.command;

import by.epam.auction.service.UserService;
import by.epam.auction.constants.ConstantForCommands;
import by.epam.auction.exception.DaoException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeActivityCommand implements Command {

    private UserService userService;

    public ChangeActivityCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        String userIdString = request.getParameter(ConstantForCommands.ID);
        long id = Long.parseLong(userIdString);
        userService.changeActivity(id);
        return CommandResult.redirect("?command=getUsers");
    }
}
