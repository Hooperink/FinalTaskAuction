package epam.by.command;

import epam.by.entity.Bet;
import epam.by.entity.Lot;
import epam.by.entity.LotStatus;
import epam.by.entity.User;
import epam.by.exception.DaoException;
import epam.by.exception.ServiceException;
import epam.by.service.BetService;
import epam.by.service.LotService;
import epam.by.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class GetAccountInfoCommand implements Command {
    private UserService userService;
    private LotService lotService;
    private BetService betService;

    public GetAccountInfoCommand(UserService userService, LotService lotService, BetService betService) {
        this.userService = userService;
        this.lotService = lotService;
        this.betService = betService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws DaoException {
        HttpSession session = request.getSession();
        long userId = (long)session.getAttribute("id");
        String status = request.getParameter("status");
        LotStatus lotStatus = status == null ? LotStatus.ACTIVE: LotStatus.valueOf(status);
        List<Lot> lots = lotService.getLotsByStatusAndUserId(userId, lotStatus);
        Optional<User> optionalUser = userService.findById(userId);
        List<Bet> bets = betService.getAllUserBetsByUserIdAndLotStatus(userId, lotStatus);
        optionalUser.ifPresent(user -> request.setAttribute("user", user));
        request.setAttribute("status", lotStatus.toString());
        request.setAttribute("lots", lots);
        request.setAttribute("bets", bets);
        return CommandResult.forward("/jsp/user/accountUpdatePage.jsp");
    }
}
