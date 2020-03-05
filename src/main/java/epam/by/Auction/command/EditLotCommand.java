package epam.by.Auction.command;


import epam.by.Auction.constants.ConstantForCommands;
import epam.by.Auction.dto.LotStatus;
import epam.by.Auction.service.LotService;
import epam.by.Auction.exception.DaoException;
import epam.by.Auction.utils.StringValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditLotCommand implements Command {

    private LotService lotService;
    private StringValidator stringValidator;

    public EditLotCommand(LotService lotService, StringValidator stringValidator) {
        this.lotService = lotService;
        this.stringValidator = stringValidator;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        String lotIdStr = request.getParameter(ConstantForCommands.LOT_ID);
        long lotId = Long.parseLong(lotIdStr);
        String name = request.getParameter(ConstantForCommands.NAME);
        String description = request.getParameter(ConstantForCommands.DESCRIPTION);
        String lotStatusString = request.getParameter(ConstantForCommands.LOT_STATUS);
        if (stringValidator.validateName(name) && stringValidator.validateText(description)) {
            if (lotStatusString != null) {
                if (lotStatusString.equalsIgnoreCase(LotStatus.SOLD.getStatus())) {
                    lotService.sellLot(lotId, name, description, lotStatusString);
                } else {
                    lotService.edit(lotId, name, description, lotStatusString);
                }
            } else {
                lotService.edit(lotId, name, description);
            }
        }
        return CommandResult.redirect("?command=getSingleLot&id=" + lotIdStr);
    }
}
