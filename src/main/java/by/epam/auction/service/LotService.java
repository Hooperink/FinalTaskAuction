package by.epam.auction.service;

import by.epam.auction.dao.DaoHelperFactory;
import by.epam.auction.dao.impl.DaoHelper;
import by.epam.auction.dao.api.BetDao;
import by.epam.auction.dao.api.LotDao;
import by.epam.auction.dao.api.UserDao;
import by.epam.auction.dto.Bet;
import by.epam.auction.dto.Lot;
import by.epam.auction.dto.enums.LotStatus;
import by.epam.auction.dto.User;
import by.epam.auction.exception.DaoException;
import by.epam.auction.utils.FileNameGetterFromPart;
import by.epam.auction.utils.FilePartSaver;
import by.epam.auction.utils.UserBalanceCalculator;

import javax.servlet.http.Part;
import java.io.IOException;
import java.math.BigDecimal;
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
            Optional<Lot> optionalLot = lotDao.getById(id);
            if (optionalLot.isPresent()) {
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
                Lot lot = optionalLot.get();
                lot.setStatus(LotStatus.DELETED);
                lotDao.save(lot);
                daoHelper.endTransaction();
            }
        }
    }

    public int getAmountOfRows(LotStatus lotStatus) throws DaoException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            String status = lotStatus.toString();
            LotDao lotDao = daoHelper.createLotDao();
            return lotDao.getAmountOfRows(status);
        }
    }

    public void save(String name, String description, BigDecimal price, long userId, Part part) throws DaoException, IOException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            LotDao lotDao = daoHelper.createLotDao();
            FilePartSaver filePartSaver = new FilePartSaver(new FileNameGetterFromPart());
            String imagePath = filePartSaver.saveFile(part);
            Lot lot = createLot(name, description, price, userId, imagePath);
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

    private Lot createLot(String name, String description, BigDecimal price, long userId, String imagePath) {
        Lot lot = new Lot();
        lot.setName(name);
        lot.setDescription(description);
        lot.setPrice(price);
        lot.setStatus(LotStatus.MODERATION);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        lot.setStartSellDate(timestamp);
        lot.setSellerId(userId);
        lot.setImagePath(imagePath);
        return lot;
    }

}
