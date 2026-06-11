package drsenhanced.service;

import drsenhanced.dao.DisasterReportDAO;
import drsenhanced.dao.DisasterWorkflowDAO;
import drsenhanced.dao.IncidentDAO;
import drsenhanced.dao.UserDAO;
import drsenhanced.model.DisasterReport;
import drsenhanced.model.Incident;
import drsenhanced.model.User;
import drsenhanced.util.PasswordUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Provides citizen account and disaster reporting operations.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class CitizenService {

    private final UserDAO userDAO = new UserDAO();
    private final DisasterWorkflowDAO workflowDAO = new DisasterWorkflowDAO();
    private final DisasterReportDAO reportDAO = new DisasterReportDAO();
    private final IncidentDAO incidentDAO = new IncidentDAO();

    public Optional<User> createAccount(
            String username, String password, String fullName) {
        if (username == null || username.isBlank()
                || password == null || password.isBlank()
                || fullName == null || fullName.isBlank()) {
            return Optional.empty();
        }
        int userId = userDAO.createUser(
                username.trim(),
                PasswordUtil.hashPassword(password),
                fullName.trim(),
               
                "Citizen");
        return userId > 0 ? userDAO.findById(userId) : Optional.empty();
    }

    public DisasterReport submitReport(
            int citizenId, String type, String location, String severity)
            throws SQLException {
        Incident incident = new Incident(
                0, type, location, severity, "PENDING", null);
        return workflowDAO.createIncidentReport(citizenId, incident);
    }

    public List<Incident> getCitizenIncidents(int citizenId)
            throws SQLException {
        List<Incident> incidents = new ArrayList<>();
        for (DisasterReport report : reportDAO.findByCitizenId(citizenId)) {
            incidentDAO.findById(report.getIncidentId())
                    .ifPresent(incidents::add);
        }
        return incidents;
    }
}
