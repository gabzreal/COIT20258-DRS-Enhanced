/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drsenhanced.dao;

import drsenhanced.database.DatabaseConnection;
import drsenhanced.model.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * ResourceDAO handles database operations
 * related to resources.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class ResourceDAO {

    public int createDispatch(Resource resource) throws SQLException {
        String sql = "INSERT INTO resource_dispatch "
                + "(incident_id, resource_type, status) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, resource.getIncidentId());
            statement.setString(2, resource.getResourceType());
            statement.setString(3, resource.getStatus());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    resource.setDispatchId(keys.getInt(1));
                    return resource.getDispatchId();
                }
            }
        }
        return 0;
    }

    public List<Resource> findByIncidentId(int incidentId)
            throws SQLException {
        String sql = "SELECT dispatch_id, incident_id, resource_type, status "
                + "FROM resource_dispatch WHERE incident_id = ? "
                + "ORDER BY dispatch_id";
        List<Resource> resources = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, incidentId);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    resources.add(new Resource(
                            result.getInt("dispatch_id"),
                            result.getInt("incident_id"),
                            result.getString("resource_type"),
                            result.getString("status")));
                }
            }
        }
        return resources;
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
}
