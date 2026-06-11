package drsenhanced.model;

/**
 * Represents a notification sent to a system user.
 * Notifications are used for live updates about incidents and missions.
 */
public class Notification {

    private int notificationId;
    private int userId;
    private String message;
    private String timestamp;
    private boolean read;

    /**
     * Default constructor.
     */
    public Notification() {
    }

    /**
     * Creates a user notification.
     *
     * @param notificationId the notification ID
     * @param userId the receiving user ID
     * @param message the notification message
     * @param timestamp the notification timestamp
     * @param read true if the notification has been read
     */
    public Notification(int notificationId, int userId, String message,
            String timestamp, boolean read) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.message = message;
        this.timestamp = timestamp;
        this.read = read;
    }

    public int getNotificationId() {
        return this.notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isRead() {
        return this.read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
    @Override
    public String toString() {
    return "Notification{"
            + "notificationId=" + notificationId
            + ", userId=" + userId
            + ", message='" + message + '\''
            + ", timestamp='" + timestamp + '\''
            + ", read=" + read
            + '}';
}
}