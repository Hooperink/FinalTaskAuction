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

        private final static String JDBC_DRIVER = "JDBC_DRIVER";
        private final static String JDBC_DB_URL = "JDBC_DB_URL";
        private final static String JDBC_USER = "JDBC_USER";
        private final static String JDBC_PASS = "JDBC_PASS";

        private String driverName;
        private String dbURL;
        private String user;
        private String password;

        public ConnectionFactory () {
            Map<String, String> properties = JDBC_PropertiesReader.getProperties("src/main/resources/connection.properties");
            driverName = properties.get(JDBC_DRIVER);
            dbURL = properties.get(JDBC_DB_URL);
            user = properties.get(JDBC_USER);
            password = properties.get(JDBC_PASS);
        }

    public ProxyConnection create(ConnectionPool connectionPool) {
        Connection connection;
        try {
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
