package epam.by.Auction.command;


import epam.by.Auction.constants.ConstantForCommands;
import epam.by.Auction.entity.LotStatus;
import epam.by.Auction.service.LotService;
import epam.by.Auction.exception.DaoException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditLotCommand implements Command {

    private LotService lotService;

    public EditLotCommand(LotService lotService) {
        this.lotService = lotService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        String lotIdStr = request.getParameter(ConstantForCommands.LOT_ID);
        long lotId = Long.parseLong(lotIdStr);
        String name = request.getParameter(ConstantForCommands.NAME);
        String description = request.getParameter(ConstantForCommands.DESCRIPTION);
        String lotStatusString = request.getParameter(ConstantForCommands.LOT_STATUS);
        if (lotStatusString != null){
            if (lotStatusString.equalsIgnoreCase(LotStatus.SOLD.getStatus())){
                lotService.sellLot(lotId, name, description, lotStatusString);
            } else {
                lotService.edit(lotId, name, description, lotStatusString);
            }
        } else {
            lotService.edit(lotId, name, description);
        }
        return CommandResult.redirect("?command=getSingleLot&id=" + lotIdStr);
    }
}
