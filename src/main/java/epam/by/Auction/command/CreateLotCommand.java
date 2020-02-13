package epam.by.Auction.command;

import epam.by.Auction.constants.ConstantForCommands;
import epam.by.Auction.service.LotService;
import epam.by.Auction.exception.DaoException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

public class CreateLotCommand implements Command {

    private LotService lotService;

    public CreateLotCommand(LotService lotService) {
        this.lotService = lotService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        String name = request.getParameter(ConstantForCommands.NAME);
        String description = request.getParameter(ConstantForCommands.DESCRIPTION);
        String strPrice = request.getParameter(ConstantForCommands.PRICE);
        BigDecimal price = new BigDecimal(strPrice);
        HttpSession session = request.getSession();
        long userId = (long) session.getAttribute(ConstantForCommands.ID);
        lotService.save(name, description, price, userId);
        return CommandResult.redirect("?command=showSuccessCreatePage");
    }
}
