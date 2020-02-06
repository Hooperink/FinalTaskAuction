package epam.by.service;

import epam.by.dao.BetDao;
import epam.by.dao.DaoHelper;
import epam.by.dao.DaoHelperFactory;
import epam.by.dao.LotDao;
import epam.by.dao.UserDao;
import epam.by.entity.Bet;
import epam.by.entity.Lot;
import epam.by.entity.LotStatus;
import epam.by.entity.User;
import epam.by.exception.DaoException;
import epam.by.exception.ServiceException;
import epam.by.utils.UserBalanceCalculator;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class LotService {

    private DaoHelperFactory daoHelperFactory;
    private UserBalanceCalculator userBalanceCalculator;

    public LotService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public LotService(DaoHelperFactory daoHelperFactory, UserBalanceCalculator userBalanceCalculator) {
        this.daoHelperFactory = daoHelperFactory;
        this.userBalanceCalculator = userBalanceCalculator;
    }

    public Optional<Lot> findLotById(long id) throws DaoException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            LotDao lotDao = daoHelper.createLotDao();
            return lotDao.getById(id);
        }
    }

    public List<Lot> getAllUserLotsById(long id) throws DaoException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            LotDao lotDao = daoHelper.createLotDao();
            return lotDao.getAllUserLotsById(id);
        }
    }

    public List<Lot> getLotByStatus(LotStatus lotStatus, int page, int numberOfRows) throws DaoException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            LotDao lotDao = daoHelper.createLotDao();
            return lotDao.findByStatus(lotStatus.getStatus(), page, numberOfRows);
        }
    }

    public void remove(long id) throws DaoException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            LotDao lotDao = daoHelper.createLotDao();
            BetDao betDao = daoHelper.createBetDao();
            UserDao userDao = daoHelper.createUserDao();
            daoHelper.startTransaction();
            Optional<Bet> betOptional = betDao.getByLotId(id);
            if (betOptional.isPresent()) {
                Bet bet = betOptional.get();
                long buyerId = bet.getBuyerId();
                Optional<User> optionalUser = userDao.getById(buyerId);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    BigDecimal betAmount = bet.getBet();
                    user = userBalanceCalculator.addToBalance(user, betAmount);
                    userDao.save(user);
                }
            }
            lotDao.removeById(id);
            daoHelper.endTransaction();
        }
    }

    public int getAmountOfRows(LotStatus lotStatus) throws DaoException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            String status = lotStatus.toString();
            LotDao lotDao = daoHelper.createLotDao();
            return lotDao.getAmountOfRows(status);
        }
    }

    public void save(String name, String description, BigDecimal price, long userId) throws DaoException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            LotDao lotDao = daoHelper.createLotDao();
            Lot lot = createLot(name, description, price, userId);
            lotDao.save(lot);
        }
    }

    public void edit(long lotId, String name, String description) throws DaoException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            LotDao lotDao = daoHelper.createLotDao();
            Optional<Lot> optionalLot = lotDao.getById(lotId);
            if (optionalLot.isPresent()) {
                Lot lot = optionalLot.get();
                lot.setName(name);
                lot.setDescription(description);
                lotDao.save(lot);
            }
        }
    }

    public void edit(long lotId, String name, String description, String status) throws DaoException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            LotDao lotDao = daoHelper.createLotDao();
            Optional<Lot> optionalLot = lotDao.getById(lotId);
            if (optionalLot.isPresent()) {
                Lot lot = optionalLot.get();
                lot.setName(name);
                lot.setDescription(description);
                LotStatus lotStatus = LotStatus.valueOf(status);
                lot.setStatus(lotStatus);
                lotDao.save(lot);
            }
        }
    }

    public void sellLot (long lotId, String name, String description, String status) throws  DaoException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            BetDao betDao = daoHelper.createBetDao();
            LotDao lotDao = daoHelper.createLotDao();
            UserDao userDao = daoHelper.createUserDao();
            Optional<Bet> optionalBet = betDao.getByLotId(lotId);
            Optional<Lot> optionalLot = lotDao.getById(lotId);
            if (optionalBet.isPresent() && optionalLot.isPresent()) {
                long buyerId = optionalBet.get().getBuyerId();
                Lot lot = optionalLot.get();
                editEntityLotForSale(lot, name, description, LotStatus.valueOf(status), buyerId);
                long userId = lot.getSellerId();
                Optional<User> optionalUser = userDao.getById(userId);
                daoHelper.startTransaction();
                if (optionalUser.isPresent()) {
                    BigDecimal betAmount = optionalBet.get().getBet();
                    User user = optionalUser.get();
                    user = userBalanceCalculator.addToBalance(user, betAmount);
                    userDao.save(user);
                }
                lotDao.save(lot);
                daoHelper.endTransaction();
            }
        }
    }

    public List<Lot> getLotsByStatusAndUserId(long id, LotStatus lotStatus) throws DaoException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            LotDao lotDao = daoHelper.createLotDao();
            return lotDao.getAllLotsStatusAndUserId(id, lotStatus.toString());
        }
    }

    private Lot editEntityLotForSale(Lot lot, String name, String description, LotStatus status, long buyerId) {
        lot.setName(name);
        lot.setDescription(description);
        lot.setStatus(status);
        lot.setBuyerId(buyerId);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        lot.setEndSellDate(timestamp);
        return lot;
    }

    private Lot createLot(String name, String description, BigDecimal price, long userId) {
        Lot lot = new Lot();
        lot.setName(name);
        lot.setDescription(description);
        lot.setPrice(price);
        lot.setStatus(LotStatus.MODERATION);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        lot.setStartSellDate(timestamp);
        lot.setSellerId(userId);
        return lot;
    }
}
