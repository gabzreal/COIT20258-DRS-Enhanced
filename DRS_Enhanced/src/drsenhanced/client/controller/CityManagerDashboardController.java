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
import javafx.scene.control.Label;
import drsenhanced.dao.ShelterDAO;
import drsenhanced.model.Shelter;
import javafx.scene.control.Label;

/**
 * Displays active incidents and recent operational activity.
 *
 * @author Krishna Kakani -12279867
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class CityManagerDashboardController {

    private final IncidentService incidentService = new IncidentService();
    private final DashboardService dashboardService = new DashboardService();
    private final List<Incident> displayedIncidents = new ArrayList<>();
    private final ShelterDAO shelterDAO = new ShelterDAO();
    @FXML
    private ListView<String> incidentList;

    @FXML
    private ListView<String> responseLogList;

    @FXML
    private Label activeIncidentsLabel;

    @FXML
    private Label criticalIncidentsLabel;

    @FXML
    private Label unitsDeployedLabel;

    @FXML
    private Label availableSheltersLabel;

    @FXML
    public void initialize() {
        try {
            displayedIncidents.addAll(incidentService.getActiveIncidents());
            // Active incidents count
            activeIncidentsLabel.setText(
                    String.valueOf(displayedIncidents.size()));

            // Critical incidents count
            long criticalCount = displayedIncidents.stream()
                    .filter(i
                            -> "HIGH".equalsIgnoreCase(i.getSeverity())
                    || "CRITICAL".equalsIgnoreCase(i.getSeverity()))
                    .count();

            criticalIncidentsLabel.setText(
                    String.valueOf(criticalCount));
            // Available shelters
            availableSheltersLabel.setText(
                    String.valueOf(
                            shelterDAO.findAvailable().size()));
            for (Incident incident : displayedIncidents) {
                incidentList.getItems().add(
                        incident.getType() + " - " + incident.getLocation()
                        + " [" + incident.getSeverity() + "]");
            }
            if (displayedIncidents.isEmpty()) {
                incidentList.getItems().add("No active incidents");
            }

            List<ResponseLogDAO.ResponseLog> logs
                    = dashboardService.getRecentResponseLogs(10);

            unitsDeployedLabel.setText(
                    String.valueOf(logs.size()));

            for (ResponseLogDAO.ResponseLog log : logs) {

                responseLogList.getItems().add(
                        log.timestamp() + " " + log.action());
            }
            
            for (ResponseLogDAO.ResponseLog log : logs) {
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
