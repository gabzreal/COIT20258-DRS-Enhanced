/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java
 */
package drsenhanced.client.controller;

import drsenhanced.client.network.ClientConnection;
import drsenhanced.server.MessageProtocol;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import drsenhanced.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

/**
 * LoginController handles the login screen functionality for the DRS Enhanced
 * system.
 *
 * @author Krishna Kakani | 12279867
 */
public class LoginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblStatus;

    @FXML
    private Button btnLogin;

    /**
     * Handles login button action.
     *
     * Sends LOGIN request to the DRS server.
     *
     * @param event Login button click event
     */
    @FXML
    private void handleLogin(ActionEvent event) {

        /*
         * Get entered username and password
         */
        String username = txtUsername.getText();

        String password = txtPassword.getText();

        try {

            /*
             * Create server connection
             */
            ClientConnection connection
                    = new ClientConnection();

            /*
             * Connect to server
             */
            connection.connect();

            /*
             * Build LOGIN request
             * Format:
             * LOGIN|username|password
             */
            String request
                    = MessageProtocol.LOGIN
                    + "|"
                    + username
                    + "|"
                    + password;

            /*
             * Send request to server
             */
            String response
                    = connection.sendRequest(request);

            /*
             * Print server response
             */
            System.out.println(
                    "Server Response: "
                    + response
            );

            /*
             * Check if login succeeded
             */
            if (response.startsWith(
                    MessageProtocol.SUCCESS)) {

                lblStatus.setText(
                        "Login successful"
                );

                /*
                 * Open dashboard screen
                 */
                openDashboard();

            } else {

                lblStatus.setText(
                        "Invalid username or password"
                );
            }

            /*
             * Disconnect from server
             */
            connection.disconnect();

        } catch (Exception e) {

            lblStatus.setText(
                    "Server connection failed"
            );

            e.printStackTrace();
        }
    }

    /**
     * Opens the dashboard screen.
     */
    private void openDashboard() {

        try {

            /*
             * Load Dashboard.fxml
             */
            FXMLLoader loader
                    = new FXMLLoader(
                            getClass().getResource(
                                    "/drsenhanced/client/view/Dashboard.fxml"
                            )
                    );

            Parent root = loader.load();

            /*
             * Get current stage
             */
            Stage stage
                    = (Stage) btnLogin
                            .getScene()
                            .getWindow();

            /*
             * Create dashboard scene
             */
            Scene scene
                    = new Scene(root);

            /*
             * Set dashboard scene
             */
            stage.setScene(scene);

            /*
             * Set dashboard title
             */
            stage.setTitle(
                    "DRS Enhanced Dashboard"
            );

            /*
             * Show dashboard
             */
            stage.show();

        } catch (IOException e) {

            lblStatus.setText(
                    "Failed to open dashboard"
            );

            e.printStackTrace();
        }
    }

    /**
     * Clears username and password fields.
     *
     * @param event Clear button click event
     */
    @FXML
    private void handleClear(ActionEvent event) {

        /*
         * Clear input fields
         */
        txtUsername.clear();

        txtPassword.clear();

        /*
         * Reset status label
         */
        lblStatus.setText(
                "System Ready"
        );
    }
}
