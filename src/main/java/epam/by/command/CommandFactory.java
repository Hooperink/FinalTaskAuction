package epam.by.command;

import epam.by.dao.DaoHelperFactory;
import epam.by.exception.UnknownCommandException;
import epam.by.service.BetService;
import epam.by.service.LotService;
import epam.by.service.UserService;
import epam.by.utils.UserBalanceCalculator;

public class CommandFactory {
    public static Command create(String command) throws UnknownCommandException {
        switch (command) {
            case "placeBet":
                return new PlaceBetCommand(new BetService(new DaoHelperFactory(), new UserBalanceCalculator()));
            case "login":
                return new LoginCommand(new UserService(new DaoHelperFactory()));
            case "showMainPage":
                return new ShowPageCommand("/jsp/user/mainPageForward.jsp");
            case "getListLot":
                return new GetListLotCommand(new LotService(new DaoHelperFactory()), new BetService(new DaoHelperFactory()));
            case "showLoginPage":
                return new ShowPageCommand("/login.jsp");
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
                return new ShowPageCommand("/jsp/user/accountUpdatePage.jsp");
            case "getAccountInfo":
                return new GetAccountInfoCommand(new UserService(new DaoHelperFactory()), new LotService(new DaoHelperFactory()), new BetService(new DaoHelperFactory()));
            default:
                throw new UnknownCommandException("Unknown command " + command);
        }
    }
}
