/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drsenhanced.client.controller;
/**
 * Handles City Manager authentication before
 * granting access to the Emergency Command Centre.
 *
 * Temporary placeholder credentials are used until
 * database authentication is integrated.
 *
 * @author Krishna Kakani - 12296276
 */
import drsenhanced.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CityManagerLoginController {

    @FXML
    private TextField managerIdField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private void handleLogin() {

        String managerId = managerIdField.getText();
        String password = passwordField.getText();

        // Placeholder authentication
        if (managerId.equals("manager")
                && password.equals("admin")) {

            SceneManager.showCityManagerDashboard();

        } else {

            messageLabel.setText("Invalid Manager ID or Password");

        }
    }

    @FXML
    private void handleBack() {

        SceneManager.showDashboard();

    }
}
