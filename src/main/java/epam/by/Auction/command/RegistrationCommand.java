package epam.by.Auction.command;

import epam.by.Auction.errors.ErrorCodeForMessage;
import epam.by.Auction.exception.DaoException;
import epam.by.Auction.exception.MessageErrorException;
import epam.by.Auction.service.UserService;
import epam.by.Auction.utils.PasswordEncryptor;
import epam.by.Auction.utils.StringValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationCommand implements Command {

    private UserService userService;
    private PasswordEncryptor passwordEncryptor;
    private StringValidator stringValidator;

    public RegistrationCommand(UserService userService, PasswordEncryptor passwordEncryptor, StringValidator stringValidator) {
        this.userService = userService;
        this.passwordEncryptor = passwordEncryptor;
        this.stringValidator = stringValidator;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repeatPassword");
        if (!password.equals(repeatPassword)) {
            request.setAttribute("errorMessage", ErrorCodeForMessage.ERROR_9.name());
            return CommandResult.forward("/jsp/user/registrationForm.jsp");
        } else if (!stringValidator.validateLogin(login)) {
            request.setAttribute("errorMessage", ErrorCodeForMessage.ERROR_1.name());
            return CommandResult.forward("/jsp/user/registrationForm.jsp");
        }
        try {
           userService.registration(login, password, passwordEncryptor);
        } catch (MessageErrorException e) {
            String errorMessage = e.getMessage();
            request.setAttribute("errorMessage", errorMessage);
            return CommandResult.forward("/jsp/user/registrationForm.jsp");
        }
        return CommandResult.forward("/jsp/login.jsp");
    }
}
