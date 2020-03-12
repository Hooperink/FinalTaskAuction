package by.epam.auction.dao.api;

import by.epam.auction.dto.User;
import by.epam.auction.exception.DaoException;

import java.util.Optional;

public interface UserDao extends Dao<User> {
    Optional<User> findUserByLoginAndPassword(String login, String password) throws DaoException;
    Optional<User> findUserByLogin(String login) throws DaoException;
    void save(User user) throws DaoException;
    void changeUserActivity(long id) throws DaoException;
}
