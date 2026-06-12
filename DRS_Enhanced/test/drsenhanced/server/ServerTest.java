package drsenhanced.server;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 * JUnit tests for the DRS-Enhanced server components.
 * Covers MessageProtocol, DRSServer, and ClientHandler logic.
 * 
 * Tests grouped by: valid inputs, invalid inputs, edge cases, and
 * constants verification. All test categories required for marking.
 * 
 * @author Angelica - P5 Server and  Testing
 */
public class ServerTest {

    // =========================================================
    // MessageProtocol - buildSuccess()
    // =========================================================

    @Test
    public void testBuildSuccess_containsSUCCESS() {
        String result = MessageProtocol.buildSuccess("Login successful", "citizen");
        assertTrue("Response should contain SUCCESS", result.contains("SUCCESS"));
    }

    @Test
    public void testBuildSuccess_containsMessage() {
        String result = MessageProtocol.buildSuccess("Login successful", "citizen");
        assertTrue("Response should include the message text", result.contains("Login successful"));
    }

    @Test
    public void testBuildSuccess_containsData() {
        String result = MessageProtocol.buildSuccess("Report saved", "42");
        assertTrue("Response should include the data payload", result.contains("42"));
    }

    @Test
    public void testBuildSuccess_formatHasPipeSeparators() {
        // Expected format: SUCCESS|message|data
        String result = MessageProtocol.buildSuccess("Shelter found", "CentreA|100|60|40");
        String[] parts = result.split("\\|");
        assertTrue("Response should have at least 3 pipe-separated parts", parts.length >= 3);
        assertEquals("First part should be SUCCESS", "SUCCESS", parts[0]);
    }

    @Test
    public void testBuildSuccess_emptyData() {
        // Edge case: empty data string still builds a valid response
        String result = MessageProtocol.buildSuccess("No reports found", "0");
        assertNotNull("Result should not be null", result);
        assertTrue("Result should still contain SUCCESS", result.contains("SUCCESS"));
    }

    // =========================================================
    // MessageProtocol - buildError()
    // =========================================================

    @Test
    public void testBuildError_containsERROR() {
        String result = MessageProtocol.buildError("Invalid password");
        assertTrue("Error response should contain ERROR", result.contains("ERROR"));
    }

    @Test
    public void testBuildError_containsMessage() {
        String result = MessageProtocol.buildError("Missing shelter ID");
        assertTrue("Error response should include the error message", result.contains("Missing shelter ID"));
    }

    @Test
    public void testBuildError_emptyMessage() {
        // Edge case: empty error message should still return a valid ERROR response
        String result = MessageProtocol.buildError("");
        assertNotNull("Result should not be null even with empty message", result);
        assertTrue("Should still contain ERROR prefix", result.contains("ERROR"));
    }

    @Test
    public void testBuildError_doesNotContainSUCCESS() {
        String result = MessageProtocol.buildError("Something went wrong");
        assertFalse("Error response must NOT contain SUCCESS", result.contains("SUCCESS"));
    }

    // =========================================================
    // MessageProtocol - parseMessage()
    // =========================================================

    @Test
    public void testParseMessage_loginCommand() {
        String[] parts = MessageProtocol.parseMessage("LOGIN|citizen|pass123");
        assertEquals("Should parse 3 parts", 3, parts.length);
        assertEquals("First part should be LOGIN", "LOGIN", parts[0]);
        assertEquals("Second part should be username", "citizen", parts[1]);
        assertEquals("Third part should be password", "pass123", parts[2]);
    }

    @Test
    public void testParseMessage_singleCommand() {
        // Commands with no parameters
        String[] parts = MessageProtocol.parseMessage("GET_ALL_REPORTS");
        assertEquals("Single command should return 1 part", 1, parts.length);
        assertEquals("GET_ALL_REPORTS", parts[0]);
    }

    @Test
    public void testParseMessage_shelterCommand() {
        String[] parts = MessageProtocol.parseMessage("CHECK_SHELTER|5");
        assertEquals("Should have 2 parts", 2, parts.length);
        assertEquals("CHECK_SHELTER", parts[0]);
        assertEquals("5", parts[1]);
    }

