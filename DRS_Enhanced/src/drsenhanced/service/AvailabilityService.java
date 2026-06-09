/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drsenhanced.service;

/**
 * Handles resource and department availability operations.
 *
 * @author Hajera Begum Shaik
 */
public class AvailabilityService {

    /**
     * Checks whether a resource is available.
     *
     * @param availableUnits available quantity
     * @return true if resource is available
     */
    public boolean checkResourceAvailability(int availableUnits) {
        return availableUnits > 0;
    }

    /**
     * Checks whether a department is operational.
     *
     * @param staffAvailable available staff members
     * @return true if department can operate
     */
    public boolean checkDepartmentAvailability(int staffAvailable) {
        return staffAvailable >= 2;
    }

    /**
     * Calculates availability percentage.
     *
     * @param available available resources
     * @param total total resources
     * @return availability percentage
     */
    public double calculateAvailabilityPercentage(int available, int total) {

        if (total <= 0) {
            return 0;
        }

        return ((double) available / total) * 100;
    }
}
