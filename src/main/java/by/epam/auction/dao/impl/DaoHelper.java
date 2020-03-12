package by.epam.auction.dao.impl;

import by.epam.auction.connection.ConnectionPool;
import by.epam.auction.connection.ProxyConnection;
import by.epam.auction.dao.api.BetDao;
import by.epam.auction.dao.api.LotDao;
import by.epam.auction.dao.api.UserDao;
import by.epam.auction.exception.DaoException;

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
            rollbackQuery();
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
