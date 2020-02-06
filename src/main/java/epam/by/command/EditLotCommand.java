package epam.by.command;


import epam.by.entity.LotStatus;
import epam.by.exception.DaoException;
import epam.by.exception.ServiceException;
import epam.by.service.LotService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditLotCommand implements Command {

    private LotService lotService;

    public EditLotCommand(LotService lotService) {
        this.lotService = lotService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        String lotIdStr = request.getParameter("lotId");
        long lotId = Long.parseLong(lotIdStr);
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String lotStatusString = request.getParameter("lotStatus");
        if (lotStatusString != null){
            if (lotStatusString.equalsIgnoreCase(LotStatus.SOLD.getStatus())){
                lotService.sellLot(lotId, name, description, lotStatusString);
            } else {
                lotService.edit(lotId, name, description, lotStatusString);
            }
        } else {
            lotService.edit(lotId, name, description);
        }
        return CommandResult.redirect("controller?command=getSingleLot&id=" + lotIdStr);
    }
}
