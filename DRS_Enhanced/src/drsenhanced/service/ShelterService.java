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

    public List<Shelter> getShelters() throws SQLException {
        return shelterDAO.findAll();
    }

    public List<Shelter> getAvailableShelters() throws SQLException {
        return shelterDAO.findAvailable();
    }

    public Optional<Shelter> getShelterById(int shelterId)
            throws SQLException {
        return shelterDAO.findById(shelterId);
    }

    public boolean updateCapacity(int shelterId, int occupancy)
            throws SQLException {
        return shelterDAO.updateOccupancy(shelterId, occupancy);
    }
}
