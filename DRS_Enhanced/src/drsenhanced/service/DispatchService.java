package drsenhanced.service;

import drsenhanced.dao.DispatchRequestDAO;
import drsenhanced.dao.DispatchWorkflowDAO;
import drsenhanced.model.DispatchRequest;
import java.sql.SQLException;

/**
 * Creates and updates resource dispatch requests.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class DispatchService {

    private final DispatchRequestDAO requestDAO = new DispatchRequestDAO();
    private final DispatchWorkflowDAO workflowDAO = new DispatchWorkflowDAO();

    public int requestSupport(int incidentId, String resourceType)
            throws SQLException {
        return requestDAO.create(new DispatchRequest(
                0, incidentId, resourceType, "REQUESTED"));
    }

    public boolean markDispatched(int dispatchId) throws SQLException {
        return requestDAO.updateStatus(dispatchId, "DISPATCHED");
    }

    public DispatchWorkflowDAO.DispatchResult dispatchIncident(
            int incidentId, int managerId, String resourceType)
            throws SQLException {
        return workflowDAO.dispatchIncident(
                incidentId, managerId, resourceType);
    }
}
