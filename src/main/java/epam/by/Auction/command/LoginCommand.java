package epam.by.Auction.command;

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
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        Optional<User> optionalUser = userService.login(login, password);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getIsActive()) {
                HttpSession session = request.getSession();
                session.setAttribute("role", user.getRole());
                session.setAttribute("id", user.getId());
                session.setAttribute("isActive", user.getIsActive());
                Locale locale = request.getLocale();
                if ("be".equals(locale.getLanguage())) {
                    session.setAttribute("lang", "be_BY");
                } else if("en".equals(locale.getLanguage())) {
                    session.setAttribute("lang", "en_US");
                } else {
                    session.setAttribute("lang", "ru_Ru");
                }
                return CommandResult.redirect("?command=showMainPage");
            }
        }
        return CommandResult.redirect("?command=showLoginPage");
    }
}
