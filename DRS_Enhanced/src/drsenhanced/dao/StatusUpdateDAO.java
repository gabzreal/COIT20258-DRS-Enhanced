package drsenhanced.dao;

import drsenhanced.database.DatabaseConnection;
import drsenhanced.model.StatusUpdate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StatusUpdateDAO {

    public int create(StatusUpdate update) throws SQLException {
        String sql = "INSERT INTO status_updates "
                + "(incident_id, worker_id, message) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, update.getIncidentId());
            statement.setInt(2, update.getWorkerId());
            statement.setString(3, update.getMessage());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    update.setUpdateId(keys.getInt(1));
                    return update.getUpdateId();
                }
            }
        }
        return 0;
    }

    public List<StatusUpdate> findByIncidentId(int incidentId)
            throws SQLException {
        String sql = "SELECT update_id, incident_id, worker_id, message, "
                + "created_at FROM status_updates WHERE incident_id = ? "
                + "ORDER BY created_at DESC";
        List<StatusUpdate> updates = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, incidentId);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    updates.add(new StatusUpdate(
                            result.getInt("update_id"),
                            result.getInt("incident_id"),
                            result.getInt("worker_id"),
                            result.getString("message"),
                            result.getTimestamp("created_at").toString()));
                }
            }
        }
        return updates;
    }
}
