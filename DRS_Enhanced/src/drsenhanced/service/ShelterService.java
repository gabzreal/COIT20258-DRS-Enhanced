/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drsenhanced.service;

import drsenhanced.dao.ShelterDAO;
import drsenhanced.model.Shelter;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Manages shelter information and capacity.
 *
 * @author Hajera Begum Shaik - 12281382
 */
public class ShelterService {

    private final ShelterDAO shelterDAO = new ShelterDAO();

    /**
     * Retrieves all shelters.
     *
     * @return list of shelters
     * @throws SQLException if database access fails
     */
    public List<Shelter> getShelters() throws SQLException {
        return shelterDAO.findAll();
    }

    /**
     * Retrieves available shelters with remaining capacity.
     *
     * @return list of available shelters
     * @throws SQLException if database access fails
     */
    public List<Shelter> getAvailableShelters() throws SQLException {
        return shelterDAO.findAvailable();
    }

    /**
     * Retrieves a shelter by ID.
     *
     * @param shelterId shelter identifier
     * @return shelter if found
     * @throws SQLException if database access fails
     */
    public Optional<Shelter> getShelterById(int shelterId)
            throws SQLException {
        return shelterDAO.findById(shelterId);
    }

    /**
     * Updates shelter occupancy.
     *
     * @param shelterId shelter identifier
     * @param occupancy current occupancy
     * @return true if update succeeds
     * @throws SQLException if database access fails
     */
    public boolean updateOccupancy(int shelterId, int occupancy)
            throws SQLException {
        return shelterDAO.updateOccupancy(shelterId, occupancy);
    }
}
