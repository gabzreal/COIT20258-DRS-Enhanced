/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java
 */
package drsenhanced.client.controller;
/**
 * LoginController handles citizen authentication
 * and navigation to the Citizen Dashboard.
 *
 * @author Krishna Kakani - 12279867
 */
import drsenhanced.service.AuthenticationService;
import drsenhanced.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    private final AuthenticationService authenticationService
            = new AuthenticationService();

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    public void initialize() {

        roleComboBox.getItems().addAll(
                "Citizen",
                "City Manager",
                "Emergency Worker"
        );
    }

    @FXML
    private void handleLogin() {
        String role = roleComboBox.getValue();
        if (role == null || authenticationService.login(
                usernameField.getText(),
                passwordField.getText(),
                role).isEmpty()) {
            showError("Invalid username, password, or role.");
            return;
        }

        switch (role) {
            case "Citizen" -> SceneManager.showCitizenDashboard();
            case "City Manager" -> SceneManager.showCityManagerDashboard();
            case "Emergency Worker" ->
                SceneManager.showEmergencyWorkerDashboard();
            default -> showError("Select a valid role.");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Login failed");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
