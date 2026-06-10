/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drsenhanced.dao;

import drsenhanced.database.DatabaseConnection;
import drsenhanced.model.DisasterReport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DisasterReportDAO handles database operations
 * related to disaster reports.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class DisasterReportDAO {

    public int create(DisasterReport report) throws SQLException {
        String sql = "INSERT INTO disaster_reports "
                + "(citizen_id, incident_id) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, report.getCitizenId());
            if (report.getIncidentId() > 0) {
                statement.setInt(2, report.getIncidentId());
            } else {
                statement.setNull(2, java.sql.Types.INTEGER);
            }
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    report.setReportId(keys.getInt(1));
                    return report.getReportId();
                }
            }
        }
        return 0;
    }

    public List<DisasterReport> findByCitizenId(int citizenId)
            throws SQLException {
        String sql = "SELECT report_id, citizen_id, incident_id "
                + "FROM disaster_reports WHERE citizen_id = ? "
                + "ORDER BY report_id DESC";
        List<DisasterReport> reports = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, citizenId);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    reports.add(new DisasterReport(
                            result.getInt("report_id"),
                            result.getInt("citizen_id"),
                            result.getInt("incident_id")));
                }
            }
        }
        return reports;
    }

    public boolean linkToIncident(int reportId, int incidentId)
            throws SQLException {
        String sql = "UPDATE disaster_reports SET incident_id = ? "
                + "WHERE report_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, incidentId);
            statement.setInt(2, reportId);
            return statement.executeUpdate() == 1;
        }
    }
}
