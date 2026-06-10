package drsenhanced.client.controller;

import drsenhanced.dao.ResponseLogDAO;
import drsenhanced.model.Incident;
import drsenhanced.service.DashboardService;
import drsenhanced.service.IncidentService;
import drsenhanced.util.SceneManager;
import drsenhanced.util.SessionContext;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

/**
 * Displays active incidents and recent operational activity.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class CityManagerDashboardController {

    private final IncidentService incidentService = new IncidentService();
    private final DashboardService dashboardService = new DashboardService();
    private final List<Incident> displayedIncidents = new ArrayList<>();

    @FXML
    private ListView<String> incidentList;

    @FXML
    private ListView<String> responseLogList;

    @FXML
    public void initialize() {
        try {
            displayedIncidents.addAll(incidentService.getActiveIncidents());
            for (Incident incident : displayedIncidents) {
                incidentList.getItems().add(
                        incident.getType() + " - " + incident.getLocation()
                        + " [" + incident.getSeverity() + "]");
            }
            if (displayedIncidents.isEmpty()) {
                incidentList.getItems().add("No active incidents");
            }

            for (ResponseLogDAO.ResponseLog log
                    : dashboardService.getRecentResponseLogs(10)) {
                responseLogList.getItems().add(
                        log.timestamp() + " " + log.action());
            }
            if (responseLogList.getItems().isEmpty()) {
                responseLogList.getItems().add("No response activity");
            }
        } catch (java.sql.SQLException e) {
            incidentList.getItems().setAll("Incident data unavailable");
            responseLogList.getItems().setAll("Response logs unavailable");
        }
    }

    @FXML
    private void handleOpenIncident() {
        int index = incidentList.getSelectionModel().getSelectedIndex();
        if (index < 0 || index >= displayedIncidents.size()) {
            return;
        }
        SessionContext.setSelectedIncident(displayedIncidents.get(index));
        SceneManager.showIncidentAssessment();
    }

    @FXML
    private void handleBack() {
        SceneManager.goBack();
    }

    @FXML
    private void handleLogout() {
        SceneManager.logout();
    }
}
