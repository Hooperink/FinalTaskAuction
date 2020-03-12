package by.epam.auction.dao.api;

import by.epam.auction.dto.Bet;
import by.epam.auction.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface BetDao extends Dao<Bet> {
    Optional<Bet> getByLotId(long id) throws DaoException;
    void save(Bet bet) throws DaoException;
    List<Bet> getByIdAndSellStatus(String status, long id) throws DaoException;
}
