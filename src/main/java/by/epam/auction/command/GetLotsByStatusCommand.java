package by.epam.auction.command;

import by.epam.auction.constants.ConstantForCommands;
import by.epam.auction.dto.Lot;
import by.epam.auction.dto.enums.LotStatus;
import by.epam.auction.service.LotService;
import by.epam.auction.exception.DaoException;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetLotsByStatusCommand implements Command {

    private LotService lotService;

    public GetLotsByStatusCommand(LotService lotService) {
        this.lotService = lotService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        String status = request.getParameter(ConstantForCommands.STATUS);
        String pageString = request.getParameter(ConstantForCommands.PAGE);
        int page =  NumberUtils.isCreatable(pageString) ? Integer.parseInt(pageString) - 1 : 0;
        int recordPerPage = 8;
        LotStatus lotStatus = LotStatus.valueOf(status);
        int amountOfRows = lotService.getAmountOfRows(lotStatus);
        int amountOfPages = (int) Math.ceil(amountOfRows * 1.0 / recordPerPage);
        List<Lot> lotsList  = lotService.getLotByStatus(lotStatus, recordPerPage, recordPerPage * page);
        request.setAttribute(ConstantForCommands.STATUS, status);
        request.setAttribute(ConstantForCommands.LOTS, lotsList);
        request.setAttribute(ConstantForCommands.AMOUNT_OF_PAGES, amountOfPages);
        request.setAttribute(ConstantForCommands.CURRENT_PAGE, page + 1);
        return CommandResult.forward("/jsp/admin/forListAdminPage.jsp");
    }
}
