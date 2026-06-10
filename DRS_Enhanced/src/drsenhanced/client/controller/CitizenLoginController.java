/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drsenhanced.client.controller;
/**
 * CitizenLoginController handles citizen authentication
 * and navigation to the Citizen Dashboard.
 *
 * @author Krishna Kakani -12279867
 */
import drsenhanced.service.AuthenticationService;
import drsenhanced.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CitizenLoginController {

    private final AuthenticationService authenticationService
            = new AuthenticationService();

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

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
    private void handleBack() {

        SceneManager.goBack();
    }
}
