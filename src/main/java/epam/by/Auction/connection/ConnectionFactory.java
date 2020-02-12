package epam.by.Auction.connection;

import epam.by.Auction.exception.NoConnectionException;
import epam.by.Auction.utils.JDBC_PropertiesReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class ConnectionFactory {

    public final static Logger logger = LogManager.getLogger(ConnectionFactory.class.getName());

    public static ProxyConnection create(ConnectionPool connectionPool) {
        Connection connection;
        try {
            Map<String,String> properties = JDBC_PropertiesReader.getProperties();
            String driverName = properties.get("JDBC_DRIVER");
            String dbURL = properties.get("JDBC_DB_URL");
            String user = properties.get("JDBC_USER");
            String password = properties.get("JDBC_PASS");
            Class.forName(driverName);
            connection = DriverManager.getConnection(dbURL, user, password);
        } catch (ClassNotFoundException e) {
            logger.error("No such driver. ", e);
            throw new NoConnectionException(e);
        } catch (SQLException e) {
            logger.error("SQL connection error. ", e);
            throw new NoConnectionException(e);
        }
        return new ProxyConnection(connection, connectionPool);
    }
}
