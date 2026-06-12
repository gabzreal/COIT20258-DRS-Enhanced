package drsenhanced.model;

/**
 * Represents a basic user in the Disaster Response System. This is the parent
 * class for Citizen, EmergencyWorker and CityManager.
 */
public class User {

    private int userId;
    private String username;
    private String password;
    private String role;
    private String phone;
    private String suburb;

    /**
     * Default constructor.
     */
    public User() {
    }

    /**
     * Creates a user with login and role details.
     *
     * @param userId the user ID
     * @param username the username
     * @param password the password
     * @param role the user role
     */
    public User(int userId,
            String username,
            String password,
            String role) {

        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    @Override
    public String toString() {
        return "User{"
                + "userId=" + this.userId
                + ", username='" + this.username + '\''
                + ", role='" + this.role + '\''
                + '}';
    }
}
