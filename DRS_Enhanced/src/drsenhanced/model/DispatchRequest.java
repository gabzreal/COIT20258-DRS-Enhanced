package drsenhanced.model;

/**
 * Represents a request to dispatch resources for an incident.
 * Dispatch requests are used to assign support to active incidents.
 */
public class DispatchRequest {

    private int dispatchId;
    private int incidentId;
    private String resourceType;
    private String status;

    /**
     * Default constructor.
     */
    public DispatchRequest() {
    }

    /**
     * Creates a dispatch request.
     *
     * @param dispatchId the dispatch request ID
     * @param incidentId the related incident ID
     * @param resourceType the requested resource type
     * @param status the dispatch request status
     */
    public DispatchRequest(int dispatchId, int incidentId,
            String resourceType, String status) {
        this.dispatchId = dispatchId;
        this.incidentId = incidentId;
        this.resourceType = resourceType;
        this.status = status;
    }

    public int getDispatchId() {
        return this.dispatchId;
    }

    public void setDispatchId(int dispatchId) {
        this.dispatchId = dispatchId;
    }

    public int getIncidentId() {
        return this.incidentId;
    }

    public void setIncidentId(int incidentId) {
        this.incidentId = incidentId;
    }

    public String getResourceType() {
        return this.resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString() {
    return "DispatchRequest{"
            + "dispatchId=" + dispatchId
            + ", incidentId=" + incidentId
            + ", resourceType='" + resourceType + '\''
            + ", status='" + status + '\''
            + '}';
}
}
