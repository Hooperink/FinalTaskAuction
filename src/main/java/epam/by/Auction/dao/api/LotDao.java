package epam.by.Auction.dao.api;

import epam.by.Auction.entity.Lot;
import epam.by.Auction.exception.DaoException;

import java.util.List;

public interface LotDao extends Dao<Lot> {
    List<Lot> findByStatus(String status, int limit, int offset) throws DaoException;
    void save(Lot lot) throws DaoException;
    List<Lot> getAllUserLotsById(long id) throws DaoException;
    List<Lot> getAllLotsStatusAndUserId(long id, String status) throws DaoException;
    int getAmountOfRows(String status) throws DaoException;
}
