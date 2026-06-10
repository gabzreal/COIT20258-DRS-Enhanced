package drsenhanced.dao;

import drsenhanced.database.DatabaseConnection;
import drsenhanced.model.Notification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles notification persistence and read-state updates.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class NotificationDAO {

    public int create(Notification notification) throws SQLException {
        String sql = "INSERT INTO notifications (user_id, message) "
                + "VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, notification.getUserId());
            statement.setString(2, notification.getMessage());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    notification.setNotificationId(keys.getInt(1));
                    return notification.getNotificationId();
                }
            }
        }
        return 0;
    }

    public List<Notification> findUnreadByUserId(int userId)
            throws SQLException {
        String sql = "SELECT notification_id, user_id, message, created_at, "
                + "is_read FROM notifications "
                + "WHERE user_id = ? AND is_read = FALSE "
                + "ORDER BY created_at DESC";
        List<Notification> notifications = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    notifications.add(mapNotification(result));
                }
            }
        }
        return notifications;
    }

    public List<Notification> findByUserId(int userId) throws SQLException {
        String sql = "SELECT notification_id, user_id, message, created_at, "
                + "is_read FROM notifications WHERE user_id = ? "
                + "ORDER BY created_at DESC";
        List<Notification> notifications = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    notifications.add(mapNotification(result));
                }
            }
        }
        return notifications;
    }

    public boolean markAsRead(int notificationId) throws SQLException {
        String sql = "UPDATE notifications SET is_read = TRUE "
                + "WHERE notification_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, notificationId);
            return statement.executeUpdate() == 1;
        }
    }

    private Notification mapNotification(ResultSet result)
            throws SQLException {
        return new Notification(
                result.getInt("notification_id"),
                result.getInt("user_id"),
                result.getString("message"),
                result.getTimestamp("created_at").toString(),
                result.getBoolean("is_read"));
    }
}
