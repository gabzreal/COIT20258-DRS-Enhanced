package drsenhanced.database;

import drsenhanced.dao.DisasterReportDAO;
import drsenhanced.dao.IncidentDAO;
import drsenhanced.dao.NotificationDAO;
import drsenhanced.dao.ResourceDAO;
import drsenhanced.dao.ShelterDAO;
import drsenhanced.dao.StatusUpdateDAO;
import drsenhanced.dao.UserDAO;
import drsenhanced.model.DisasterReport;
import drsenhanced.model.Incident;
import drsenhanced.model.Notification;
import drsenhanced.model.Resource;
import drsenhanced.model.Shelter;
import drsenhanced.model.StatusUpdate;
import drsenhanced.util.PasswordUtil;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * End-to-end smoke test for the schema and core DAO workflow.
 */
public class DatabaseIntegrationTest {

    private static final String TEST_DATABASE = "drs_enhanced_test";
    private static final String JDBC_OPTIONS
            = "?useSSL=false&allowPublicKeyRetrieval=true"
            + "&serverTimezone=Australia/Sydney";
    private static final String SERVER_URL
            = "jdbc:mysql://localhost:3306/" + JDBC_OPTIONS;

    public static void main(String[] args) throws Exception {
        String user = setting("DRS_DB_USER", "root");
        String password = setting("DRS_DB_PASSWORD", "pass");

        try {
            createTestSchema(user, password);
            System.setProperty("DRS_DB_URL",
                    "jdbc:mysql://localhost:3306/" + TEST_DATABASE
                    + JDBC_OPTIONS);
            System.setProperty("DRS_DB_USER", user);
            System.setProperty("DRS_DB_PASSWORD", password);
            runWorkflow();
            System.out.println("Database integration test passed.");
        } finally {
            dropTestDatabase(user, password);
        }
    }

    private static void runWorkflow() throws Exception {
        UserDAO users = new UserDAO();
        int citizenId = users.createUser(
                "citizen_test",
                PasswordUtil.hashPassword("citizen123"),
                "Citizen Test",
                "Citizen");
        int workerId = users.createUser(
                "worker_test",
                PasswordUtil.hashPassword("worker123"),
                "Worker Test",
                "EmergencyWorker");
        require(citizenId > 0 && workerId > 0, "Users were not created");
        require(users.authenticate("citizen_test", "citizen123").isPresent(),
                "Authentication failed");

        Incident incident = new Incident(
                0, "Flood", "Docklands", "HIGH", "PENDING", null);
        IncidentDAO incidents = new IncidentDAO();
        require(incidents.create(incident) > 0, "Incident was not created");
        require(incidents.assignWorker(incident.getIncidentId(), "worker_test"),
                "Worker was not assigned");
        require(incidents.updateStatus(incident.getIncidentId(), "RESPONDING"),
                "Incident status was not updated");

        DisasterReport report = new DisasterReport(
                0, citizenId, incident.getIncidentId());
        DisasterReportDAO reports = new DisasterReportDAO();
        require(reports.create(report) > 0, "Report was not created");
        require(reports.findByCitizenId(citizenId).size() == 1,
                "Citizen report was not retrieved");

        Resource resource = new Resource(
                0, incident.getIncidentId(), "Fire Unit", "DISPATCHED");
        ResourceDAO resources = new ResourceDAO();
        require(resources.createDispatch(resource) > 0,
                "Resource dispatch was not created");

        StatusUpdate update = new StatusUpdate(
                0, incident.getIncidentId(), workerId,
                "Team arrived on site", null);
        StatusUpdateDAO updates = new StatusUpdateDAO();
        require(updates.create(update) > 0, "Status update was not created");

        Shelter shelter = new Shelter(0, "Town Hall", 200, 120);
        ShelterDAO shelters = new ShelterDAO();
        require(shelters.create(shelter, "Melbourne CBD") > 0,
                "Shelter was not created");
        require(shelters.updateOccupancy(shelter.getShelterId(), 125),
                "Shelter occupancy was not updated");

        Notification notification = new Notification(
                0, citizenId, "Incident response started", null, false);
        NotificationDAO notifications = new NotificationDAO();
        require(notifications.create(notification) > 0,
                "Notification was not created");
        require(notifications.findUnreadByUserId(citizenId).size() == 1,
                "Notification was not retrieved");
    }

    private static void createTestSchema(String user, String password)
            throws Exception {
        String schema = Files.readString(
                Path.of("..", "sql", "drs_enhanced_schema.sql"))
                .replace("drs_enhanced", TEST_DATABASE);
        StringBuilder executableSql = new StringBuilder();
        for (String line : schema.split("\\R")) {
            if (!line.stripLeading().startsWith("--")) {
                executableSql.append(line).append('\n');
            }
        }

        try (Connection connection = DriverManager.getConnection(
                SERVER_URL, user, password);
                Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "DROP DATABASE IF EXISTS " + TEST_DATABASE);
            for (String sql : executableSql.toString().split(";")) {
                if (!sql.isBlank()) {
                    statement.execute(sql);
                }
            }
        }
    }

    private static void dropTestDatabase(String user, String password)
            throws Exception {
        try (Connection connection = DriverManager.getConnection(
                SERVER_URL, user, password);
                Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "DROP DATABASE IF EXISTS " + TEST_DATABASE);
        }
    }

    private static String setting(String name, String defaultValue) {
        String value = System.getenv(name);
        return value == null || value.isBlank() ? defaultValue : value;
    }

    private static void require(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }
}
