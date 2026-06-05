package drsenhanced.dao;

import drsenhanced.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * UserDAO handles database operations related to users.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class UserDAO {

    /**
     * Inserts a new user into the database.
     *
     * @param username username of the user
     * @param passwordHash hashed password
     * @param fullName full name of the user
     * @param role user role
     * @return true if insert successful, otherwise false
     */
    public boolean addUser(String username,
            String passwordHash,
            String fullName,
            String role) {

        String sql = "INSERT INTO users "
                + "(username, password_hash, full_name, role) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection connection
                = DatabaseConnection.getConnection();

                PreparedStatement statement
                = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, passwordHash);
            statement.setString(3, fullName);
            statement.setString(4, role);

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {

            System.out.println("Error adding user: "
                    + e.getMessage());

            return false;
        }
    }
}