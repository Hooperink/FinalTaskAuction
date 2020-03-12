package by.epam.auction.dao.impl;

import by.epam.auction.dao.api.UserDao;
import by.epam.auction.mapper.UserRowMapper;
import by.epam.auction.connection.ProxyConnection;
import by.epam.auction.dto.User;
import by.epam.auction.exception.DaoException;

import java.util.Optional;

public class UserDaoImpl extends AbstractDAO<User> implements UserDao {

    private static final String FIND_BY_LOGIN_AND_PASSWORD = "SELECT * FROM user WHERE login = ? AND password = ?";
    private static final String FIND_BY_LOGIN = "SELECT * FROM user WHERE login = ?";
    private static final String SAVE_USER = "insert into user(login, password, is_active, balance) " +
            "VALUES(?, ?, ?, ?) ON DUPLICATE KEY UPDATE login=VALUES(login), password=VALUES(password), " +
            "is_active=VALUES(is_active), balance=VALUES(balance)";
    private static final String CHANGE_USER_ACTIVITY = "UPDATE user SET is_active = IF (is_active, 0, 1) WHERE id = ?";

    public UserDaoImpl(ProxyConnection proxyConnection) {
        super(proxyConnection);
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DaoException {
        return executeGetForSingleResult(FIND_BY_LOGIN, new UserRowMapper(), login);
    }

    @Override
    public Optional<User> findUserByLoginAndPassword(String login, String password) throws DaoException {
        return executeGetForSingleResult(FIND_BY_LOGIN_AND_PASSWORD, new UserRowMapper(), login, password);
    }

    public void changeUserActivity(long id) throws DaoException {
        executeUpdate(CHANGE_USER_ACTIVITY, id);
    }

    @Override
    protected String getTableName() {
        return User.TABLE;
    }

    @Override
    public void save(User item) throws DaoException {
        super.save(item, SAVE_USER);
    }
}
