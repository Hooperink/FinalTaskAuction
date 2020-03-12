package by.epam.auction.command;

import by.epam.auction.constants.ConstantForCommands;
import by.epam.auction.dto.enums.UserRole;
import by.epam.auction.exception.DaoException;
import by.epam.auction.service.LotService;

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
