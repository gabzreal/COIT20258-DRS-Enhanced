package drsenhanced.model;

/**
 * Represents a citizen user in the Disaster Response System.
 * A citizen can submit disaster reports and view report updates.
 */
public class Citizen extends User {

    /**
     * Default constructor.
     */
    public Citizen() {
    }

    /**
     * Creates a citizen with login details.
     *
     * @param userId the citizen user ID
     * @param username the citizen username
     * @param password the citizen password
     */
    public Citizen(int userId, String username, String password) {
        super(userId, username, password, "Citizen");
    }
}