package epam.by.Auction.connection;

public class ConnectionConfig {
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String JDBC_DB_URL = "jdbc:mysql://localhost:3306/auction?" + "useUnicode=true&serverTimezone=UTC&useSSL=true&verifyServerCertificate=false";
    public static final String JDBC_USER = "root";
    public static final String JDBC_PASS = "root";
    private ConnectionConfig () {}
}
