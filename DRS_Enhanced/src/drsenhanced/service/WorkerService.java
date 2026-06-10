package drsenhanced.service;

import drsenhanced.dao.StatusUpdateDAO;
import drsenhanced.model.StatusUpdate;
import java.sql.SQLException;
import java.util.List;

/**
 * Records and retrieves emergency-worker status updates.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class WorkerService {

    private final StatusUpdateDAO updateDAO = new StatusUpdateDAO();

    public int submitStatusUpdate(
            int incidentId, int workerId, String message)
            throws SQLException {
        return updateDAO.create(new StatusUpdate(
                0, incidentId, workerId, message, null));
    }

    public List<StatusUpdate> getIncidentUpdates(int incidentId)
            throws SQLException {
        return updateDAO.findByIncidentId(incidentId);
    }
}
