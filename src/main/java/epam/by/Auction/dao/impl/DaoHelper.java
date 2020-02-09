package epam.by.Auction.dao.impl;

import epam.by.Auction.connection.ConnectionPool;
import epam.by.Auction.connection.ProxyConnection;
import epam.by.Auction.dao.api.BetDao;
import epam.by.Auction.dao.api.LotDao;
import epam.by.Auction.dao.api.UserDao;
import epam.by.Auction.exception.DaoException;

import java.sql.SQLException;

public class DaoHelper implements AutoCloseable {

    private ProxyConnection proxyConnection;

    public DaoHelper(ConnectionPool pool) {
        this.proxyConnection = pool.getConnectionFromPool();
    }

    public UserDao createUserDao() {
        return new UserDaoImpl(proxyConnection);
    }

    public LotDao createLotDao() {
        return new LotDaoImpl(proxyConnection);
    }

    public BetDao createBetDao() {
        return new BetDaoImpl(proxyConnection);
    }

    @Override
    public void close() {
        proxyConnection.close();
    }

    public void startTransaction() throws DaoException {
        try {
            proxyConnection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void endTransaction() throws DaoException {
        try {
            proxyConnection.commit();
            proxyConnection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void rollbackQuery() throws DaoException {
        try {
            proxyConnection.rollback();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
