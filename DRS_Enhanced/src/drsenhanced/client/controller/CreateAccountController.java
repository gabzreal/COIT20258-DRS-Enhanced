/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drsenhanced.client.controller;
/**
 * CreateAccountController handles citizen account
 * registration and validation processes.
 *
 * @author Krishna Kakani - 12279867
 */
import drsenhanced.service.CitizenService;
import drsenhanced.util.SceneManager;
import drsenhanced.util.SessionContext;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CreateAccountController {

    private final CitizenService citizenService = new CitizenService();

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private ComboBox<String> suburbCombo;

    @FXML
    public void initialize() {

        suburbCombo.getItems().addAll(
                "Melbourne CBD",
                "Docklands",
                "Southbank",
                "Richmond",
                "Carlton",
                "Brunswick",
                "Footscray",
                "St Kilda",
                "North Melbourne",
                "Fitzroy"
        );
    }

    @FXML
    private void handleCreateAccount() {

        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            showError("Passwords do not match.");
            return;
        }

        var user = citizenService.createAccount(
                emailField.getText(),
                passwordField.getText(),
                fullNameField.getText());
        if (user.isEmpty()) {
            showError("Account could not be created. Check required fields "
                    + "or use a different email.");
            return;
        }
        SessionContext.setCurrentUser(user.get());
        SceneManager.showCitizenDashboard();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Account creation failed");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleBack() {

        SceneManager.goBack();
    }
}
