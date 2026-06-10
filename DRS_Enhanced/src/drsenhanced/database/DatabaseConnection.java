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

    private static final String DEFAULT_URL
            = "jdbc:mysql://localhost:3306/drs_enhanced"
            + "?useSSL=false&allowPublicKeyRetrieval=true"
            + "&serverTimezone=Australia/Sydney";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASSWORD = "pass";

    /**
     * Returns a connection to the MySQL database.
     *
     * @return Connection object
     * @throws SQLException if database connection fails
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                getUrl(), getUser(), getPassword());
    }

    static String getUrl() {
        return getSetting("DRS_DB_URL", DEFAULT_URL);
    }

    static String getUser() {
        return getSetting("DRS_DB_USER", DEFAULT_USER);
    }

    static String getPassword() {
        return getSetting("DRS_DB_PASSWORD", DEFAULT_PASSWORD);
    }

    private static String getSetting(String name, String defaultValue) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            value = System.getProperty(name);
        }
        return value == null || value.isBlank() ? defaultValue : value;
    }
}
