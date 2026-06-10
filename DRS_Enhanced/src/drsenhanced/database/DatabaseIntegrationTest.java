package drsenhanced.database;

import drsenhanced.dao.AssessmentDAO;
import drsenhanced.dao.CoordinationRecordDAO;
import drsenhanced.dao.DepartmentDAO;
import drsenhanced.dao.DisasterReportDAO;
import drsenhanced.dao.DisasterWorkflowDAO;
import drsenhanced.dao.DispatchRequestDAO;
import drsenhanced.dao.EvacuationRecordDAO;
import drsenhanced.dao.IncidentDAO;
import drsenhanced.dao.NotificationDAO;
import drsenhanced.dao.ResourceDAO;
import drsenhanced.dao.ResponseLogDAO;
import drsenhanced.dao.ShelterDAO;
import drsenhanced.dao.StatusUpdateDAO;
import drsenhanced.dao.UserDAO;
import drsenhanced.model.DisasterReport;
import drsenhanced.model.DispatchRequest;
import drsenhanced.model.Incident;
import drsenhanced.model.Notification;
import drsenhanced.model.Resource;
import drsenhanced.model.Shelter;
import drsenhanced.model.StatusUpdate;
import drsenhanced.service.AuthenticationService;
import drsenhanced.service.CitizenService;
import drsenhanced.service.DispatchService;
import drsenhanced.service.NotificationService;
import drsenhanced.service.WorkerService;
import drsenhanced.util.PasswordUtil;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * End-to-end smoke test for the schema and core DAO workflow.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
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
        int managerId = users.createUser(
                "manager_test",
                PasswordUtil.hashPassword("manager123"),
                "Manager Test",
                "CityManager");
        require(citizenId > 0 && workerId > 0 && managerId > 0,
                "Users were not created");
        require(users.authenticate("citizen_test", "citizen123").isPresent(),
                "Authentication failed");
        AuthenticationService authentication = new AuthenticationService();
        require(authentication.login(
                "citizen_test", "citizen123", "Citizen").isPresent(),
                "Authentication service failed");
        authentication.logout();
        require(users.findById(citizenId).isPresent(),
                "User lookup by ID failed");
        require(users.findByRole("EmergencyWorker").size() == 1,
                "User role lookup failed");

        IncidentDAO incidents = new IncidentDAO();
        CitizenService citizens = new CitizenService();
        DisasterReport report = citizens.submitReport(
                citizenId, "Flood", "Docklands", "HIGH");
        Incident incident = incidents.findById(report.getIncidentId())
                .orElseThrow();
        DisasterWorkflowDAO workflow = new DisasterWorkflowDAO();
        require(report.getReportId() > 0 && incident.getIncidentId() > 0,
                "Incident report workflow failed");
        DispatchService dispatchService = new DispatchService();
        var dispatch = dispatchService.dispatchIncident(
                        incident.getIncidentId(),
                        managerId,
                        "Emergency Response Unit");
        require(dispatch.workerId() == workerId,
                "Worker was not assigned by dispatch");
        require(incidents.findByAssignedWorker("worker_test").size() == 1,
                "Assigned incident was not retrieved");

        DisasterReportDAO reports = new DisasterReportDAO();
        require(reports.findByCitizenId(citizenId).size() == 1,
                "Citizen report was not retrieved");
        require(reports.findByIncidentId(incident.getIncidentId()).isPresent(),
                "Incident report was not retrieved");

        AssessmentDAO assessments = new AssessmentDAO();
        require(assessments.create(new AssessmentDAO.Assessment(
                0, report.getReportId(), "HIGH", "CRITICAL", 18,
                "Flooding threatens residential properties")) > 0,
                "Assessment was not created");
        require(assessments.findByReportId(report.getReportId()).isPresent(),
                "Assessment was not retrieved");

        int incidentCount = incidents.findAll().size();
        try {
            workflow.createIncidentReport(-1, new Incident(
                    0, "Fire", "CBD", "HIGH", "PENDING", null));
            throw new IllegalStateException(
                    "Invalid workflow unexpectedly succeeded");
        } catch (java.sql.SQLException expected) {
            require(incidents.findAll().size() == incidentCount,
                    "Failed workflow was not rolled back");
        }

        DispatchRequest request = new DispatchRequest(
                0, incident.getIncidentId(), "Ambulance", "REQUESTED");
        DispatchRequestDAO requests = new DispatchRequestDAO();
        require(requests.create(request) > 0,
                "Dispatch request was not created");
        require(requests.findPending().size() == 1,
                "Pending dispatch was not retrieved");
        require(requests.updateStatus(request.getDispatchId(), "DISPATCHED"),
                "Dispatch status was not updated");

        Resource resource = new Resource(
                0, incident.getIncidentId(), "Fire Unit", "DISPATCHED");
        ResourceDAO resources = new ResourceDAO();
        require(resources.createDispatch(resource) > 0,
                "Resource dispatch was not created");

        DepartmentDAO departments = new DepartmentDAO();
        int departmentId = departments.create(new DepartmentDAO.Department(
                0, "Melbourne Fire", "FIRE", "000", "Available", 4));
        require(departmentId > 0, "Department was not created");
        require(departments.findAvailable().size() == 1,
                "Available department was not retrieved");
        require(departments.updateAvailability(
                departmentId, "Limited", 1),
                "Department availability was not updated");

        CoordinationRecordDAO coordination = new CoordinationRecordDAO();
        require(coordination.create(
                new CoordinationRecordDAO.CoordinationRecord(
                        0, report.getReportId(), departmentId, null,
                        "Deploy flood response team", "RESPONDING",
                        "Team notified")) > 0,
                "Coordination record was not created");
        require(coordination.findByReportId(report.getReportId()).size() == 1,
                "Coordination record was not retrieved");

        StatusUpdate update = new StatusUpdate(
                0, incident.getIncidentId(), workerId,
                "Team arrived on site", null);
        StatusUpdateDAO updates = new StatusUpdateDAO();
        WorkerService workers = new WorkerService();
        require(workers.submitStatusUpdate(
                update.getIncidentId(),
                update.getWorkerId(),
                update.getMessage()) > 0,
                "Status update was not created");
        require(updates.findByWorkerId(workerId).size() == 1,
                "Worker status history was not retrieved");

        Shelter shelter = new Shelter(0, "Town Hall", 200, 120);
        ShelterDAO shelters = new ShelterDAO();
        require(shelters.create(shelter, "Melbourne CBD") > 0,
                "Shelter was not created");
        require(shelters.updateOccupancy(shelter.getShelterId(), 125),
                "Shelter occupancy was not updated");
        require(shelters.findById(shelter.getShelterId()).isPresent(),
                "Shelter lookup failed");
        require(shelters.findAvailable().size() == 1,
                "Available shelter was not retrieved");

        EvacuationRecordDAO evacuations = new EvacuationRecordDAO();
        require(evacuations.create(
                new EvacuationRecordDAO.EvacuationRecord(
                        0, report.getReportId(), shelter.getShelterId(),
                        50, true, 25)) > 0,
                "Evacuation record was not created");
        require(evacuations.findByReportId(report.getReportId()).size() == 1,
                "Evacuation record was not retrieved");

        ResponseLogDAO responseLogs = new ResponseLogDAO();
        require(responseLogs.create(new ResponseLogDAO.ResponseLog(
                0, incident.getIncidentId(), "Ambulance assigned",
                workerId, "Unit A12 dispatched", null)) > 0,
                "Response log was not created");
        require(responseLogs.findByIncidentId(
                incident.getIncidentId()).size() == 2,
                "Incident response log was not retrieved");
        require(responseLogs.findRecent(5).size() == 2,
                "Recent response log was not retrieved");

        Notification notification = new Notification(
                0, citizenId, "Incident response started", null, false);
        NotificationDAO notifications = new NotificationDAO();
        NotificationService notificationService = new NotificationService();
        int notificationId = notificationService.sendNotification(
                notification.getUserId(), notification.getMessage());
        require(notificationId > 0,
                "Notification was not created");
        require(notifications.findUnreadByUserId(citizenId).size() == 1,
                "Notification was not retrieved");
        require(notifications.markAsRead(notificationId),
                "Notification was not marked as read");
        require(notifications.findUnreadByUserId(citizenId).isEmpty(),
                "Read notification remained unread");
        require(notifications.findByUserId(citizenId).size() == 1,
                "Notification history was not retrieved");
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
