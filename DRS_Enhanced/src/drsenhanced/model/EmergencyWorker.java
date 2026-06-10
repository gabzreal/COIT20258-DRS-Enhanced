package drsenhanced.model;

/**
 * Represents an emergency worker in the Disaster Response System.
 * Emergency workers receive assigned incidents and submit status updates.
 */
public class EmergencyWorker extends User {

    private String assignedWorker;
    private String status;

    /**
     * Default constructor.
     */
    public EmergencyWorker() {
    }

    /**
     * Creates an emergency worker with assignment and status details.
     *
     * @param userId the emergency worker user ID
     * @param username the emergency worker username
     * @param password the emergency worker password
     * @param assignedWorker the assigned worker name or identifier
     * @param status the current worker status
     */
    public EmergencyWorker(int userId, String username, String password,
            String assignedWorker, String status) {
        super(userId, username, password, "EmergencyWorker");
        this.assignedWorker = assignedWorker;
        this.status = status;
    }

    public String getAssignedWorker() {
        return this.assignedWorker;
    }

    public void setAssignedWorker(String assignedWorker) {
        this.assignedWorker = assignedWorker;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
