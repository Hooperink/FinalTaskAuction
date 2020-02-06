package epam.by.connection;

import epam.by.exception.NoConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static epam.by.connection.ConnectionConfig.*;

public class ConnectionFactory {

    public final static Logger logger = LogManager.getLogger(ConnectionFactory.class.getName());

    public static ProxyConnection create(ConnectionPool connectionPool) {
        Connection connection = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(JDBC_DB_URL, JDBC_USER, JDBC_PASS);
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
