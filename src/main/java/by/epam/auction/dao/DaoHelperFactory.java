package by.epam.auction.dao;

import by.epam.auction.dao.impl.DaoHelper;
import by.epam.auction.connection.ConnectionPool;

public class DaoHelperFactory {

    public DaoHelper create() {
        return new DaoHelper(ConnectionPool.getInstance());
    }
}
