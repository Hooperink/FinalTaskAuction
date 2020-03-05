package epam.by.Auction.service;

import epam.by.Auction.dao.impl.DaoHelper;
import epam.by.Auction.dao.DaoHelperFactory;
import epam.by.Auction.dto.LotStatus;
import epam.by.Auction.errors.ErrorCodeForMessage;
import epam.by.Auction.exception.MessageErrorException;
import epam.by.Auction.utils.UserBalanceCalculator;
import epam.by.Auction.dao.api.BetDao;
import epam.by.Auction.dao.impl.BetDaoImpl;
import epam.by.Auction.dao.api.LotDao;
import epam.by.Auction.dao.api.UserDao;
import epam.by.Auction.dto.Bet;
import epam.by.Auction.dto.Lot;
import epam.by.Auction.dto.User;
import epam.by.Auction.exception.DaoException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class BetService {

    private DaoHelperFactory daoHelperFactory;
    private UserBalanceCalculator userBalanceCalculator;

    public BetService(DaoHelperFactory daoHelperFactory, UserBalanceCalculator userBalanceCalculator) {
        this.daoHelperFactory = daoHelperFactory;
        this.userBalanceCalculator = userBalanceCalculator;
    }

    public BetService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public Optional<Bet> findBetByLotId(long id) throws DaoException {
        try (DaoHelper daoHelper = daoHelperFactory.create()){
            BetDao betDao = daoHelper.createBetDao();
            return betDao.getByLotId(id);
        }
    }

    public List<Bet> getAllBets() throws DaoException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            BetDaoImpl betDao = (BetDaoImpl) daoHelper.createBetDao();
            return betDao.getAll();
        }
    }

    public List<Bet> getAllUserBetsByUserIdAndLotStatus(long userId, LotStatus lotStatus) throws DaoException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            BetDao betDao = daoHelper.createBetDao();
            return betDao.getByIdAndSellStatus(lotStatus.toString(), userId);
        }
    }

    public boolean placeBet(long lotId, long newUserId, BigDecimal newBet) throws DaoException, MessageErrorException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            BetDao betDao = daoHelper.createBetDao();
            UserDao userDao = daoHelper.createUserDao();
            LotDao lotDao = daoHelper.createLotDao();
            Optional<Bet> bet = betDao.getByLotId(lotId);
            Optional<User> newUser = userDao.getById(newUserId);
            Optional<Lot> lot = lotDao.getById(lotId);
            if (bet.isPresent() & newUser.isPresent()) {
                long oldUserId = bet.get().getBuyerId();
                Optional<User> oldUser = userDao.getById(oldUserId);
                if (oldUser.isPresent()) {
                    return replaceExistingBet(bet.get(), newUser.get(), oldUser.get(), newBet, daoHelper);
                }
            } else if (newUser.isPresent() & lot.isPresent()) {
                return createNewBet(newUser.get(), newBet, lot.get(), daoHelper);
            }
            return false;
        }
    }

    private void saveUserAndBet(DaoHelper daoHelper, Object... objects) throws DaoException {
        UserDao userDao = daoHelper.createUserDao();
        BetDao betDao = daoHelper.createBetDao();
        daoHelper.startTransaction();
        for (Object o: objects) {
            if (o instanceof User) {
                userDao.save((User) o);
            } else if (o instanceof Bet) {
                betDao.save((Bet) o);
            }
        }
        daoHelper.endTransaction();
    }

    private boolean replaceExistingBet(Bet bet, User newUser, User oldUser, BigDecimal newBet, DaoHelper daoHelper) throws DaoException, MessageErrorException {
        BigDecimal newUserBalance = newUser.getBalance();
        long newUserId = newUser.getId();
        long oldUserId = oldUser.getId();
        BigDecimal currentBet = bet.getBet();
        if ((newUserBalance.compareTo(currentBet) >= 0)) {
           if (newBet.compareTo(currentBet) >= 0) {
               if (newUserId != oldUserId) {
                   oldUser = userBalanceCalculator.addToBalance(oldUser, currentBet);
                   newUser = userBalanceCalculator.subtractFromBalance(newUser, newBet);
                   bet.setBet(newBet);
                   bet.setBuyerId(newUserId);
                   saveUserAndBet(daoHelper, oldUser, newUser, bet);
                   return true;
               } else {
                   throw new MessageErrorException(ErrorCodeForMessage.ERROR_2.name());
               }
           } else {
               throw new MessageErrorException(ErrorCodeForMessage.ERROR_3.name());
           }
        } else {
            throw new MessageErrorException(ErrorCodeForMessage.ERROR_4.name());
        }
    }
    private boolean createNewBet(User newUser, BigDecimal newBetAmount, Lot lot, DaoHelper daoHelper) throws DaoException, MessageErrorException {
        BigDecimal newUserBalance = newUser.getBalance();
        long newUserId = newUser.getId();
        long lotId = lot.getId();
        BigDecimal lotPrice = lot.getPrice();
        if(newUserBalance.compareTo(lotPrice) >= 0 || newUserBalance.compareTo(newBetAmount) >= 0) {
            if(newBetAmount.compareTo(lotPrice) >= 0) {
                Bet bet = new Bet();
                bet.setBet(newBetAmount);
                bet.setBuyerId(newUserId);
                bet.setLotId(lotId);
                newUser = userBalanceCalculator.subtractFromBalance(newUser, newBetAmount);
                saveUserAndBet(daoHelper, newUser, bet);
                return true;
            } else {
                throw new MessageErrorException(ErrorCodeForMessage.ERROR_5.name());
            }
        } else {
            throw new MessageErrorException(ErrorCodeForMessage.ERROR_4.name());
        }
    }
}