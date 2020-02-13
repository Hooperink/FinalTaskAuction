package epam.by.Auction.command;

import epam.by.Auction.dao.impl.DaoHelperFactory;
import epam.by.Auction.service.LotService;
import epam.by.Auction.service.UserService;
import epam.by.Auction.exception.UnknownCommandException;
import epam.by.Auction.service.BetService;
import epam.by.Auction.utils.UserBalanceCalculator;

public class CommandFactory {
    public static Command create(String command) throws UnknownCommandException {
        switch (command) {
            case "changeLanguage":
                return new ChangeLanguage();
            case "placeBet":
                return new PlaceBetCommand(new BetService(new DaoHelperFactory(), new UserBalanceCalculator()));
            case "login":
                return new LoginCommand(new UserService(new DaoHelperFactory()));
            case "showMainPage":
                return new ShowPageCommand("/jsp/user/mainPageForward.jsp");
            case "getListLot":
                return new GetListLotCommand(new LotService(new DaoHelperFactory()), new BetService(new DaoHelperFactory()));
            case "showLoginPage":
                return new ShowPageCommand("/jsp/login.jsp");
            case "showSingleLotPage":
                return new ShowPageCommand("/jsp/user/singleLotPage.jsp");
            case "showCreateLotPage":
                return new ShowPageCommand("/jsp/user/createLot.jsp");
            case "createLot":
                return new CreateLotCommand(new LotService(new DaoHelperFactory()));
            case "showSuccessCreatePage":
                return new ShowPageCommand("/jsp/user/successCreationOfLot.jsp");
            case "logout":
                return new LogoutCommand();
            case "getSingleLot":
                return new GetSingleLotCommand(new LotService(new DaoHelperFactory()), new BetService(new DaoHelperFactory()), new UserService(new DaoHelperFactory()));
            case "getUsers":
                return new GetUserListCommand(new UserService(new DaoHelperFactory()));
            case "changeActivity":
                return new ChangeActivityCommand(new UserService(new DaoHelperFactory()));
            case "deleteLot":
                return new DeleteLotCommand(new LotService(new DaoHelperFactory(), new UserBalanceCalculator()));
            case "editLot":
                return new EditLotCommand(new LotService(new DaoHelperFactory(), new UserBalanceCalculator()));
            case "getLotsByStatus":
                return new GetLotsByStatusCommand(new LotService(new DaoHelperFactory()));
            case "getUserLots":
                return new GetUserLotsCommand(new LotService(new DaoHelperFactory()));
            case "updateAccount":
                return new UpdateAccountCommand(new UserService(new DaoHelperFactory()));
            case "showUpdateAccountPage":
                return new ShowPageCommand("/jsp/user/accountBalanceInfo.jsp");
            case "showHistoryPage":
                return new ShowPageCommand("/jsp/user/accountHistory.jsp");
            case "getAccountInfo":
                return new GetAccountInfoCommand(new UserService(new DaoHelperFactory()), new LotService(new DaoHelperFactory()), new BetService(new DaoHelperFactory()));
            case "getBalanceInfo":
                return new GetBalanceInfoCommand(new UserService(new DaoHelperFactory()));
            default:
                throw new UnknownCommandException("Unknown command " + command);
        }
    }
}
