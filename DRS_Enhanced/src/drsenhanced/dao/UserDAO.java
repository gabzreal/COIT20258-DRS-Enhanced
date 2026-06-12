package drsenhanced.dao;

import drsenhanced.database.DatabaseConnection;
import drsenhanced.model.User;
import drsenhanced.util.PasswordUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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

    public int createUser(String username,
            String passwordHash,
            String fullName,
            String role) {

        String sql = "INSERT INTO users "
                + "(username, password_hash, full_name, role) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection connection
                = DatabaseConnection.getConnection(); PreparedStatement statement
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
        String sql = "SELECT user_id,\n"
                + "       username,\n"
                + "       password_hash,\n"
                + "       full_name,\n"
                
                + "       role "
                + "FROM users WHERE username = ?";

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement
                = connection.prepareStatement(sql)) {
            statement.setString(1, username);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return Optional.of(mapUser(result));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error finding user: " + e.getMessage());
        }
        return Optional.empty();
    }

    public Optional<User> findById(int userId) {
        String sql = "SELECT user_id,\n"
                + "       username,\n"
                + "       password_hash,\n"
                + "       full_name,\n"
                
                + "       role "
                + "FROM users WHERE user_id = ?";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement
                = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet result = statement.executeQuery()) {
                return result.next()
                        ? Optional.of(mapUser(result))
                        : Optional.empty();
            }
        } catch (SQLException e) {
            System.out.println("Error finding user: " + e.getMessage());
            return Optional.empty();
        }
    }

    public List<User> findByRole(String role) {
        String sql = "SELECT user_id,\n"
                + "       username,\n"
                + "       password_hash,\n"
                + "       full_name,\n"
                
                + "       role "
                + "FROM users WHERE role = ? ORDER BY username";
        List<User> users = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement
                = connection.prepareStatement(sql)) {
            statement.setString(1, role);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    users.add(mapUser(result));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error finding users: " + e.getMessage());
        }
        return users;
    }

    public Optional<User> authenticate(String username, String password) {
        String passwordHash = PasswordUtil.hashPassword(password);
        return findByUsername(username)
                .filter(user -> passwordHash != null
                && passwordHash.equals(user.getPassword()));
    }

    private User mapUser(ResultSet result) throws SQLException {
        return new User(
                result.getInt("user_id"),
                result.getString("username"),
                result.getString("password_hash"),
               
                result.getString("role"));
    }
}
