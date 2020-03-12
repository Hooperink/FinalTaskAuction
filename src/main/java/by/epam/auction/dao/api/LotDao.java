package by.epam.auction.dao.api;

import by.epam.auction.dto.Lot;
import by.epam.auction.exception.DaoException;

import java.util.List;

public interface LotDao extends Dao<Lot> {
    List<Lot> findByStatus(String status, int limit, int offset) throws DaoException;
    void save(Lot lot) throws DaoException;
    List<Lot> getAllUserLotsById(long id) throws DaoException;
    List<Lot> getAllLotsStatusAndUserId(long id, String status) throws DaoException;
    int getAmountOfRows(String status) throws DaoException;
}
