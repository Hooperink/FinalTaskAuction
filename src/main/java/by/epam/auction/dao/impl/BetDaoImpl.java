package by.epam.auction.dao.impl;

import by.epam.auction.dao.api.BetDao;
import by.epam.auction.dto.Bet;
import by.epam.auction.mapper.BetRowMapper;
import by.epam.auction.connection.ProxyConnection;
import by.epam.auction.exception.DaoException;

import java.util.List;
import java.util.Optional;

public class BetDaoImpl extends AbstractDAO<Bet> implements BetDao {

    public static final String GET_BY_LOT_ID = "SELECT * FROM lot_bet WHERE lot_id = ?";
    public static final String SAVE_BET = "INSERT INTO lot_bet (lot_id, buyer_id, bet) VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE " +
            "lot_id=VALUES(lot_id), buyer_id=VALUES(buyer_id), bet=VALUES(bet)";
    public static final  String GET_BY_ID_AND_STATUS = "SELECT * FROM lot_bet JOIN lot ON lot.id = lot_bet.lot_id " +
            "WHERE lot.status = ? AND lot.buyer_id = ?";


    public BetDaoImpl(ProxyConnection proxyConnection) {
        super(proxyConnection);
    }

    @Override
    public void save(Bet bet) throws DaoException{
            super.save(bet, SAVE_BET);
    }

    @Override
    public List<Bet> getByIdAndSellStatus(String status, long id) throws DaoException {
        return executeGetQuery(GET_BY_ID_AND_STATUS, new BetRowMapper(), status, id);
    }

    @Override
    protected String getTableName() {
        return Bet.TABLE;
    }

    public Optional<Bet> getByLotId(long id) throws DaoException {
          return executeGetForSingleResult(GET_BY_LOT_ID, new BetRowMapper(), id);
    }
}
