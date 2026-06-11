/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drsenhanced.dao;

import drsenhanced.database.DatabaseConnection;
import drsenhanced.model.Shelter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * ShelterDAO handles database operations
 * related to shelters.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class ShelterDAO {

    public int create(Shelter shelter, String location) throws SQLException {
        String sql = "INSERT INTO shelters "
                + "(name, location, capacity, current_occupancy) "
                + "VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, shelter.getName());
            statement.setString(2, location);
            statement.setInt(3, shelter.getCapacity());
            statement.setInt(4, shelter.getCurrentOccupancy());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    shelter.setShelterId(keys.getInt(1));
                    return shelter.getShelterId();
                }
            }
        }
        return 0;
    }

    public List<Shelter> findAll() throws SQLException {
        String sql = "SELECT shelter_id, name, capacity, current_occupancy "
                + "FROM shelters ORDER BY name";
        List<Shelter> shelters = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                shelters.add(mapShelter(result));
            }
        }
        return shelters;
    }

    public Optional<Shelter> findById(int shelterId) throws SQLException {
        String sql = "SELECT shelter_id, name, capacity, current_occupancy "
                + "FROM shelters WHERE shelter_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, shelterId);
            try (ResultSet result = statement.executeQuery()) {
                return result.next()
                        ? Optional.of(mapShelter(result))
                        : Optional.empty();
            }
        }
    }

    public List<Shelter> findAvailable() throws SQLException {
        String sql = "SELECT shelter_id, name, capacity, current_occupancy "
                + "FROM shelters WHERE current_occupancy < capacity "
                + "ORDER BY name";
        List<Shelter> shelters = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                shelters.add(mapShelter(result));
            }
        }
        return shelters;
    }

    public boolean updateOccupancy(int shelterId, int occupancy)
            throws SQLException {
        String sql = "UPDATE shelters SET current_occupancy = ? "
                + "WHERE shelter_id = ? AND ? BETWEEN 0 AND capacity";
        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, occupancy);
            statement.setInt(2, shelterId);
            statement.setInt(3, occupancy);
            return statement.executeUpdate() == 1;
        }
    }

    private Shelter mapShelter(ResultSet result) throws SQLException {
        return new Shelter(
                result.getInt("shelter_id"),
                result.getString("name"),
                result.getInt("capacity"),
                result.getInt("current_occupancy"));
    }
}
