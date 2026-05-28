package drsenhanced.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * DashboardController handles the dashboard operations
 * for the DRS Enhanced system.
 *
 * @author Krishna Kakani | 12279867
 */
public class DashboardController {

    @FXML
    private Label activeIncidentsLabel;

    @FXML
    private Label criticalCasesLabel;

    @FXML
    private Label availableTeamsLabel;

    @FXML
    private Label hospitalBedsLabel;

    /**
     * Initializes dashboard data.
     */
    @FXML
    public void initialize() {

        loadDashboardStatistics();
    }

    /**
     * Loads sample dashboard statistics.
     */
    private void loadDashboardStatistics() {

        activeIncidentsLabel.setText("12");

        criticalCasesLabel.setText("4");

        availableTeamsLabel.setText("18");

        hospitalBedsLabel.setText("126");
    }

    /**
     * Opens disaster reports screen.
     */
    @FXML
    private void openDisasterReport() {

        System.out.println("Opening Disaster Reports...");
    }

    /**
     * Opens emergency dispatch screen.
     */
    @FXML
    private void openEmergencyDispatch() {

        System.out.println("Opening Emergency Dispatch...");
    }

    /**
     * Opens hospital management screen.
     */
    @FXML
    private void openHospitalManagement() {

        System.out.println("Opening Hospital Management...");
    }

    /**
     * Opens alert center screen.
     */
    @FXML
    private void openAlertCenter() {

        System.out.println("Opening Alert Center...");
    }

    /**
     * Refreshes dashboard analytics.
     */
    @FXML
    private void refreshDashboard() {

        loadDashboardStatistics();

        System.out.println("Dashboard Refreshed");
    }

    /**
     * Handles logout functionality.
     */
    @FXML
    private void handleLogout() {

        System.out.println("Logout clicked");
    }
}