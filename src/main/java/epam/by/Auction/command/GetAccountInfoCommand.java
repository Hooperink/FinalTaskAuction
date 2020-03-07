package epam.by.Auction.command;

import epam.by.Auction.constants.ConstantForCommands;
import epam.by.Auction.dto.Bet;
import epam.by.Auction.service.BetService;
import epam.by.Auction.service.LotService;
import epam.by.Auction.service.UserService;
import epam.by.Auction.dto.Lot;
import epam.by.Auction.dto.enums.LotStatus;
import epam.by.Auction.dto.User;
import epam.by.Auction.exception.DaoException;

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
