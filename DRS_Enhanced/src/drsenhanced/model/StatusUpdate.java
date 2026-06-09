
package drsenhanced.model;

/**
 * Represents a status update submitted by an emergency worker.
 * Status updates are linked to incidents and shown on dashboards.
 */
public class StatusUpdate {

    private int updateId;
    private int incidentId;
    private int workerId;
    private String message;
    private String timestamp;

    /**
     * Default constructor.
     */
    public StatusUpdate() {
    }

    /**
     * Creates an incident status update.
     *
     * @param updateId the status update ID
     * @param incidentId the related incident ID
     * @param workerId the emergency worker user ID
     * @param message the update message
     * @param timestamp the update timestamp
     */
    public StatusUpdate(int updateId, int incidentId, int workerId,
            String message, String timestamp) {
        this.updateId = updateId;
        this.incidentId = incidentId;
        this.workerId = workerId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getUpdateId() {
        return this.updateId;
    }

    public void setUpdateId(int updateId) {
        this.updateId = updateId;
    }

    public int getIncidentId() {
        return this.incidentId;
    }

    public void setIncidentId(int incidentId) {
        this.incidentId = incidentId;
    }

    public int getWorkerId() {
        return this.workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
