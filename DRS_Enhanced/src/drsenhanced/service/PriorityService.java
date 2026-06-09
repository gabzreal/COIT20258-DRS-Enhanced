/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drsenhanced.service;

/**
 * Handles disaster response prioritisation.
 *
 * @author Hajera Begum Shaik - 12281382
 */
public class PriorityService {

    /**
     * Calculates priority score.
     *
     * @param severity disaster severity
     * @param affectedPeople number of affected people
     * @return priority score
     */
    public int calculatePriority(int severity, int affectedPeople) {
        return severity + (affectedPeople / 10);
    }

    /**
     * Determines priority level.
     *
     * @param priorityScore calculated priority score
     * @return priority level
     */
    public String getPriorityLevel(int priorityScore) {

        if (priorityScore >= 15) {
            return "CRITICAL";
        }

        if (priorityScore >= 10) {
            return "HIGH";
        }

        if (priorityScore >= 5) {
            return "MEDIUM";
        }

        return "LOW";
    }
}
