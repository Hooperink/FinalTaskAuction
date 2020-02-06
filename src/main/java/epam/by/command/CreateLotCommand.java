package epam.by.command;

import epam.by.exception.DaoException;
import epam.by.exception.ServiceException;
import epam.by.service.LotService;

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
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String strPrice = request.getParameter("price");
        BigDecimal price = new BigDecimal(strPrice);
        HttpSession session = request.getSession();
        long userId = (long) session.getAttribute("id");
        lotService.save(name, description, price, userId);
        return CommandResult.redirect("controller?command=showSuccessCreatePage");
    }
}
