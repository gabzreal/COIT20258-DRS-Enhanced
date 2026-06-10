package drsenhanced.service;

import drsenhanced.dao.NotificationDAO;
import drsenhanced.model.Notification;
import java.sql.SQLException;
import java.util.List;

/**
 * Provides user notification operations.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class NotificationService {

    private final NotificationDAO notificationDAO = new NotificationDAO();

    public int sendNotification(int userId, String message)
            throws SQLException {
        return notificationDAO.create(new Notification(
                0, userId, message, null, false));
    }

    public List<Notification> getNotifications(int userId)
            throws SQLException {
        return notificationDAO.findByUserId(userId);
    }
}
