package by.epam.auction.command;

import by.epam.auction.service.LotService;
import by.epam.auction.constants.ConstantForCommands;
import by.epam.auction.dto.Lot;
import by.epam.auction.exception.DaoException;

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
        long id = (long) session.getAttribute(ConstantForCommands.ID);
        List<Lot> lotList = lotService.getAllUserLotsById(id);
        request.setAttribute(ConstantForCommands.LOTS, lotList);
        return CommandResult.forward("/jsp/user/mainPage.jsp");
    }
}
