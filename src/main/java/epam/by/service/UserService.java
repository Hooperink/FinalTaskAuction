package epam.by.service;

import epam.by.dao.DaoHelper;
import epam.by.dao.DaoHelperFactory;
import epam.by.dao.UserDao;
import epam.by.entity.User;
import epam.by.exception.DaoException;
import epam.by.exception.ServiceException;


import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserService {
    private DaoHelperFactory daoHelperFactory;

    public UserService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public Optional<User> login(String login, String password) throws DaoException {
        try (DaoHelper daoHelper = daoHelperFactory.create() ){
            UserDao userDao = daoHelper.createUserDao();
            return userDao.findUserByLoginAndPassword(login, password);
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

    public void saveUser (User user) throws DaoException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            UserDao userDao = daoHelper.createUserDao();
            userDao.save(user);
        }
    }

    public boolean registration(String login, String password) throws DaoException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            UserDao userDao = daoHelper.createUserDao();
            Optional<User> userLogin = login(login, password);
            if (userLogin.isPresent()) {
                return false;
            } else {
                User user = new User();
                user.setLogin(login);
                user.setPassword(password);
                user.setIsActive(true);
                user.setBalance(new BigDecimal("0"));
                userDao.save(user);
                return true;
            }
        }
    }
}