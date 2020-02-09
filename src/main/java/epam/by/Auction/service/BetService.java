package epam.by.Auction.service;

import epam.by.Auction.dao.impl.DaoHelper;
import epam.by.Auction.dao.impl.DaoHelperFactory;
import epam.by.Auction.entity.LotStatus;
import epam.by.Auction.utils.UserBalanceCalculator;
import epam.by.Auction.dao.api.BetDao;
import epam.by.Auction.dao.impl.BetDaoImpl;
import epam.by.Auction.dao.api.LotDao;
import epam.by.Auction.dao.api.UserDao;
import epam.by.Auction.entity.Bet;
import epam.by.Auction.entity.Lot;
import epam.by.Auction.entity.User;
import epam.by.Auction.exception.DaoException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static epam.by.Auction.constants.ConstantForBetService.*;

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

    public String placeBet(long lotId, long newUserId, BigDecimal newBet) throws DaoException {
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
            return NOTHING_HAPPENED;
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

    private String replaceExistingBet(Bet bet, User newUser, User oldUser, BigDecimal newBet, DaoHelper daoHelper) throws DaoException {
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
                   return SUCCESS;
               } else {
                   return ALREADY_PLACED;
               }
           } else {
               return LOWER_THAN_SHOULD;
           }
        } else {
            return NOT_ENOUGH_MONEY;
        }
    }
    private String createNewBet(User newUser, BigDecimal newBetAmount, Lot lot, DaoHelper daoHelper) throws DaoException {
        BigDecimal newUserBalance = newUser.getBalance();
        long newUserId = newUser.getId();
        long lotId = lot.getId();
        BigDecimal lotPrice = lot.getPrice();
        if(newUserBalance.compareTo(lotPrice) >= 0) {
            if(newUserBalance.compareTo(newBetAmount) >= 0) {
                if(newBetAmount.compareTo(lotPrice) >= 0) {
                    Bet bet = new Bet();
                    bet.setBet(newBetAmount);
                    bet.setBuyerId(newUserId);
                    bet.setLotId(lotId);
                    newUser = userBalanceCalculator.subtractFromBalance(newUser, newBetAmount);
                    saveUserAndBet(daoHelper, newUser, bet);
                    return SUCCESS;
                } else {
                    return BET_LOWER_THEN_PRICE;
                }
            } else {
                return NOT_ENOUGH_MONEY;
            }
        } else {
            return NOT_ENOUGH_MONEY;
        }
    }
}