package epam.by.Auction.command;

import epam.by.Auction.constants.ConstantForCommands;
import epam.by.Auction.service.LotService;
import epam.by.Auction.entity.Bet;
import epam.by.Auction.entity.Lot;
import epam.by.Auction.entity.LotStatus;
import epam.by.Auction.exception.DaoException;
import epam.by.Auction.service.BetService;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetListLotCommand implements Command {

    public static final int RECORDS_PER_PAGE = 8;
    private LotService lotService;
    private BetService betService;

    public GetListLotCommand(LotService lotService, BetService betService) {
        this.lotService = lotService;
        this.betService = betService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        String pageString = request.getParameter(ConstantForCommands.PAGE);
        int page =  NumberUtils.isCreatable(pageString) ? Integer.parseInt(pageString) - 1 : 0;
        List<Lot> lotList = lotService.getLotByStatus(LotStatus.ACTIVE, RECORDS_PER_PAGE, page * RECORDS_PER_PAGE);
        List<Bet> betList = betService.getAllBets();
        int amountOfRows = lotService.getAmountOfRows(LotStatus.ACTIVE);
        int amountOfPages = (int) Math.ceil(amountOfRows * 1.0 / RECORDS_PER_PAGE);
        request.setAttribute(ConstantForCommands.LOTS, lotList);
        request.setAttribute(ConstantForCommands.BETS, betList);
        request.setAttribute(ConstantForCommands.AMOUNT_OF_PAGES, amountOfPages);
        request.setAttribute(ConstantForCommands.CURRENT_PAGE, page + 1);
        return CommandResult.forward("/jsp/user/mainPage.jsp");
    }
}