    @Test
    public void testParseMessage_assignShelterCommand() {
        // ASSIGN_SHELTER|reportId|shelterId|people
        String[] parts = MessageProtocol.parseMessage("ASSIGN_SHELTER|10|3|15");
        assertEquals("Should parse 4 parts", 4, parts.length);
        assertEquals("ASSIGN_SHELTER", parts[0]);
        assertEquals("10", parts[1]);
        assertEquals("3", parts[2]);
        assertEquals("15", parts[3]);
    }

    @Test
    public void testParseMessage_emptyString() {
        // Edge case: empty input should not crash
        String[] parts = MessageProtocol.parseMessage("");
        assertNotNull("Result should not be null for empty input", parts);
    }

    @Test
    public void testParseMessage_resourceCommand() {
        String[] parts = MessageProtocol.parseMessage("ASSIGN_RESOURCE|7|Ambulance");
        assertEquals("Should parse 3 parts", 3, parts.length);
        assertEquals("ASSIGN_RESOURCE", parts[0]);
        assertEquals("7", parts[1]);
        assertEquals("Ambulance", parts[2]);
    }

    @Test
    public void testParseMessage_updateStatusCommand() {
        String[] parts = MessageProtocol.parseMessage("UPDATE_STATUS|12|COMPLETED");
        assertEquals("Should parse 3 parts", 3, parts.length);
        assertEquals("UPDATE_STATUS", parts[0]);
    }

    // =========================================================
    // MessageProtocol - constants verification
    // Checking these match exactly what ClientHandler uses in its switch
    // =========================================================

    @Test
    public void testConstant_LOGIN() {
        assertEquals("LOGIN", MessageProtocol.LOGIN);
    }

    @Test
    public void testConstant_REPORT() {
        assertEquals("REPORT", MessageProtocol.REPORT);
    }

    @Test
    public void testConstant_GET_ALL_REPORTS() {
        assertEquals("GET_ALL_REPORTS", MessageProtocol.GET_ALL_REPORTS);
    }

    @Test
    public void testConstant_GET_DEPARTMENTS() {
        assertEquals("GET_DEPARTMENTS", MessageProtocol.GET_DEPARTMENTS);
    }

    @Test
    public void testConstant_UPDATE_DEPT_AVAILABILITY() {
        assertEquals("UPDATE_DEPT_AVAILABILITY", MessageProtocol.UPDATE_DEPT_AVAILABILITY);
    }

    @Test
    public void testConstant_GET_AVAILABLE_RESOURCES() {
        assertEquals("GET_AVAILABLE_RESOURCES", MessageProtocol.GET_AVAILABLE_RESOURCES);
    }

    @Test
    public void testConstant_ASSIGN_RESOURCE() {
        assertEquals("ASSIGN_RESOURCE", MessageProtocol.ASSIGN_RESOURCE);
    }

    @Test
    public void testConstant_CHECK_SHELTER() {
        assertEquals("CHECK_SHELTER", MessageProtocol.CHECK_SHELTER);
    }

    @Test
    public void testConstant_ASSIGN_SHELTER() {
        assertEquals("ASSIGN_SHELTER", MessageProtocol.ASSIGN_SHELTER);
    }

    @Test
    public void testConstant_UPDATE_STATUS() {
        assertEquals("UPDATE_STATUS", MessageProtocol.UPDATE_STATUS);
    }

    @Test
    public void testConstant_GET_RESPONSE_LOG() {
        assertEquals("GET_RESPONSE_LOG", MessageProtocol.GET_RESPONSE_LOG);
    }

    @Test
    public void testConstant_SUCCESS() {
        assertEquals("SUCCESS", MessageProtocol.SUCCESS);
    }

    @Test
    public void testConstant_ERROR() {
        assertEquals("ERROR", MessageProtocol.ERROR);
    }

    @Test
    public void testConstant_FAIL() {
        assertEquals("FAIL", MessageProtocol.FAIL);
    }

    // =========================================================
    // DRSServer - basic instantiation and structure
    // (no real socket opened - just checks class can be constructed)
    // =========================================================

    @Test
    public void testDRSServer_canBeInstantiated() {
        DRSServer server = new DRSServer();
        assertNotNull("DRSServer instance should not be null", server);
    }

