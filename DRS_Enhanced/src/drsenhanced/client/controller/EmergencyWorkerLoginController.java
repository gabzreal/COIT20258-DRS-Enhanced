package drsenhanced.client.controller;

import drsenhanced.model.Incident;
import drsenhanced.service.AuthenticationService;
import drsenhanced.service.IncidentService;
import drsenhanced.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Authenticates emergency workers and displays live incident alerts.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class EmergencyWorkerLoginController {

    private final AuthenticationService authenticationService
            = new AuthenticationService();
    private final IncidentService incidentService = new IncidentService();

    @FXML
    private TextField workerIdField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ListView<String> alertList;

    @FXML
    public void initialize() {
        try {
            for (Incident incident : incidentService.getActiveIncidents()) {
                alertList.getItems().add(
                        incident.getType() + " - " + incident.getLocation());
            }
            if (alertList.getItems().isEmpty()) {
                alertList.getItems().add("No active incidents");
            }
        } catch (java.sql.SQLException e) {
            alertList.getItems().add("Live alerts unavailable");
        }
    }

    @FXML
    private void handleLogin() {
        if (authenticationService.login(
                workerIdField.getText(),
                passwordField.getText(),
                "EmergencyWorker").isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Worker login failed");
            alert.setContentText("Invalid worker ID or password.");
            alert.showAndWait();
            return;
        }
        SceneManager.showEmergencyWorkerDashboard();
    }

    @FXML
    private void handleBack() {
        SceneManager.goBack();
    }
}
