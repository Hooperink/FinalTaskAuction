package epam.by.command;

import epam.by.entity.UserRole;
import epam.by.exception.DaoException;
import epam.by.exception.ServiceException;
import epam.by.service.LotService;

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
        UserRole role = (UserRole) session.getAttribute("role");
        if (role == UserRole.ADMIN) {
            String lotId = request.getParameter("id");
            long id = Long.parseLong(lotId);
            lotService.remove(id);
            return CommandResult.forward("/jsp/user/mainPageForward.jsp");
        }
        return CommandResult.redirect("controller?command=showMainPage");
    }
}
