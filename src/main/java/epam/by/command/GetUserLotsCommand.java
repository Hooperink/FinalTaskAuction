package epam.by.command;

import epam.by.entity.Lot;
import epam.by.exception.DaoException;
import epam.by.exception.ServiceException;
import epam.by.service.LotService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class GetUserLotsCommand implements Command {

    private LotService lotService;

    public GetUserLotsCommand(LotService lotService) {
        this.lotService = lotService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        HttpSession session = request.getSession();
        long id = (long) session.getAttribute("id");
        List<Lot> lotList = lotService.getAllUserLotsById(id);
        request.setAttribute("lots", lotList);
        return CommandResult.forward("/jsp/user/mainPage.jsp");
    }
}
