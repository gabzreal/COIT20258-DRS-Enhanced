package drsenhanced.client.controller;

import drsenhanced.model.Incident;
import drsenhanced.model.User;
import drsenhanced.service.IncidentService;
import drsenhanced.util.SceneManager;
import drsenhanced.util.SessionContext;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 * Displays live incidents and role navigation.
 *
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
    public void initialize() {
        configureRoleButtons();
        try {
            for (Incident incident : incidentService.getActiveIncidents()) {
                alertsList.getItems().add(
                        incident.getSeverity() + " - "
                        + incident.getType() + " - "
                        + incident.getLocation());
            }
            if (alertsList.getItems().isEmpty()) {
                alertsList.getItems().add("No active emergency alerts");
            }
        } catch (java.sql.SQLException e) {
            alertsList.getItems().add("Emergency alerts unavailable");
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
