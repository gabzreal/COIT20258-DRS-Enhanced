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
import java.util.Optional;

/**
 * AssessmentDAO handles database operations
 * related to assessments.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class AssessmentDAO {

    public record Assessment(
            int assessmentId,
            int reportId,
            String severityLevel,
            String priorityLevel,
            int priorityScore,
            String impactSummary) {
    }

    public int create(Assessment assessment) throws SQLException {
        String sql = "INSERT INTO assessments (report_id, severity_level, "
                + "priority_level, priority_score, impact_summary) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, assessment.reportId());
            statement.setString(2, assessment.severityLevel());
            statement.setString(3, assessment.priorityLevel());
            statement.setInt(4, assessment.priorityScore());
            statement.setString(5, assessment.impactSummary());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : 0;
            }
        }
    }

    public Optional<Assessment> findByReportId(int reportId)
            throws SQLException {
        String sql = "SELECT assessment_id, report_id, severity_level, "
                + "priority_level, priority_score, impact_summary "
                + "FROM assessments WHERE report_id = ? "
                + "ORDER BY created_at DESC LIMIT 1";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, reportId);
            try (ResultSet result = statement.executeQuery()) {
                return result.next()
                        ? Optional.of(mapAssessment(result))
                        : Optional.empty();
            }
        }
    }

    private Assessment mapAssessment(ResultSet result) throws SQLException {
        return new Assessment(
                result.getInt("assessment_id"),
                result.getInt("report_id"),
                result.getString("severity_level"),
                result.getString("priority_level"),
                result.getInt("priority_score"),
                result.getString("impact_summary"));
    }
}
