package by.epam.auction.command;

import by.epam.auction.service.LotService;
import by.epam.auction.utils.StringValidator;
import by.epam.auction.constants.ConstantForCommands;
import by.epam.auction.exception.DaoException;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.math.BigDecimal;

public class CreateLotCommand implements Command {

    private LotService lotService;
    private StringValidator stringValidator;

    public CreateLotCommand(LotService lotService, StringValidator stringValidator) {
        this.lotService = lotService;
        this.stringValidator = stringValidator;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DaoException, IOException, ServletException {
        String name = request.getParameter(ConstantForCommands.NAME);
        String description = request.getParameter(ConstantForCommands.DESCRIPTION);
        String strPrice = request.getParameter(ConstantForCommands.PRICE);
        Part part = request.getPart("filecover");
        BigDecimal price;
        if (NumberUtils.isCreatable(strPrice) && stringValidator.validateName(name) && stringValidator.validateText(description)) {
            price = new BigDecimal(strPrice);
            HttpSession session = request.getSession();
            long userId = (long) session.getAttribute(ConstantForCommands.ID);
            lotService.save(name, description, price, userId, part);
            return CommandResult.redirect("?command=showSuccessCreatePage");
        } else {
            return CommandResult.redirect("?command=showCreateLotPage");
        }
    }
}
