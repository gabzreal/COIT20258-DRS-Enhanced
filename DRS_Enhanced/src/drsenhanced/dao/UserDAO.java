package drsenhanced.dao;

import drsenhanced.database.DatabaseConnection;
import drsenhanced.model.User;
import drsenhanced.util.PasswordUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

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
        return createUser(username, passwordHash, fullName, role) > 0;
    }

    public int createUser(String username, String passwordHash,
            String fullName, String role) {

        String sql = "INSERT INTO users "
                + "(username, password_hash, full_name, role) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection connection
                = DatabaseConnection.getConnection();

                PreparedStatement statement
                = connection.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, username);
            statement.setString(2, passwordHash);
            statement.setString(3, fullName);
            statement.setString(4, role);

            if (statement.executeUpdate() == 0) {
                return 0;
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : 0;
            }
        } catch (SQLException e) {

            System.out.println("Error adding user: "
                    + e.getMessage());

            return 0;
        }
    }

    public Optional<User> findByUsername(String username) {
        String sql = "SELECT user_id, username, password_hash, role "
                + "FROM users WHERE username = ?";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement
                = connection.prepareStatement(sql)) {
            statement.setString(1, username);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return Optional.of(new User(
                            result.getInt("user_id"),
                            result.getString("username"),
                            result.getString("password_hash"),
                            result.getString("role")));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error finding user: " + e.getMessage());
        }
        return Optional.empty();
    }

    public Optional<User> authenticate(String username, String password) {
        String passwordHash = PasswordUtil.hashPassword(password);
        return findByUsername(username)
                .filter(user -> passwordHash != null
                && passwordHash.equals(user.getPassword()));
    }
}