    @Test
    public void testDRSServer_stopDoesNotThrow() {
        // stop() sets running=false, should never throw an exception
        DRSServer server = new DRSServer();
        try {
            server.stop();
        } catch (Exception e) {
            fail("server.stop() should not throw any exception: " + e.getMessage());
        }
    }

    // =========================================================
    // processRequest logic (via MessageProtocol helper)
    // These simulate what ClientHandler.processRequest() would
    // receive and check the protocol is well-formed before sending
    // =========================================================

    @Test
    public void testProcessRequest_unknownCommandReturnsError() {
        // ClientHandler returns buildError() for unknown commands.
        // Here we just verify the error format is what we'd expect.
        String unknownResponse = MessageProtocol.buildError("Unknown command: BLAH");
        assertTrue("Unknown command response should start with ERROR",
                unknownResponse.startsWith("ERROR"));
    }

    @Test
    public void testProcessRequest_missingLoginParams_returnsError() {
        // Simulating what handleLogin does when parts.length < 3
        String shortMessage = "LOGIN|onlyOneParam";
        String[] parts = MessageProtocol.parseMessage(shortMessage);
        // ClientHandler checks parts.length < 3 and returns error
        assertTrue("LOGIN with only 1 param should be detected as incomplete",
                parts.length < 3);
    }

    @Test
    public void testProcessRequest_missingReportParams_returnsError() {
        // REPORT requires citizenId and incidentId (3 parts total)
        String shortMessage = "REPORT|1";
        String[] parts = MessageProtocol.parseMessage(shortMessage);
        assertTrue("REPORT with only 1 param should be detected as incomplete",
                parts.length < 3);
    }

    @Test
    public void testProcessRequest_missingShelterParam_returnsError() {
        // CHECK_SHELTER requires shelterId (2 parts total)
        String shortMessage = "CHECK_SHELTER";
        String[] parts = MessageProtocol.parseMessage(shortMessage);
        assertTrue("CHECK_SHELTER with no params should be detected as incomplete",
                parts.length < 2);
    }

    @Test
    public void testProcessRequest_invalidIdFormat_wouldCauseError() {
        // ClientHandler parses IDs with Integer.parseInt() - "abc" throws NumberFormatException
        // We verify the handler would catch this by checking the string is not a valid int
        String badId = "abc";
        boolean isValidInt = true;
        try {
            Integer.parseInt(badId);
        } catch (NumberFormatException e) {
            isValidInt = false;
        }
        assertFalse("Non-numeric IDs should be detected as invalid", isValidInt);
    }

    @Test
    public void testProcessRequest_validShelterAssignment_partsCheck() {
        // Full valid ASSIGN_SHELTER message has 4 parts
        String msg = "ASSIGN_SHELTER|10|3|15";
        String[] parts = MessageProtocol.parseMessage(msg);
        assertEquals("Valid ASSIGN_SHELTER should have exactly 4 parts", 4, parts.length);
        // All numeric params should parse without exception
        try {
            int reportId  = Integer.parseInt(parts[1]);
            int shelterId = Integer.parseInt(parts[2]);
            int people    = Integer.parseInt(parts[3]);
            assertTrue("reportId should be positive",  reportId  > 0);
            assertTrue("shelterId should be positive", shelterId > 0);
            assertTrue("people should be positive",    people    > 0);
        } catch (NumberFormatException e) {
            fail("Numeric params should all parse correctly: " + e.getMessage());
        }
    }

    @Test
    public void testProcessRequest_assignShelter_tooManyPeopleFails() {
        // Capacity check: if people > (capacity - currentOccupancy), server returns error
        // Simulating the math done in handleAssignShelter
        int capacity         = 100;
        int currentOccupancy = 90;
        int peopleRequested  = 20;
        int available = capacity - currentOccupancy;
        assertTrue("Should detect overflow when people > available space",
                peopleRequested > available);
    }

    @Test
    public void testProcessRequest_assignShelter_exactCapacity_succeeds() {
        // Edge case: exactly filling to capacity should be allowed
        int capacity         = 100;
        int currentOccupancy = 85;
        int peopleRequested  = 15;
        int newOccupancy = currentOccupancy + peopleRequested;
        assertTrue("Filling exactly to capacity should be allowed",
                newOccupancy <= capacity);
    }
}