package drsenhanced.database;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * DatabaseConnectionTest is used to test if the Java application
 * can successfully connect to the MySQL database.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class DatabaseConnectionTest {

    /**
     * Main method used to test the database connection.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        try {

            Connection connection = DatabaseConnection.getConnection();

            if (connection != null) {

                System.out.println("Database connection successful.");
                connection.close();
            }

        } catch (SQLException e) {

            System.out.println("Database connection failed.");
            System.out.println("Error: " + e.getMessage());
        }
    }
}