package epam.by.Auction.command;

import epam.by.Auction.constants.ConstantForCommands;
import epam.by.Auction.entity.UserRole;
import epam.by.Auction.exception.DaoException;
import epam.by.Auction.service.LotService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteLotCommand implements Command {
    private LotService lotService;

    public DeleteLotCommand(LotService lotService) {
        this.lotService = lotService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        HttpSession session = request.getSession();
        UserRole role = (UserRole) session.getAttribute(ConstantForCommands.ROLE);
        if (role == UserRole.ADMIN) {
            String lotId = request.getParameter(ConstantForCommands.ID);
            long id = Long.parseLong(lotId);
            lotService.remove(id);
            return CommandResult.forward("/jsp/user/mainPageForward.jsp");
        }
        return CommandResult.redirect("?command=showMainPage");
    }
}
