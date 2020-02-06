package epam.by.dao;

import epam.by.entity.Bet;
import epam.by.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface BetDao {
    Optional<Bet> getByLotId(long id) throws DaoException;
    void save(Bet bet) throws DaoException;
    List<Bet> getByIdAndSellStatus(String status, long id) throws DaoException;
}
