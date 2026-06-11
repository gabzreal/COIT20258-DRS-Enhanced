package drsenhanced.client.controller;

import drsenhanced.model.Incident;
import drsenhanced.model.User;
import drsenhanced.service.IncidentService;
import drsenhanced.util.SceneManager;
import drsenhanced.util.SessionContext;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import java.util.List;
import drsenhanced.service.DashboardService;

/**
 * Displays live incidents and role navigation.
 * @author Krishna Kakani -12279867
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class DashboardController {

    private final IncidentService incidentService = new IncidentService();

    @FXML
    private ListView<String> alertsList;

    @FXML
    private Button citizenButton;

    @FXML
    private Button managerButton;

    @FXML
    private Button workerButton;

    @FXML
    private Label activeIncidentsLabel;

    @FXML
    private Label criticalIncidentsLabel;

    @FXML
    private Label responsesTodayLabel;

    private final DashboardService dashboardService
            = new DashboardService();

    @FXML
    public void initialize() {
        configureRoleButtons();
        try {

            List<Incident> incidents
                    = incidentService.getActiveIncidents();

            activeIncidentsLabel.setText(
                    String.valueOf(incidents.size()));

            long criticalCount
                    = incidents.stream()
                            .filter(i
                                    -> "HIGH".equalsIgnoreCase(i.getSeverity())
                            || "CRITICAL".equalsIgnoreCase(i.getSeverity()))
                            .count();

            criticalIncidentsLabel.setText(
                    String.valueOf(criticalCount));

            responsesTodayLabel.setText(
                    String.valueOf(
                            dashboardService.getResponsesToday()));

            for (Incident incident : incidents) {

                alertsList.getItems().add(
                        incident.getSeverity() + " - "
                        + incident.getType() + " - "
                        + incident.getLocation());
            }

            if (alertsList.getItems().isEmpty()) {
                alertsList.getItems().add(
                        "No active emergency alerts");
            }

        } catch (java.sql.SQLException e) {

            activeIncidentsLabel.setText("0");
            criticalIncidentsLabel.setText("0");
            responsesTodayLabel.setText("0");

            alertsList.getItems().add(
                    "Emergency alerts unavailable");
        }
    }

    private void configureRoleButtons() {
        User user = SessionContext.getCurrentUser();
        if (user == null) {
            return;
        }

        String role = normalizeRole(user.getRole());
        citizenButton.setDisable(!"citizen".equals(role));
        managerButton.setDisable(!"citymanager".equals(role));
        workerButton.setDisable(!"emergencyworker".equals(role));

        switch (role) {
            case "citizen" ->
                citizenButton.setText("Open Citizen Dashboard");
            case "citymanager" ->
                managerButton.setText("Open City Manager Dashboard");
            case "emergencyworker" ->
                workerButton.setText("Open Emergency Worker Dashboard");
            default -> {
                citizenButton.setDisable(true);
                managerButton.setDisable(true);
                workerButton.setDisable(true);
            }
        }
    }

    private String normalizeRole(String role) {
        return role == null
                ? ""
                : role.replace(" ", "").toLowerCase();
    }

    @FXML
    private void handleCitizen() {
        SceneManager.showCitizenAccess();
    }

    @FXML
    private void handleManager() {
        SceneManager.showCityManagerLogin();
    }

    @FXML
    private void handleWorker() {
        SceneManager.showEmergencyWorkerLogin();
    }
}
