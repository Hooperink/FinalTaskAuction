package by.epam.auction.command;

import by.epam.auction.dto.Bet;
import by.epam.auction.service.BetService;
import by.epam.auction.service.LotService;
import by.epam.auction.service.UserService;
import by.epam.auction.constants.ConstantForCommands;
import by.epam.auction.dto.Lot;
import by.epam.auction.dto.enums.LotStatus;
import by.epam.auction.dto.User;
import by.epam.auction.exception.DaoException;

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
        long userId = (long)session.getAttribute(ConstantForCommands.ID);
        String status = request.getParameter(ConstantForCommands.STATUS);
        LotStatus lotStatus = status == null ? LotStatus.ACTIVE: LotStatus.valueOf(status);
        List<Lot> lots = lotService.getLotsByStatusAndUserId(userId, lotStatus);
        Optional<User> optionalUser = userService.findById(userId);
        List<Bet> bets = betService.getAllUserBetsByUserIdAndLotStatus(userId, lotStatus);
        optionalUser.ifPresent(user -> request.setAttribute(ConstantForCommands.USER, user));
        request.setAttribute(ConstantForCommands.STATUS, lotStatus.toString());
        request.setAttribute(ConstantForCommands.LOTS, lots);
        request.setAttribute(ConstantForCommands.BETS, bets);
        return CommandResult.forward("/jsp/user/accountHistory.jsp");
    }
}
