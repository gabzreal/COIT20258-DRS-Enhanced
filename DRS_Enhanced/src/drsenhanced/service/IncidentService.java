package drsenhanced.service;

import drsenhanced.dao.IncidentDAO;
import drsenhanced.model.Incident;
import java.sql.SQLException;
import java.util.List;

/**
 * Provides incident retrieval, assignment, and status operations.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class IncidentService {

    private final IncidentDAO incidentDAO = new IncidentDAO();

    public List<Incident> getActiveIncidents() throws SQLException {
        return incidentDAO.findActive();
    }

    public List<Incident> getAssignedIncidents(String workerUsername)
            throws SQLException {
        return incidentDAO.findByAssignedWorker(workerUsername);
    }

    public boolean assignWorker(int incidentId, String workerUsername)
            throws SQLException {
        return incidentDAO.assignWorker(incidentId, workerUsername);
    }

    public boolean updateStatus(int incidentId, String status)
            throws SQLException {
        return incidentDAO.updateStatus(incidentId, status);
    }
}
