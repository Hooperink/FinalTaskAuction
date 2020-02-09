package epam.by.Auction.dao.impl;

import epam.by.Auction.dao.api.UserDao;
import epam.by.Auction.mapper.UserRowMapper;
import epam.by.Auction.connection.ProxyConnection;
import epam.by.Auction.entity.User;
import epam.by.Auction.exception.DaoException;

import java.util.Optional;

public class UserDaoImpl extends AbstractDAO<User> implements UserDao {

    private static final String FIND_BY_LOGIN_AND_PASSWORD = "SELECT * FROM user WHERE login = ? AND password = ?";
    private static final String SAVE_USER = "insert into user(login, password, is_active, balance) " +
            "VALUES(?, ?, ?, ?) ON DUPLICATE KEY UPDATE login=VALUES(login), password=VALUES(password), " +
            "is_active=VALUES(is_active), balance=VALUES(balance)";
    private static final String CHANGE_USER_ACTIVITY = "UPDATE user SET is_active = IF (is_active, 0, 1) WHERE id = ?";

    public UserDaoImpl(ProxyConnection proxyConnection) {
        super(proxyConnection);
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
