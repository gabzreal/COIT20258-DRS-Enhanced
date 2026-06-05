package drsenhanced.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * ClientHandler manages the communication with a single connected client.
 * Each client that connects to the server gets its own ClientHandler
 * running in a separate thread.
 * 
 * It reads the request from the client, figures out what command was sent,
 * and returns the appropriate response.
 * 
 * @author Angelica - P5 Server and Testing
 */
public class ClientHandler implements Runnable {

    // The socket connection to this specific client
    private Socket clientSocket;

    /**
     * Constructor - receives the client socket when a new client connects.
     */
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * This method runs in its own thread.
     * It reads messages from the client and sends back responses.
     */
    @Override
    public void run() {

        System.out.println("Handling client: "
                + clientSocket.getInetAddress().getHostAddress());

        // Set up input and output streams to communicate with the client
        try (
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(
                    clientSocket.getOutputStream(), true)
        ) {

            String message;

            // Keep reading messages from this client until they disconnect
            while ((message = input.readLine()) != null) {

                System.out.println("Received: " + message);

                // Process the message and get the response
                String response = processRequest(message);

                // Send the response back to the client
                output.println(response);

                System.out.println("Sent: " + response);
            }

        } catch (IOException e) {
            System.out.println("Client disconnected: " + e.getMessage());
        } finally {
            // Close the socket when the client disconnects
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Error closing socket: " + e.getMessage());
            }
        }
    }

    /**
     * Reads the incoming message, identifies the command,
     * and returns the correct response.
     * 
     * Format expected: COMMAND|param1|param2|...
     */
    private String processRequest(String message) {

        // Split the message into parts using the separator
        String[] parts = MessageProtocol.parseMessage(message);

        // The first part is always the command
        String command = parts[0];

        // Route the command to the correct handler
        switch (command) {

            case MessageProtocol.LOGIN:
                return handleLogin(parts);

            case MessageProtocol.REPORT:
                return handleReport(parts);

            case MessageProtocol.GET_ALL_REPORTS:
                return handleGetAllReports();

            case MessageProtocol.GET_DEPARTMENTS:
                return handleGetDepartments();

            case MessageProtocol.UPDATE_DEPT_AVAILABILITY:
                return handleUpdateDeptAvailability(parts);

            case MessageProtocol.GET_AVAILABLE_RESOURCES:
                return handleGetAvailableResources(parts);

            case MessageProtocol.ASSIGN_RESOURCE:
                return handleAssignResource(parts);

            case MessageProtocol.CHECK_SHELTER:
                return handleCheckShelter(parts);

            case MessageProtocol.ASSIGN_SHELTER:
                return handleAssignShelter(parts);

            case MessageProtocol.UPDATE_STATUS:
                return handleUpdateStatus(parts);

            case MessageProtocol.GET_RESPONSE_LOG:
                return handleGetResponseLog(parts);

            default:
                return MessageProtocol.buildError("Unknown command: " + command);
        }
    }

    // -------------------------------------------------------
    // Individual command handlers
    // These will be connected to the service layer in Week 11
    // For now they return placeholder responses so the server compiles
    // -------------------------------------------------------

    private String handleLogin(String[] parts) {
        // Expected: LOGIN|username|password
        // TODO Week 11 - connect to UserDAO to verify credentials
        if (parts.length < 3) {
            return MessageProtocol.buildError("Missing username or password");
        }
        return MessageProtocol.buildSuccess("Login received", parts[1]);
    }

    private String handleReport(String[] parts) {
        // Expected: REPORT|type|location|description|people|userId
        // TODO Week 11 - connect to DisasterReportDAO to save report
        if (parts.length < 6) {
            return MessageProtocol.buildError("Missing report fields");
        }
        return MessageProtocol.buildSuccess("Report received", parts[1]);
    }

    private String handleGetAllReports() {
        // TODO Week 11 - connect to DisasterReportDAO to fetch all reports
        return MessageProtocol.buildSuccess("Reports fetched", "placeholder");
    }

    private String handleGetDepartments() {
        // TODO Week 11 - connect to DepartmentDAO to fetch departments
        return MessageProtocol.buildSuccess("Departments fetched", "placeholder");
    }

    private String handleUpdateDeptAvailability(String[] parts) {
        // Expected: UPDATE_DEPT_AVAILABILITY|deptId|status|units
        // TODO Week 11 - connect to DepartmentDAO to update availability
        if (parts.length < 4) {
            return MessageProtocol.buildError("Missing department availability fields");
        }
        return MessageProtocol.buildSuccess("Department availability updated", parts[1]);
    }

    private String handleGetAvailableResources(String[] parts) {
        // Expected: GET_AVAILABLE_RESOURCES|deptType
        // TODO Week 11 - connect to ResourceDAO
        if (parts.length < 2) {
            return MessageProtocol.buildError("Missing department type");
        }
        return MessageProtocol.buildSuccess("Resources fetched", "placeholder");
    }

    private String handleAssignResource(String[] parts) {
        // Expected: ASSIGN_RESOURCE|reportId|resourceId
        // TODO Week 11 - connect to CoordinationRecordDAO
        if (parts.length < 3) {
            return MessageProtocol.buildError("Missing resource assignment fields");
        }
        return MessageProtocol.buildSuccess("Resource assigned", parts[1]);
    }

    private String handleCheckShelter(String[] parts) {
        // Expected: CHECK_SHELTER|shelterId|people
        // TODO Week 11 - connect to ShelterDAO to check capacity
        if (parts.length < 3) {
            return MessageProtocol.buildError("Missing shelter check fields");
        }
        return MessageProtocol.buildSuccess("Shelter capacity checked", "placeholder");
    }

    private String handleAssignShelter(String[] parts) {
        // Expected: ASSIGN_SHELTER|reportId|shelterId|people
        // TODO Week 11 - connect to EvacuationRecordDAO
        if (parts.length < 4) {
            return MessageProtocol.buildError("Missing shelter assignment fields");
        }
        return MessageProtocol.buildSuccess("Shelter assigned", parts[1]);
    }

    private String handleUpdateStatus(String[] parts) {
        // Expected: UPDATE_STATUS|reportId|newStatus|note
        // TODO Week 11 - connect to DisasterReportDAO
        if (parts.length < 4) {
            return MessageProtocol.buildError("Missing status update fields");
        }
        return MessageProtocol.buildSuccess("Status updated", parts[1]);
    }

    private String handleGetResponseLog(String[] parts) {
        // Expected: GET_RESPONSE_LOG|reportId
        // TODO Week 11 - connect to ResponseLogDAO
        if (parts.length < 2) {
            return MessageProtocol.buildError("Missing report ID");
        }
        return MessageProtocol.buildSuccess("Response log fetched", "placeholder");
    }
}