package drsenhanced.database;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Creates the configured MySQL database, tables, and demo records when absent.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public final class DatabaseInitializer {

    private static final String SCHEMA_RESOURCE
            = "/drsenhanced/database/sql/schema.sql";
    private static final String DEMO_RESOURCE
            = "/drsenhanced/database/sql/demo_data.sql";

    private DatabaseInitializer() {
    }

    /**
     * Ensures the configured database is ready for the application.
     *
     * @throws SQLException when MySQL cannot be reached or initialized
     */
    public static void initialize() throws SQLException {
        DatabaseTarget target = DatabaseTarget.from(DatabaseConnection.getUrl());
        try (Connection connection = openDatabase(target)) {
            executeResource(connection, SCHEMA_RESOURCE);
            executeResource(connection, DEMO_RESOURCE);
        }
    }

    private static Connection openDatabase(DatabaseTarget target)
            throws SQLException {
        try {
            return DatabaseConnection.getConnection();
        } catch (SQLException e) {
            if (e.getErrorCode() != 1049) {
                throw e;
            }
            createDatabase(target);
            return DatabaseConnection.getConnection();
        }
    }

    private static void createDatabase(DatabaseTarget target)
            throws SQLException {
        try (Connection connection = DriverManager.getConnection(
                target.serverUrl(),
                DatabaseConnection.getUser(),
                DatabaseConnection.getPassword());
                Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "CREATE DATABASE IF NOT EXISTS `" + target.databaseName()
                    + "` CHARACTER SET utf8mb4"
                    + " COLLATE utf8mb4_unicode_ci");
        }
    }

    private static void executeResource(Connection connection, String resource)
            throws SQLException {
        String script = readResource(resource);
        StringBuilder executableSql = new StringBuilder();

        for (String line : script.split("\\R")) {
            if (!line.stripLeading().startsWith("--")) {
                executableSql.append(line).append('\n');
            }
        }

        try (Statement statement = connection.createStatement()) {
            for (String sql : executableSql.toString().split(";")) {
                if (!sql.isBlank()) {
                    statement.execute(sql);
                }
            }
        }
    }

    private static String readResource(String resource) throws SQLException {
        try (InputStream input
                = DatabaseInitializer.class.getResourceAsStream(resource)) {
            if (input == null) {
                throw new SQLException(
                        "Database setup resource is missing: " + resource);
            }
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new SQLException(
                    "Could not read database setup resource: " + resource, e);
        }
    }

    private record DatabaseTarget(String serverUrl, String databaseName) {

        private static DatabaseTarget from(String databaseUrl)
                throws SQLException {
            int queryStart = databaseUrl.indexOf('?');
            String baseUrl = queryStart >= 0
                    ? databaseUrl.substring(0, queryStart) : databaseUrl;
            String options = queryStart >= 0
                    ? databaseUrl.substring(queryStart) : "";
            int databaseSeparator = baseUrl.lastIndexOf('/');

            if (!baseUrl.startsWith("jdbc:mysql://")
                    || databaseSeparator < "jdbc:mysql://".length()
                    || databaseSeparator == baseUrl.length() - 1) {
                throw new SQLException(
                        "DRS_DB_URL must include a MySQL database name");
            }

            String databaseName = baseUrl.substring(databaseSeparator + 1);
            if (!databaseName.matches("[A-Za-z0-9_]+")) {
                throw new SQLException(
                        "Invalid database name in DRS_DB_URL: " + databaseName);
            }

            String serverUrl = baseUrl.substring(0, databaseSeparator + 1)
                    + options;
            return new DatabaseTarget(serverUrl, databaseName);
        }
    }
}
