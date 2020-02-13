package epam.by.Auction.dao.impl;

import epam.by.Auction.dao.api.LotDao;
import epam.by.Auction.mapper.LotRowMapper;
import epam.by.Auction.connection.ProxyConnection;
import epam.by.Auction.entity.Lot;
import epam.by.Auction.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
public class LotDaoImpl extends AbstractDAO<Lot> implements LotDao {

    public static final String GET_BY_STATUS = "SELECT * FROM lot WHERE status = ? LIMIT ? OFFSET ?";
    public static final String SAVE_LOT = "INSERT INTO lot (id, name, start_sell_date, price, status, seller_id, buyer_id, description, end_date_sell) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE id=VALUES(id), name=VALUES(name)," +
            "start_sell_date=VALUES(start_sell_date), price=VALUES(price), status=VALUES(status), seller_id=VALUES(seller_id), " +
            "buyer_id=VALUES(buyer_id), description=VALUES(description), end_date_sell=VALUES(end_date_sell)";
    public static final String GET_ALL_LOTS_BY_ID = "SELECT * FROM lot WHERE seller_id = ?";
    public static final String GET_ALL_LOTS_BY_STATUS_AND_ID = "SELECT * FROM lot JOIN lot_Bet ON lot.id = lot_bet.lot_id WHERE lot_bet.buyer_id = ? AND lot.status = ?";
    public static final String GET_AMOUNT_OF_ROWS_BY_STATUS = "SELECT COUNT(*) AS count FROM lot WHERE status = ?";

    public LotDaoImpl(ProxyConnection proxyConnection) {
        super(proxyConnection);
    }

    @Override
    public void save(Lot item) throws DaoException {
        super.save(item, SAVE_LOT);
    }

    @Override
    public List<Lot> getAllUserLotsById(long id) throws DaoException {
        return executeGetQuery(GET_ALL_LOTS_BY_ID, new LotRowMapper(), id);
    }

    @Override
    public int getAmountOfRows(String status) throws DaoException {
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(GET_AMOUNT_OF_ROWS_BY_STATUS)) {
            preparedStatement.setObject(1, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("count");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Lot> getAllLotsStatusAndUserId(long id, String status) throws DaoException {
        return executeGetQuery(GET_ALL_LOTS_BY_STATUS_AND_ID, new LotRowMapper(), id, status);
    }

    @Override
    protected String getTableName() {
        return Lot.TABLE;
    }

    @Override
    public List<Lot> findByStatus(String status, int limit, int offset) throws DaoException {
        return executeGetQuery(GET_BY_STATUS, new LotRowMapper(), status, limit, offset);
    }
}
