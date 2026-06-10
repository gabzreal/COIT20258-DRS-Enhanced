package drsenhanced.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Verifies automatic setup against a newly created test database.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class DatabaseInitializerTest {

    private static final String TEST_DATABASE
            = "drs_enhanced_initializer_test";
    private static final String OPTIONS
            = "?useSSL=false&allowPublicKeyRetrieval=true"
            + "&serverTimezone=Australia/Sydney";
    private static final String SERVER_URL
            = "jdbc:mysql://localhost:3306/" + OPTIONS;
    private static final String TEST_URL
            = "jdbc:mysql://localhost:3306/" + TEST_DATABASE + OPTIONS;

    public static void main(String[] args) throws Exception {
        String user = setting("DRS_DB_USER", "root");
        String password = setting("DRS_DB_PASSWORD", "pass");

        System.setProperty("DRS_DB_URL", TEST_URL);
        System.setProperty("DRS_DB_USER", user);
        System.setProperty("DRS_DB_PASSWORD", password);

        dropDatabase(user, password);
        try {
            DatabaseInitializer.initialize();
            DatabaseInitializer.initialize();
            verifySetup();
            System.out.println("Database initializer test passed.");
        } finally {
            dropDatabase(user, password);
        }
    }

    private static void verifySetup() throws Exception {
        try (Connection connection = DatabaseConnection.getConnection();
                Statement statement = connection.createStatement()) {
            require(count(statement,
                    "SELECT COUNT(*) FROM information_schema.tables "
                    + "WHERE table_schema = '" + TEST_DATABASE + "'") == 13,
                    "Required tables were not created");
            require(count(statement,
                    "SELECT COUNT(*) FROM users WHERE username IN "
                    + "('citizen', 'manager', 'worker')") == 3,
                    "Demo users were not created exactly once");
            require(count(statement,
                    "SELECT COUNT(*) FROM shelters WHERE name IN "
                    + "('Town Hall Shelter', "
                    + "'Docklands Community Centre')") == 2,
                    "Demo shelters were not created exactly once");
        }
    }

    private static int count(Statement statement, String sql)
            throws Exception {
        try (ResultSet result = statement.executeQuery(sql)) {
            result.next();
            return result.getInt(1);
        }
    }

    private static void dropDatabase(String user, String password)
            throws Exception {
        try (Connection connection = DriverManager.getConnection(
                SERVER_URL, user, password);
                Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "DROP DATABASE IF EXISTS " + TEST_DATABASE);
        }
    }

    private static String setting(String name, String fallback) {
        String value = System.getenv(name);
        return value == null || value.isBlank() ? fallback : value;
    }

    private static void require(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }
}
