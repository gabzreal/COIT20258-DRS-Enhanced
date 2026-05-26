package drsenhanced.server;

/**
 * MessageProtocol defines all the request and response constants
 * used for communication between the JavaFX client and the server.
 * 
 * Format for requests:  COMMAND|param1|param2|...
 * Format for responses: SUCCESS|message|data  or  ERROR|message
 * 
 * @author Angelica - P5 Server and Testing
 */
public class MessageProtocol {

    // -------------------------
    // REQUEST CONSTANTS (Client -> Server)
    // -------------------------
    
    // User authentication
    public static final String LOGIN = "LOGIN";
    
    // Disaster report operations
    public static final String REPORT = "REPORT";
    public static final String GET_ALL_REPORTS = "GET_ALL_REPORTS";
    public static final String UPDATE_STATUS = "UPDATE_STATUS";
    
    // Department and resource operations (Feature 1)
    public static final String GET_DEPARTMENTS = "GET_DEPARTMENTS";
    public static final String UPDATE_DEPT_AVAILABILITY = "UPDATE_DEPT_AVAILABILITY";
    public static final String GET_AVAILABLE_RESOURCES = "GET_AVAILABLE_RESOURCES";
    public static final String ASSIGN_RESOURCE = "ASSIGN_RESOURCE";
    
    // Shelter and evacuation operations (Feature 2)
    public static final String CHECK_SHELTER = "CHECK_SHELTER";
    public static final String ASSIGN_SHELTER = "ASSIGN_SHELTER";
    
    // Response log
    public static final String GET_RESPONSE_LOG = "GET_RESPONSE_LOG";
    
    // -------------------------
    // RESPONSE CONSTANTS (Server -> Client)
    // -------------------------
    
    public static final String SUCCESS = "SUCCESS";
    public static final String ERROR = "ERROR";
    
    // -------------------------
    // SEPARATOR used to split message parts
    // -------------------------
    
    public static final String SEPARATOR = "|";
    public static final String SEPARATOR_REGEX = "\\|";
    
    // -------------------------
    // Helper methods
    // -------------------------
    
    /**
     * Builds a SUCCESS response string to send back to the client.
     * Example: SUCCESS|User logged in|admin,ADMIN
     */
    public static String buildSuccess(String message, String data) {
        return SUCCESS + SEPARATOR + message + SEPARATOR + data;
    }
    
    /**
     * Builds an ERROR response string to send back to the client.
     * Example: ERROR|Invalid username or password
     */
    public static String buildError(String message) {
        return ERROR + SEPARATOR + message;
    }
    
    /**
     * Splits an incoming message into its parts using the separator.
     * Example: "LOGIN|admin|pass123" -> ["LOGIN", "admin", "pass123"]
     */
    public static String[] parseMessage(String message) {
        return message.split(SEPARATOR_REGEX);
    }
}
