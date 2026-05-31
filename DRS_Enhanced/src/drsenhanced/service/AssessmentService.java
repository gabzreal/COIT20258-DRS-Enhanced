/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drsenhanced.service;

/**
 * Handles disaster assessment operations.
 *
 * @author Hajera Begum Shaik - 12281382
 */
public class AssessmentService {

    /**
     * Determines severity level based on assessment score.
     *
     * @param severityScore severity score from 1-10
     * @return severity level
     */
    public String determineSeverityLevel(int severityScore) {

        if (severityScore >= 8) {
            return "HIGH";
        }

        if (severityScore >= 5) {
            return "MEDIUM";
        }

        return "LOW";
    }

    /**
     * Checks whether immediate response is required.
     *
     * @param severityScore severity score
     * @return true if immediate response required
     */
    public boolean requiresImmediateResponse(int severityScore) {
        return severityScore >= 8;
    }
}
