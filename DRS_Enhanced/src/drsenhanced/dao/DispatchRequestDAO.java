package drsenhanced.dao;

import drsenhanced.database.DatabaseConnection;
import drsenhanced.model.DispatchRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Persists resource dispatch requests for incidents.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class DispatchRequestDAO {

    public int create(DispatchRequest request) throws SQLException {
        String sql = "INSERT INTO resource_dispatch "
                + "(incident_id, resource_type, status) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, request.getIncidentId());
            statement.setString(2, request.getResourceType());
            statement.setString(3, request.getStatus());
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    request.setDispatchId(keys.getInt(1));
                    return request.getDispatchId();
                }
            }
        }
        return 0;
    }

    public List<DispatchRequest> findByIncidentId(int incidentId)
            throws SQLException {
        String sql = "SELECT dispatch_id, incident_id, resource_type, status "
                + "FROM resource_dispatch WHERE incident_id = ? "
                + "ORDER BY dispatch_id";
        List<DispatchRequest> requests = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, incidentId);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    requests.add(mapRequest(result));
                }
            }
        }
        return requests;
    }

    public List<DispatchRequest> findPending() throws SQLException {
        String sql = "SELECT dispatch_id, incident_id, resource_type, status "
                + "FROM resource_dispatch "
                + "WHERE status IN ('REQUESTED', 'PENDING') "
                + "ORDER BY dispatch_id";
        List<DispatchRequest> requests = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                requests.add(mapRequest(result));
            }
        }
        return requests;
    }

    public boolean updateStatus(int dispatchId, String status)
            throws SQLException {
        String sql = "UPDATE resource_dispatch SET status = ? "
                + "WHERE dispatch_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setInt(2, dispatchId);
            return statement.executeUpdate() == 1;
        }
    }

    private DispatchRequest mapRequest(ResultSet result) throws SQLException {
        return new DispatchRequest(
                result.getInt("dispatch_id"),
                result.getInt("incident_id"),
                result.getString("resource_type"),
                result.getString("status"));
    }
}
