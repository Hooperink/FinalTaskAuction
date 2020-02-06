package epam.by.dao;

import epam.by.connection.ConnectionPool;
import epam.by.connection.ProxyConnection;
import epam.by.exception.DaoException;

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
        try {
            proxyConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            //TODO: LOG
        }
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
