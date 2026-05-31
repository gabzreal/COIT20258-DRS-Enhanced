/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drsenhanced.service;

/**
 * Handles evacuation-related operations.
 *
 * @author Hajera Begum Shaik - 12281382
 */
public class EvacuationService {

    /**
     * Checks if evacuation is required based on severity.
     *
     * @param severityScore disaster severity score
     * @return true if evacuation is required
     */
    public boolean requiresEvacuation(int severityScore) {
        return severityScore >= 7;
    }

    /**
     * Calculates evacuation completion percentage.
     *
     * @param evacuatedPeople number of evacuated people
     * @param totalPeople total affected people
     * @return evacuation completion percentage
     */
    public double calculateEvacuationProgress(int evacuatedPeople, int totalPeople) {

        if (totalPeople <= 0) {
            return 0;
        }

        return ((double) evacuatedPeople / totalPeople) * 100;
    }

    /**
     * Determines evacuation status.
     *
     * @param evacuatedPeople evacuated people
     * @param totalPeople total people
     * @return status message
     */
    public String getEvacuationStatus(int evacuatedPeople, int totalPeople) {

        if (evacuatedPeople >= totalPeople) {
            return "COMPLETED";
        }

        if (evacuatedPeople > 0) {
            return "IN PROGRESS";
        }

        return "NOT STARTED";
    }
}
