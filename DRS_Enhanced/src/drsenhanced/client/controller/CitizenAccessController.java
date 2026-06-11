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
 * Provides citizen entry points and live incident alerts.
 * @author Krishna Kakani -12279867
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class CitizenAccessController {

    private final AuthenticationService authenticationService
            = new AuthenticationService();
    private final IncidentService incidentService = new IncidentService();

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ListView<String> alertsList;

    @FXML
    public void initialize() {
        try {
            for (Incident incident : incidentService.getActiveIncidents()) {
                alertsList.getItems().add(
                        incident.getType() + " - " + incident.getLocation());
            }
            if (alertsList.getItems().isEmpty()) {
                alertsList.getItems().add("No active alerts");
            }
        } catch (java.sql.SQLException e) {
            alertsList.getItems().add("Alerts unavailable");
        }
    }

    @FXML
    private void handleLogin() {
        if (authenticationService.login(
                usernameField.getText(),
                passwordField.getText(),
                "Citizen").isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Citizen login failed");
            alert.setContentText("Invalid username or password.");
            alert.showAndWait();
            return;
        }
        SceneManager.showCitizenDashboard();
    }

    @FXML
    private void handleCreateAccount() {
        SceneManager.showCreateAccount();
    }

    @FXML
    private void handleReportDisaster() {
        SceneManager.showDisasterReport();
    }

    @FXML
    private void handleBack() {
        SceneManager.goBack();
    }
}
