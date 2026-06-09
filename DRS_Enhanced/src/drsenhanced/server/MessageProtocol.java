package drsenhanced.server;

/**
 * MessageProtocol defines all commands and response formats used between the
 * DRS client and server.
 */
public class MessageProtocol {

    public static final String SEPARATOR = "\\|";
    public static final String DELIMITER = "|";

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

    public static String[] parseMessage(String message) {
        if (message == null || message.trim().isEmpty()) {
            return new String[]{""};
        }
        return message.split(SEPARATOR);
    }

    public static String buildSuccess(String message, String data) {
        return SUCCESS + DELIMITER + message + DELIMITER + data;
    }

    public static String buildError(String errorMessage) {
        return ERROR + DELIMITER + errorMessage;
    }
}
