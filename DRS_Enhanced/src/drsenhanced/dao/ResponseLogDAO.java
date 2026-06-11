/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drsenhanced.dao;

import drsenhanced.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * ResponseLogDAO handles database operations related to response logs.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class ResponseLogDAO {

    public record ResponseLog(
            int logId,
            int incidentId,
            String action,
            int performedBy,
            String details,
            String timestamp) {

    }

    public int create(ResponseLog log) throws SQLException {
        String sql = "INSERT INTO response_logs "
                + "(incident_id, action, performed_by, details) "
                + "VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, log.incidentId());
            statement.setString(2, log.action());
            statement.setInt(3, log.performedBy());
            statement.setString(4, log.details());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : 0;
            }
        }
    }

    public List<ResponseLog> findRecent(int limit) throws SQLException {
        String sql = "SELECT log_id, incident_id, action, performed_by, "
                + "details, created_at FROM response_logs "
                + "ORDER BY created_at DESC LIMIT ?";
        List<ResponseLog> logs = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Math.max(1, limit));
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    logs.add(mapLog(result));
                }
            }
        }
        return logs;
    }

    public List<ResponseLog> findByIncidentId(int incidentId)
            throws SQLException {
        String sql = "SELECT log_id, incident_id, action, performed_by, "
                + "details, created_at FROM response_logs "
                + "WHERE incident_id = ? ORDER BY created_at DESC";
        List<ResponseLog> logs = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, incidentId);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    logs.add(mapLog(result));
                }
            }
        }
        return logs;
    }

    private ResponseLog mapLog(ResultSet result) throws SQLException {
        return new ResponseLog(
                result.getInt("log_id"),
                result.getInt("incident_id"),
                result.getString("action"),
                result.getInt("performed_by"),
                result.getString("details"),
                result.getTimestamp("created_at").toString());
    }

    public int countTodayResponses() throws SQLException {

        String sql
                = "SELECT COUNT(*) "
                + "FROM response_logs "
                + "WHERE DATE(created_at) = CURDATE()";

        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement
                = connection.prepareStatement(sql); ResultSet result
                = statement.executeQuery()) {

            if (result.next()) {
                return result.getInt(1);
            }

            return 0;
        }
    }
}
