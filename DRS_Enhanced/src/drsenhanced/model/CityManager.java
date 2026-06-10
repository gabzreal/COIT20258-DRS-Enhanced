package drsenhanced.model;

/**
 * Represents a city manager in the Disaster Response System.
 * A city manager can assess incidents and monitor dashboard information.
 */
public class CityManager extends User {

    /**
     * Default constructor.
     */
    public CityManager() {
    }

    /**
     * Creates a city manager with login details.
     *
     * @param userId the city manager user ID
     * @param username the city manager username
     * @param password the city manager password
     */
    public CityManager(int userId, String username, String password) {
        super(userId, username, password, "CityManager");
    }
}