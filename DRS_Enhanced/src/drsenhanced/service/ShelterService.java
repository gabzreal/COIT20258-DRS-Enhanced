/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drsenhanced.service;

/**
 * Handles shelter capacity and occupancy operations.
 *
 * @author Hajera Begum Shaik - 12281382
 */
public class ShelterService {

    /**
     * Checks whether a shelter has enough remaining capacity.
     *
     * @param currentOccupancy current number of occupants
     * @param totalCapacity maximum shelter capacity
     * @param incomingPeople number of people to assign
     * @return true if assignment is possible
     */
    public boolean hasAvailableCapacity(
            int currentOccupancy,
            int totalCapacity,
            int incomingPeople) {

        return (currentOccupancy + incomingPeople)
                <= totalCapacity;
    }

    /**
     * Calculates remaining shelter capacity.
     *
     * @param totalCapacity shelter capacity
     * @param currentOccupancy current occupancy
     * @return remaining spaces
     */
    public int calculateRemainingCapacity(
            int totalCapacity,
            int currentOccupancy) {

        return totalCapacity - currentOccupancy;
    }

    /**
     * Calculates occupancy percentage.
     *
     * @param currentOccupancy current occupancy
     * @param totalCapacity shelter capacity
     * @return occupancy percentage
     */
    public double calculateOccupancyPercentage(
            int currentOccupancy,
            int totalCapacity) {

        if (totalCapacity <= 0) {
            return 0;
        }

        return ((double) currentOccupancy / totalCapacity) * 100;
    }

    /**
     * Returns shelter status based on occupancy.
     *
     * @param currentOccupancy current occupancy
     * @param totalCapacity shelter capacity
     * @return shelter status
     */
    public String getShelterStatus(
            int currentOccupancy,
            int totalCapacity) {

        double occupancy =
                calculateOccupancyPercentage(
                        currentOccupancy,
                        totalCapacity);

        if (occupancy >= 100) {
            return "FULL";
        }

        if (occupancy >= 80) {
            return "NEAR CAPACITY";
        }

        return "AVAILABLE";
    }
}