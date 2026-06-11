package drsenhanced.dao;

import drsenhanced.database.DatabaseConnection;
import drsenhanced.model.DisasterReport;
import drsenhanced.model.Incident;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

/**
 * Coordinates database operations that must succeed as one workflow.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class DisasterWorkflowDAO {

    public DisasterReport createIncidentReport(
            int citizenId, Incident incident) throws SQLException {
        String incidentSql = "INSERT INTO incidents "
                + "(type, location, severity, status, assigned_worker) "
                + "VALUES (?, ?, ?, ?, ?)";
        String reportSql = "INSERT INTO disaster_reports "
                + "(citizen_id, incident_id, disaster_type, location) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int incidentId = insertIncident(
                        connection, incidentSql, incident);
                int reportId = insertReport(
                        connection, reportSql, citizenId, incidentId, incident);
                connection.commit();

                incident.setIncidentId(incidentId);
                return new DisasterReport(reportId, citizenId, incidentId);
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    private int insertIncident(Connection connection, String sql,
            Incident incident) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, incident.getType());
            statement.setString(2, incident.getLocation());
            statement.setString(3, incident.getSeverity());
            statement.setString(4, incident.getStatus());
            statement.setString(5, incident.getAssignedWorker());
            statement.executeUpdate();
            return generatedId(statement, "incident");
        }
    }

    private int insertReport(Connection connection, String sql,
            int citizenId, int incidentId, Incident incident)
            throws SQLException {

        try (PreparedStatement statement = connection.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS)) {

            if (citizenId > 0) {
                statement.setInt(1, citizenId);
            } else {
                statement.setNull(1, Types.INTEGER);
            }

            statement.setInt(2, incidentId);
            statement.setString(3, incident.getType());
            statement.setString(4, incident.getLocation());

            statement.executeUpdate();

            return generatedId(statement, "disaster report");
        }
    }

    private int generatedId(PreparedStatement statement, String entity)
            throws SQLException {
        try (ResultSet keys = statement.getGeneratedKeys()) {
            if (keys.next()) {
                return keys.getInt(1);
            }
        }
        throw new SQLException("No generated ID returned for " + entity);
    }
}
