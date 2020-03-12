package by.epam.auction.command;

import by.epam.auction.service.UserService;
import by.epam.auction.constants.ConstantForCommands;
import by.epam.auction.exception.DaoException;
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
        long id = (long) session.getAttribute(ConstantForCommands.ID);
        String updateAmount = request.getParameter(ConstantForCommands.AMOUNT);
        if (NumberUtils.isCreatable(updateAmount)){
            BigDecimal amountToAddToBalance = new BigDecimal(updateAmount);
            if (amountToAddToBalance.compareTo(BigDecimal.ZERO) > 0){
                userService.updateAccount(id, amountToAddToBalance);
            }
        }
        return CommandResult.redirect("?command=showMainPage");
    }
}
