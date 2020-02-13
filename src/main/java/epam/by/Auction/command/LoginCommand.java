package epam.by.Auction.command;

import epam.by.Auction.constants.ConstantForCommands;
import epam.by.Auction.service.UserService;
import epam.by.Auction.entity.User;
import epam.by.Auction.exception.DaoException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Optional;

public class LoginCommand implements Command {

    private UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        String login = request.getParameter(ConstantForCommands.LOGIN);
        String password = request.getParameter(ConstantForCommands.PASSWORD);
        Optional<User> optionalUser = userService.login(login, password);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getIsActive()) {
                HttpSession session = request.getSession();
                session.setAttribute(ConstantForCommands.ROLE, user.getRole());
                session.setAttribute(ConstantForCommands.ID, user.getId());
                session.setAttribute(ConstantForCommands.IS_ACTIVE, user.getIsActive());
                Locale locale = request.getLocale();
                if ("be".equals(locale.getLanguage())) {
                    session.setAttribute(ConstantForCommands.LANG, "be_BY");
                } else if("en".equals(locale.getLanguage())) {
                    session.setAttribute(ConstantForCommands.LANG, "en_US");
                } else {
                    session.setAttribute(ConstantForCommands.LANG, "ru_Ru");
                }
                return CommandResult.redirect("?command=showMainPage");
            }
        }
        return CommandResult.redirect("?command=showLoginPage");
    }
}
