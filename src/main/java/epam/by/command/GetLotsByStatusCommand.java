package epam.by.command;

import epam.by.entity.Lot;
import epam.by.entity.LotStatus;
import epam.by.entity.UserRole;
import epam.by.exception.DaoException;
import epam.by.exception.ServiceException;
import epam.by.service.LotService;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.print.Pageable;
import java.util.List;

public class GetLotsByStatusCommand implements Command {

    private LotService lotService;

    public GetLotsByStatusCommand(LotService lotService) {
        this.lotService = lotService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        String status = request.getParameter("status");
        String pageString = request.getParameter("page");
        int page =  NumberUtils.isCreatable(pageString) ? Integer.parseInt(pageString) - 1 : 0;
        int recordPerPage = 8;
        LotStatus lotStatus = LotStatus.valueOf(status);
        int amountOfRows = lotService.getAmountOfRows(lotStatus);
        int amountOfPages = (int) Math.ceil(amountOfRows * 1.0 / recordPerPage);
        List<Lot> lotsList  = lotService.getLotByStatus(lotStatus, recordPerPage, recordPerPage * page);
        request.setAttribute("status", status);
        request.setAttribute("lots", lotsList);
        request.setAttribute("amountOfPages", amountOfPages);
        request.setAttribute("currentPage", page + 1);
        return CommandResult.forward("/jsp/admin/forListAdminPage.jsp");
    }
}
