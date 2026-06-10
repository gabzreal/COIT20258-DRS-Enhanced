package drsenhanced.dao;

import drsenhanced.database.DatabaseConnection;
import drsenhanced.model.Incident;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Handles incident persistence and retrieval.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class IncidentDAO {

    public int create(Incident incident) throws SQLException {
        String sql = "INSERT INTO incidents "
                + "(type, location, severity, status, assigned_worker) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, incident.getType());
            statement.setString(2, incident.getLocation());
            statement.setString(3, incident.getSeverity());
            statement.setString(4, incident.getStatus());
            statement.setString(5, incident.getAssignedWorker());
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    incident.setIncidentId(keys.getInt(1));
                    return incident.getIncidentId();
                }
            }
        }
        return 0;
    }

    public Optional<Incident> findById(int incidentId) throws SQLException {
        String sql = "SELECT * FROM incidents WHERE incident_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, incidentId);
            try (ResultSet result = statement.executeQuery()) {
                return result.next()
                        ? Optional.of(mapIncident(result))
                        : Optional.empty();
            }
        }
    }

    public List<Incident> findAll() throws SQLException {
        String sql = "SELECT * FROM incidents ORDER BY incident_id DESC";
        List<Incident> incidents = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                incidents.add(mapIncident(result));
            }
        }
        return incidents;
    }

    public List<Incident> findActive() throws SQLException {
        String sql = "SELECT * FROM incidents "
                + "WHERE status NOT IN ('RESOLVED', 'CLOSED') "
                + "ORDER BY incident_id DESC";
        List<Incident> incidents = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                incidents.add(mapIncident(result));
            }
        }
        return incidents;
    }

    public List<Incident> findByAssignedWorker(String assignedWorker)
            throws SQLException {
        String sql = "SELECT * FROM incidents WHERE assigned_worker = ? "
                + "ORDER BY incident_id DESC";
        List<Incident> incidents = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, assignedWorker);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    incidents.add(mapIncident(result));
                }
            }
        }
        return incidents;
    }

    public boolean updateStatus(int incidentId, String status)
            throws SQLException {
        String sql = "UPDATE incidents SET status = ? WHERE incident_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setInt(2, incidentId);
            return statement.executeUpdate() == 1;
        }
    }

    public boolean assignWorker(int incidentId, String assignedWorker)
            throws SQLException {
        String sql = "UPDATE incidents SET assigned_worker = ? "
                + "WHERE incident_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, assignedWorker);
            statement.setInt(2, incidentId);
            return statement.executeUpdate() == 1;
        }
    }

    private Incident mapIncident(ResultSet result) throws SQLException {
        return new Incident(
                result.getInt("incident_id"),
                result.getString("type"),
                result.getString("location"),
                result.getString("severity"),
                result.getString("status"),
                result.getString("assigned_worker"));
    }
}
