package epam.by.Auction.dao.api;

import epam.by.Auction.entity.Identifiable;
import epam.by.Auction.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface Dao <T extends Identifiable> {
    Optional<T> getById(long id) throws DaoException;
    List<T> getAll() throws DaoException;
    void save(T item, String query) throws DaoException;
    void removeById(long id) throws DaoException;
    void executeUpdate(String query, Object... params) throws DaoException;
}
