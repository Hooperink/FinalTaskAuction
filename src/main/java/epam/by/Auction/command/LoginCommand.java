package epam.by.Auction.command;

import epam.by.Auction.constants.ConstantForCommands;
import epam.by.Auction.service.UserService;
import epam.by.Auction.dto.User;
import epam.by.Auction.exception.DaoException;
import epam.by.Auction.utils.PasswordEncryptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Optional;

import static epam.by.Auction.errors.ErrorCodeForMessage.ERROR_1;

public class LoginCommand implements Command {

    private UserService userService;
    private PasswordEncryptor encryptor;

    public LoginCommand(UserService userService, PasswordEncryptor encryptor) {
        this.userService = userService;
        this.encryptor = encryptor;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        String login = request.getParameter(ConstantForCommands.LOGIN);
        String password = request.getParameter(ConstantForCommands.PASSWORD);
        String enryptedPassword = encryptor.md5Custom(password);
        Optional<User> optionalUser = userService.login(login, enryptedPassword);
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
                } else if("ru".equals(locale.getLanguage())) {
                    session.setAttribute(ConstantForCommands.LANG, "ru_RU");
                } else {
                    session.setAttribute(ConstantForCommands.LANG, "en_US");
                }
                return CommandResult.redirect("?command=showMainPage");
            }
        }
        request.setAttribute("errorMessage", ERROR_1.name());
        return CommandResult.forward("/jsp/login.jsp");
    }
}
