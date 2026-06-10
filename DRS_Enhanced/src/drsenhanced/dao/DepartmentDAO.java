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
 * DepartmentDAO handles database operations
 * related to departments.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class DepartmentDAO {

    public record Department(
            int departmentId,
            String name,
            String type,
            String contactInfo,
            String availabilityStatus,
            int availableUnits) {
    }

    public int create(Department department) throws SQLException {
        String sql = "INSERT INTO departments (department_name, "
                + "department_type, contact_info, availability_status, "
                + "available_units) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, department.name());
            statement.setString(2, department.type());
            statement.setString(3, department.contactInfo());
            statement.setString(4, department.availabilityStatus());
            statement.setInt(5, department.availableUnits());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : 0;
            }
        }
    }

    public List<Department> findAvailable() throws SQLException {
        String sql = "SELECT department_id, department_name, department_type, "
                + "contact_info, availability_status, available_units "
                + "FROM departments WHERE availability_status = 'Available' "
                + "AND available_units > 0 ORDER BY department_name";
        List<Department> departments = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                departments.add(mapDepartment(result));
            }
        }
        return departments;
    }

    public boolean updateAvailability(int departmentId,
            String status, int availableUnits) throws SQLException {
        String sql = "UPDATE departments SET availability_status = ?, "
                + "available_units = ? WHERE department_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setInt(2, availableUnits);
            statement.setInt(3, departmentId);
            return statement.executeUpdate() == 1;
        }
    }

    private Department mapDepartment(ResultSet result) throws SQLException {
        return new Department(
                result.getInt("department_id"),
                result.getString("department_name"),
                result.getString("department_type"),
                result.getString("contact_info"),
                result.getString("availability_status"),
                result.getInt("available_units"));
    }
}
