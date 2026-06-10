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
 * EvacuationRecordDAO handles database operations
 * related to evacuation records.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class EvacuationRecordDAO {

    public record EvacuationRecord(
            int evacuationId,
            int reportId,
            int shelterId,
            int peopleAffected,
            boolean evacuationRequired,
            int assignedPeople) {
    }

    public int create(EvacuationRecord record) throws SQLException {
        String sql = "INSERT INTO evacuation_records (report_id, shelter_id, "
                + "people_affected, evacuation_required, assigned_people) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, record.reportId());
            statement.setInt(2, record.shelterId());
            statement.setInt(3, record.peopleAffected());
            statement.setBoolean(4, record.evacuationRequired());
            statement.setInt(5, record.assignedPeople());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : 0;
            }
        }
    }

    public List<EvacuationRecord> findByReportId(int reportId)
            throws SQLException {
        String sql = "SELECT evacuation_id, report_id, shelter_id, "
                + "people_affected, evacuation_required, assigned_people "
                + "FROM evacuation_records WHERE report_id = ? "
                + "ORDER BY evacuation_id";
        List<EvacuationRecord> records = new ArrayList<>();
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

    private EvacuationRecord mapRecord(ResultSet result) throws SQLException {
        return new EvacuationRecord(
                result.getInt("evacuation_id"),
                result.getInt("report_id"),
                result.getInt("shelter_id"),
                result.getInt("people_affected"),
                result.getBoolean("evacuation_required"),
                result.getInt("assigned_people"));
    }
}
