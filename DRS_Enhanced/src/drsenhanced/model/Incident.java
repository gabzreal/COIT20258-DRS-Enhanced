package drsenhanced.model;

/**
 * Represents an incident created from a disaster report.
 * Incidents are assigned, monitored and updated by the system.
 */
public class Incident {

    private int incidentId;
    private String type;
    private String location;
    private String severity;
    private String status;
    private String assignedWorker;

    /**
     * Default constructor.
     */
    public Incident() {
    }

    /**
     * Creates an incident with disaster and assignment details.
     *
     * @param incidentId the incident ID
     * @param type the incident type
     * @param location the incident location
     * @param severity the incident severity
     * @param status the incident status
     * @param assignedWorker the assigned emergency worker
     */
    public Incident(int incidentId, String type, String location,
            String severity, String status, String assignedWorker) {
        this.incidentId = incidentId;
        this.type = type;
        this.location = location;
        this.severity = severity;
        this.status = status;
        this.assignedWorker = assignedWorker;
    }

    public int getIncidentId() {
        return this.incidentId;
    }

    public void setIncidentId(int incidentId) {
        this.incidentId = incidentId;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSeverity() {
        return this.severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignedWorker() {
        return this.assignedWorker;
    }

    public void setAssignedWorker(String assignedWorker) {
        this.assignedWorker = assignedWorker;
    }

    @Override
    public String toString() {
        return this.incidentId + " | "
                + this.type + " | "
                + this.location + " | "
                + this.severity + " | "
                + this.status;
    }
}