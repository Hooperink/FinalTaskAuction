package epam.by.Auction.dao;

import epam.by.Auction.connection.ConnectionPool;
import epam.by.Auction.dao.impl.DaoHelper;

public class DaoHelperFactory {

    public DaoHelper create() {
        return new DaoHelper(ConnectionPool.getInstance());
    }
}
