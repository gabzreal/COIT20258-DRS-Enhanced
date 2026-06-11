package drsenhanced.service;

import drsenhanced.dao.ResponseLogDAO;
import java.sql.SQLException;
import java.util.List;

/**
 * Retrieves operational activity for dashboard display.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class DashboardService {

    private final ResponseLogDAO responseLogDAO = new ResponseLogDAO();

    public List<ResponseLogDAO.ResponseLog> getRecentResponseLogs(int limit)
            throws SQLException {
        return responseLogDAO.findRecent(limit);
    }

    public int getResponsesToday() throws SQLException {
        return responseLogDAO.countTodayResponses();
    }
}
