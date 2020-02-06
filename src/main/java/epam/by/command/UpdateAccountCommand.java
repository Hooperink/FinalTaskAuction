package epam.by.command;

import epam.by.exception.DaoException;
import epam.by.exception.ServiceException;
import epam.by.service.UserService;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

public class UpdateAccountCommand implements Command {

    private UserService userService;

    public UpdateAccountCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        HttpSession session = request.getSession();
        long id = (long) session.getAttribute("id");
        String updateAmount = request.getParameter("amount");
        if (NumberUtils.isCreatable(updateAmount)){
            BigDecimal amountToAddToBalance = new BigDecimal(updateAmount);
            if (amountToAddToBalance.compareTo(BigDecimal.ZERO) > 0){
                userService.updateAccount(id, amountToAddToBalance);
            }
        }
        return CommandResult.redirect("controller?command=showMainPage");
    }
}
