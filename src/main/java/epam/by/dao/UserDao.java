package epam.by.dao;

import epam.by.entity.User;
import epam.by.exception.DaoException;

import java.util.Optional;

public interface UserDao extends Dao<User> {
    Optional<User> findUserByLoginAndPassword(String login, String password) throws DaoException;
    void save(User user) throws DaoException;
    void changeUserActivity(long id) throws DaoException;
}
