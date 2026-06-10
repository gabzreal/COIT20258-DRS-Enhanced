package drsenhanced.dao;

import drsenhanced.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Assigns a worker and records a dispatch as one transaction.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class DispatchWorkflowDAO {

    public record DispatchResult(int dispatchId, int workerId,
            String workerUsername) {
    }

    public DispatchResult dispatchIncident(
            int incidentId, int managerId, String resourceType)
            throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Worker worker = findWorker(connection);
                int dispatchId = createDispatch(
                        connection, incidentId, resourceType);
                assignIncident(connection, incidentId, worker.username());
                createResponseLog(
                        connection, incidentId, managerId, resourceType);
                createNotification(
                        connection, worker.userId(), incidentId);
                connection.commit();
                return new DispatchResult(
                        dispatchId, worker.userId(), worker.username());
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    private Worker findWorker(Connection connection) throws SQLException {
        String sql = "SELECT u.user_id, u.username FROM users u "
                + "WHERE LOWER(REPLACE(u.role, ' ', '')) = 'emergencyworker' "
                + "AND NOT EXISTS (SELECT 1 FROM incidents i "
                + "WHERE i.assigned_worker = u.username "
                + "AND i.status NOT IN ('RESOLVED', 'CLOSED')) "
                + "ORDER BY u.user_id LIMIT 1 FOR UPDATE";
        try (PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery()) {
            if (result.next()) {
                return new Worker(
                        result.getInt("user_id"),
                        result.getString("username"));
            }
        }
        throw new SQLException("No available emergency worker");
    }

    private int createDispatch(Connection connection, int incidentId,
            String resourceType) throws SQLException {
        String sql = "INSERT INTO resource_dispatch "
                + "(incident_id, resource_type, status) "
                + "VALUES (?, ?, 'DISPATCHED')";
        try (PreparedStatement statement = connection.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, incidentId);
            statement.setString(2, resourceType);
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        throw new SQLException("No dispatch ID returned");
    }

    private void assignIncident(Connection connection, int incidentId,
            String workerUsername) throws SQLException {
        String sql = "UPDATE incidents SET assigned_worker = ?, "
                + "status = 'RESPONDING' WHERE incident_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, workerUsername);
            statement.setInt(2, incidentId);
            if (statement.executeUpdate() != 1) {
                throw new SQLException("Incident was not assigned");
            }
        }
    }

    private void createResponseLog(Connection connection, int incidentId,
            int managerId, String resourceType) throws SQLException {
        String sql = "INSERT INTO response_logs "
                + "(incident_id, action, performed_by, details) "
                + "VALUES (?, 'Resources dispatched', ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, incidentId);
            statement.setInt(2, managerId);
            statement.setString(3, resourceType);
            statement.executeUpdate();
        }
    }

    private void createNotification(Connection connection, int workerId,
            int incidentId) throws SQLException {
        String sql = "INSERT INTO notifications (user_id, message) "
                + "VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, workerId);
            statement.setString(2,
                    "You have been assigned to incident " + incidentId);
            statement.executeUpdate();
        }
    }

    private record Worker(int userId, String username) {
    }
}
