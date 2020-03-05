package epam.by.Auction.dao.api;

import epam.by.Auction.dto.Bet;
import epam.by.Auction.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface BetDao extends Dao<Bet> {
    Optional<Bet> getByLotId(long id) throws DaoException;
    void save(Bet bet) throws DaoException;
    List<Bet> getByIdAndSellStatus(String status, long id) throws DaoException;
}
