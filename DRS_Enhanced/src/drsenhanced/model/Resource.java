package drsenhanced.model;

/**
 * Represents a dispatched resource for an incident.
 * Resources may include fire, police, medical or shelter support.
 */
public class Resource {

    private int dispatchId;
    private int incidentId;
    private String resourceType;
    private String status;

    /**
     * Default constructor.
     */
    public Resource() {
    }

    /**
     * Creates a resource dispatch record.
     *
     * @param dispatchId the dispatch ID
     * @param incidentId the related incident ID
     * @param resourceType the type of resource
     * @param status the resource dispatch status
     */
    public Resource(int dispatchId, int incidentId,
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
    return "Resource{"
            + "dispatchId=" + dispatchId
            + ", incidentId=" + incidentId
            + ", resourceType='" + resourceType + '\''
            + ", status='" + status + '\''
            + '}';
}
}
