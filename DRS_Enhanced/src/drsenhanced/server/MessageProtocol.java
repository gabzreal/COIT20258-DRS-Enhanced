package drsenhanced.server;

/**
 * Defines message keywords shared by the client and server.
 */
/**
 * MessageProtocol manages communication messages
 * exchanged between system components.
 *
 * Modified by Krishna Kakani to support additional
 * message types required for the Disaster Response
 * System enhancement, including Citizen, Emergency
 * Worker, and City Manager operations.
 *
 * 
 */
public final class MessageProtocol {

    public static final String LOGIN = "LOGIN";
    public static final String REPORT = "REPORT";
    public static final String GET_ALL_REPORTS = "GET_ALL_REPORTS";
    public static final String GET_DEPARTMENTS = "GET_DEPARTMENTS";
    public static final String UPDATE_DEPT_AVAILABILITY = "UPDATE_DEPT_AVAILABILITY";
    public static final String GET_AVAILABLE_RESOURCES = "GET_AVAILABLE_RESOURCES";
    public static final String ASSIGN_RESOURCE = "ASSIGN_RESOURCE";
    public static final String CHECK_SHELTER = "CHECK_SHELTER";
    public static final String ASSIGN_SHELTER = "ASSIGN_SHELTER";
    public static final String UPDATE_STATUS = "UPDATE_STATUS";
    public static final String GET_RESPONSE_LOG = "GET_RESPONSE_LOG";

    public static final String SUCCESS = "SUCCESS";
    public static final String ERROR = "ERROR";
    public static final String FAIL = "FAIL";

    private MessageProtocol() {
    }

    public static String[] parseMessage(String message) {
        return message.split("\\|");
    }

    public static String buildSuccess(String message, String data) {
        return SUCCESS + "|" + message + "|" + data;
    }

    public static String buildError(String message) {
        return ERROR + "|" + message;
    }
}