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
import drsenhanced.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

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

        SceneManager.showDashboard();
    }
}
