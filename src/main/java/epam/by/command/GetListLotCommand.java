package epam.by.command;

import epam.by.entity.Bet;
import epam.by.entity.Lot;
import epam.by.entity.LotStatus;
import epam.by.exception.DaoException;
import epam.by.service.BetService;
import epam.by.service.LotService;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetListLotCommand implements Command {

    private LotService lotService;
    private BetService betService;

    public GetListLotCommand(LotService lotService, BetService betService) {
        this.lotService = lotService;
        this.betService = betService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        String pageString = request.getParameter("page");
        int page =  NumberUtils.isCreatable(pageString) ? Integer.parseInt(pageString) - 1 : 0;
        int recordPerPage = 8;
        List<Lot> lotList = lotService.getLotByStatus(LotStatus.ACTIVE, recordPerPage, page * recordPerPage);
        List<Bet> betList = betService.getAllBets();
        int amountOfRows = lotService.getAmountOfRows(LotStatus.ACTIVE);
        int amountOfPages = (int) Math.ceil(amountOfRows * 1.0 / recordPerPage);
        request.setAttribute("lots", lotList);
        request.setAttribute("bets", betList);
        request.setAttribute("amountOfPages", amountOfPages);
        request.setAttribute("currentPage", page + 1);
        return CommandResult.forward("/jsp/user/mainPage.jsp");
    }
}
