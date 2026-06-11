package drsenhanced.model;

/**
 * Represents a disaster report submitted by a citizen.
 * A disaster report can be converted into an incident.
 */
public class DisasterReport {

    private int reportId;
    private int citizenId;
    private int incidentId;

    /**
     * Default constructor.
     */
    public DisasterReport() {
    }

    /**
     * Creates a disaster report linked to a citizen and incident.
     *
     * @param reportId the report ID
     * @param citizenId the citizen user ID
     * @param incidentId the related incident ID
     */
    public DisasterReport(int reportId, int citizenId, int incidentId) {
        this.reportId = reportId;
        this.citizenId = citizenId;
        this.incidentId = incidentId;
    }

    public int getReportId() {
        return this.reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public int getCitizenId() {
        return this.citizenId;
    }

    public void setCitizenId(int citizenId) {
        this.citizenId = citizenId;
    }

    public int getIncidentId() {
        return this.incidentId;
    }

    public void setIncidentId(int incidentId) {
        this.incidentId = incidentId;
    }
    
    @Override
    public String toString() {
    return "DisasterReport{"
            + "reportId=" + reportId
            + ", citizenId=" + citizenId
            + ", incidentId=" + incidentId
            + '}';
}
}