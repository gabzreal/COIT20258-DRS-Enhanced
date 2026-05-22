package drsenhanced.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DatabaseConnection handles the MySQL database connection
 * for the DRS-Enhanced system.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class DatabaseConnection {

    /**
     * Database URL.
     */
    private static final String URL
            = "jdbc:mysql://localhost:3306/drs_enhanced";

    /**
     * MySQL username.
     */
    private static final String USER = "root";

    /**
     * MySQL password.
     */
    private static final String PASSWORD = "root";

    /**
     * Returns a connection to the MySQL database.
     *
     * @return Connection object
     * @throws SQLException if database connection fails
     */
    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}