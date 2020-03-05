package epam.by.Auction.dao.api;

import epam.by.Auction.dto.User;
import epam.by.Auction.exception.DaoException;

import java.util.Optional;

public interface UserDao extends Dao<User> {
    Optional<User> findUserByLoginAndPassword(String login, String password) throws DaoException;
    Optional<User> findUserByLogin(String login) throws DaoException;
    void save(User user) throws DaoException;
    void changeUserActivity(long id) throws DaoException;
}
