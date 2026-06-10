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
 * CoordinationRecordDAO handles database operations
 * related to coordination records.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class CoordinationRecordDAO {

    public record CoordinationRecord(
            int coordinationId,
            int reportId,
            int departmentId,
            Integer resourceId,
            String assignedAction,
            String incidentStatus,
            String updateNote) {
    }

    public int create(CoordinationRecord record) throws SQLException {
        String sql = "INSERT INTO coordination_records (report_id, "
                + "department_id, resource_id, assigned_action, "
                + "incident_status, update_note) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, record.reportId());
            statement.setInt(2, record.departmentId());
            if (record.resourceId() == null) {
                statement.setNull(3, java.sql.Types.INTEGER);
            } else {
                statement.setInt(3, record.resourceId());
            }
            statement.setString(4, record.assignedAction());
            statement.setString(5, record.incidentStatus());
            statement.setString(6, record.updateNote());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : 0;
            }
        }
    }

    public List<CoordinationRecord> findByReportId(int reportId)
            throws SQLException {
        String sql = "SELECT coordination_id, report_id, department_id, "
                + "resource_id, assigned_action, incident_status, update_note "
                + "FROM coordination_records WHERE report_id = ? "
                + "ORDER BY coordination_id";
        List<CoordinationRecord> records = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, reportId);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    records.add(mapRecord(result));
                }
            }
        }
        return records;
    }

    private CoordinationRecord mapRecord(ResultSet result)
            throws SQLException {
        int resourceId = result.getInt("resource_id");
        return new CoordinationRecord(
                result.getInt("coordination_id"),
                result.getInt("report_id"),
                result.getInt("department_id"),
                result.wasNull() ? null : resourceId,
                result.getString("assigned_action"),
                result.getString("incident_status"),
                result.getString("update_note"));
    }
}
