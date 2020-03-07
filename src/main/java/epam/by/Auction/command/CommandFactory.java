package epam.by.Auction.command;

import epam.by.Auction.dao.DaoHelperFactory;
import epam.by.Auction.service.LotService;
import epam.by.Auction.service.UserService;
import epam.by.Auction.exception.UnknownCommandException;
import epam.by.Auction.service.BetService;
import epam.by.Auction.utils.PasswordEncryptor;
import epam.by.Auction.utils.StringValidator;
import epam.by.Auction.utils.UserBalanceCalculator;

public class CommandFactory {

    private final static String CHANGE_LANGUAGE = "changeLanguage";
    private final static String PLACE_BET = "placeBet";
    private final static String LOGIN = "login";
    private final static String SHOW_MAIN_PAGE = "showMainPage";
    private final static String GET_LIST_LOT = "getListLot";
    private final static String SHOW_LOGIN_PAGE = "showLoginPage";
    private final static String SHOW_SINGLE_LOT_PAGE = "showSingleLotPage";
    private final static String SHOW_CREATE_LOT_PAGE = "showCreateLotPage";
    private final static String CREATE_LOT = "createLot";
    private final static String SHOW_SUCCESS_CREATE_PAGE = "showSuccessCreatePage";
    private final static String LOGOUT = "logout";
    private final static String GET_SINGLE_LOT = "getSingleLot";
    private final static String GET_USERS = "getUsers";
    private final static String CHANGE_ACTIVITY = "changeActivity";
    private final static String DELETE_LOT = "deleteLot";
    private final static String EDIT_LOT = "editLot";
    private final static String GET_LOT_BY_STATUS = "getLotsByStatus";
    private final static String GET_USER_LOTS = "getUserLots";
    private final static String UPDATE_ACCOUNT = "updateAccount";
    private final static String SHOW_UPDATE_ACCOUNT_PAGE = "showUpdateAccountPage";
    private final static String SHOW_HISTORY_PAGE = "showHistoryPage";
    private final static String GET_ACCOUNT_INFO = "getAccountInfo";
    private final static String GET_BALANCE_INFO = "getBalanceInfo";
    private final static String SHOW_REGISTRATION_PAGE = "showRegistrationPage";
    private final static String REGISTRATION = "register";
    
    public static Command create(String command) throws UnknownCommandException {
        switch (command) {
            case CHANGE_LANGUAGE:
                return new ChangeLanguage();
            case PLACE_BET:
                return new PlaceBetCommand(new BetService(new DaoHelperFactory(), new UserBalanceCalculator()));
            case LOGIN:
                return new LoginCommand(new UserService(new DaoHelperFactory()), new PasswordEncryptor());
            case SHOW_MAIN_PAGE:
                return new ShowPageCommand("/jsp/user/mainPageForward.jsp");
            case GET_LIST_LOT:
                return new GetListLotCommand(new LotService(new DaoHelperFactory()), new BetService(new DaoHelperFactory()));
            case SHOW_LOGIN_PAGE:
                return new ShowPageCommand("/jsp/login.jsp");
            case SHOW_SINGLE_LOT_PAGE:
                return new ShowPageCommand("/jsp/user/singleLotPage.jsp");
            case SHOW_CREATE_LOT_PAGE:
                return new ShowPageCommand("/jsp/user/createLot.jsp");
            case CREATE_LOT:
                return new CreateLotCommand(new LotService(new DaoHelperFactory()), new StringValidator());
            case SHOW_SUCCESS_CREATE_PAGE:
                return new ShowPageCommand("/jsp/user/successCreationOfLot.jsp");
            case LOGOUT:
                return new LogoutCommand();
            case GET_SINGLE_LOT:
                return new GetSingleLotCommand(new LotService(new DaoHelperFactory()), new BetService(new DaoHelperFactory()), new UserService(new DaoHelperFactory()));
            case GET_USERS:
                return new GetUserListCommand(new UserService(new DaoHelperFactory()));
            case CHANGE_ACTIVITY:
                return new ChangeActivityCommand(new UserService(new DaoHelperFactory()));
            case DELETE_LOT:
                return new DeleteLotCommand(new LotService(new DaoHelperFactory(), new UserBalanceCalculator()));
            case EDIT_LOT:
                return new EditLotCommand(new LotService(new DaoHelperFactory(), new UserBalanceCalculator()), new StringValidator());
            case GET_LOT_BY_STATUS:
                return new GetLotsByStatusCommand(new LotService(new DaoHelperFactory()));
            case GET_USER_LOTS:
                return new GetUserLotsCommand(new LotService(new DaoHelperFactory()));
            case UPDATE_ACCOUNT:
                return new UpdateAccountCommand(new UserService(new DaoHelperFactory()));
            case SHOW_UPDATE_ACCOUNT_PAGE:
                return new ShowPageCommand("/jsp/user/accountBalanceInfo.jsp");
            case SHOW_HISTORY_PAGE:
                return new ShowPageCommand("/jsp/user/accountHistory.jsp");
            case GET_ACCOUNT_INFO:
                return new GetAccountInfoCommand(new UserService(new DaoHelperFactory()), new LotService(new DaoHelperFactory()), new BetService(new DaoHelperFactory()));
            case GET_BALANCE_INFO:
                return new GetBalanceInfoCommand(new UserService(new DaoHelperFactory()));
            case SHOW_REGISTRATION_PAGE:
                return new ShowPageCommand("/jsp/user/registrationForm.jsp");
            case REGISTRATION:
                return new RegistrationCommand(new UserService(new DaoHelperFactory()), new PasswordEncryptor(), new StringValidator());
            default:
                throw new UnknownCommandException("Unknown command " + command);
        }
    }
}
