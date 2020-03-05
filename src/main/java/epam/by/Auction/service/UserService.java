package epam.by.Auction.service;

import epam.by.Auction.dao.impl.DaoHelper;
import epam.by.Auction.dao.DaoHelperFactory;
import epam.by.Auction.dao.api.UserDao;
import epam.by.Auction.dto.User;
import epam.by.Auction.errors.ErrorCodeForMessage;
import epam.by.Auction.exception.DaoException;
import epam.by.Auction.exception.MessageErrorException;
import epam.by.Auction.utils.PasswordEncryptor;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class UserService {
    private DaoHelperFactory daoHelperFactory;

    public UserService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public void registration (String login, String password, PasswordEncryptor passwordEncryptor) throws DaoException, MessageErrorException {
        String md5Password = passwordEncryptor.md5Custom(password);
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            UserDao userDao = daoHelper.createUserDao();
            Optional<User> userLogin = findByLogin(login);
            if (userLogin.isPresent()) {
                throw new MessageErrorException(ErrorCodeForMessage.ERROR_8.name());
            } else {
                User user = new User();
                user.setLogin(login);
                user.setPassword(md5Password);
                user.setIsActive(true);
                user.setBalance(new BigDecimal("0"));
                userDao.save(user);
            }
        }
    }

    public Optional<User> login(String login, String password) throws DaoException {
        try (DaoHelper daoHelper = daoHelperFactory.create() ){
            UserDao userDao = daoHelper.createUserDao();
            return userDao.findUserByLoginAndPassword(login, password);
        }
    }

    private Optional<User> findByLogin(String login) throws DaoException {
        try (DaoHelper daoHelper = daoHelperFactory.create() ){
            UserDao userDao = daoHelper.createUserDao();
            return userDao.findUserByLogin(login);
        }
    }

    public Optional<User> findById(long id) throws DaoException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            UserDao userDao = daoHelper.createUserDao();
            return userDao.getById(id);
        }
    }

    public List<User> getAllUsers() throws DaoException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            UserDao userDao = daoHelper.createUserDao();
            return userDao.getAll();
        }
    }

    public void changeActivity(long id) throws DaoException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            UserDao userDao = daoHelper.createUserDao();
            userDao.changeUserActivity(id);
        }
    }

    public void updateAccount(long id, BigDecimal amount) throws DaoException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            UserDao userDao = daoHelper.createUserDao();
            Optional<User> optionalUser = userDao.getById(id);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                BigDecimal userBalance = user.getBalance();
                BigDecimal newBalance = userBalance.add(amount);
                user.setBalance(newBalance);
                userDao.save(user);
            }
        }
    }
}