package drsenhanced.server;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for MessageProtocol and DRSServer.
 * Covers valid inputs, invalid inputs, and edge cases.
 *
 * @author Angelica - P5 Server and Testing
 */
public class ServerTest {

    // --- MessageProtocol tests ---

    @Test
    public void testBuildSuccess() {
        String result = MessageProtocol.buildSuccess("Login successful", "admin");
        assertTrue(result.contains("SUCCESS"));
    }

    @Test
    public void testBuildError() {
        String result = MessageProtocol.buildError("Invalid password");
        assertTrue(result.contains("ERROR"));
    }

    @Test
    public void testParseMessageValidInput() {
        String[] parts = MessageProtocol.parseMessage("LOGIN|citizen|pass123");
        assertEquals("LOGIN", parts[0]);
        assertEquals("citizen", parts[1]);
        assertEquals("pass123", parts[2]);
    }

    @Test
    public void testParseMessageSinglePart() {
        String[] parts = MessageProtocol.parseMessage("GET_ALL_REPORTS");
        assertEquals(1, parts.length);
        assertEquals("GET_ALL_REPORTS", parts[0]);
    }

    @Test
    public void testParseMessageEmptyString() {
        String[] parts = MessageProtocol.parseMessage("");
        assertNotNull(parts);
    }

    @Test
    public void testLoginCommandConstant() {
        assertEquals("LOGIN", MessageProtocol.LOGIN);
    }

    @Test
    public void testReportCommandConstant() {
        assertEquals("REPORT", MessageProtocol.REPORT);
    }

    @Test
    public void testCheckShelterCommandConstant() {
        assertEquals("CHECK_SHELTER", MessageProtocol.CHECK_SHELTER);
    }

    @Test
    public void testAssignShelterCommandConstant() {
        assertEquals("ASSIGN_SHELTER", MessageProtocol.ASSIGN_SHELTER);
    }

    @Test
    public void testServerClassExists() {
        DRSServer server = new DRSServer();
        assertNotNull(server);
    }
}