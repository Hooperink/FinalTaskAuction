package epam.by.command;

import epam.by.entity.Bet;
import epam.by.entity.Lot;
import epam.by.entity.User;
import epam.by.entity.UserRole;
import epam.by.exception.DaoException;
import epam.by.exception.ServiceException;
import epam.by.service.BetService;
import epam.by.service.LotService;
import epam.by.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class GetSingleLotCommand implements Command {

    private LotService lotService;
    private BetService betService;
    private UserService userService;

    public GetSingleLotCommand(LotService lotService, BetService betService, UserService userService) {
        this.lotService = lotService;
        this.betService = betService;
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        HttpSession session = request.getSession();
        UserRole role = (UserRole) session.getAttribute("role");
        String lotIdString = request.getParameter("id");
        String message = request.getParameter("message");
        request.setAttribute("message", message);
        long lotId = Long.parseLong(lotIdString.trim());
        Optional<Lot> lot = lotService.findLotById(lotId);
        Optional<Bet> bet = betService.findBetByLotId(lotId);
        if (bet.isPresent()) {
            request.setAttribute("bet", bet.get());
            Optional<User> bidder = userService.findById(bet.get().getBuyerId());
            bidder.ifPresent(user -> request.setAttribute("bidder", user));
        }
        lot.ifPresent(x -> request.setAttribute("lot", x));
        if (role == UserRole.ADMIN) {
            return CommandResult.forward("/jsp/admin/singleLotPageAdminEdit.jsp");
        }
        return CommandResult.forward("/jsp/user/singleLotPage.jsp");
    }
}