package drsenhanced.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import drsenhanced.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.control.TextArea;

/**
 * DashboardController handles the dashboard operations for the DRS Enhanced
 * system.
 *
 * @author Krishna Kakani | 12279867
 */
public class DashboardController {

    @FXML
    private Label activeIncidentsLabel;

    @FXML
    private Label criticalCasesLabel;

    @FXML
    private Label availableDepartmentsLabel;

    @FXML
    private Label shelterCapacityLabel;

    @FXML
    private Label lblDateTime;

    @FXML
    private TextArea incidentFeedArea;

    @FXML
    private TextArea activityArea;

    @FXML
    private TextArea departmentStatusArea;

    @FXML
    private Label lblSystemStatus;

    /**
     * Initializes dashboard data.
     */
    @FXML
    public void initialize() {

        loadDashboardStatistics();

        initializeClock();

        loadIncidentFeed();

        loadRecentActivity();

        loadDepartmentStatus();

        loadSystemStatus();
    }

    /**
     * Loads sample dashboard statistics.
     */
    private void loadDashboardStatistics() {

        activeIncidentsLabel.setText("18");

        criticalCasesLabel.setText("5");

        availableDepartmentsLabel.setText("12");

        shelterCapacityLabel.setText("430");
    }

    /**
     * Opens disaster reports screen.
     */
    @FXML
    private void openDisasterReport(ActionEvent event) {

        try {

            Stage stage
                    = (Stage) ((javafx.scene.Node) event.getSource())
                            .getScene().getWindow();

            SceneManager.switchScene(
                    stage,
                    "DisasterReportView.fxml");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * Opens emergency dispatch screen.
     */
    @FXML
    private void openDepartmentAvailability(ActionEvent event) {

        try {

            Stage stage
                    = (Stage) ((javafx.scene.Node) event.getSource())
                            .getScene().getWindow();

            SceneManager.switchScene(
                    stage,
                    "DepartmentAvailabilityView.fxml");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * Opens hospital management screen.
     */
    @FXML
    private void openEvacuationShelter(ActionEvent event) {

        try {

            Stage stage
                    = (Stage) ((javafx.scene.Node) event.getSource())
                            .getScene().getWindow();

            SceneManager.switchScene(
                    stage,
                    "EvacuationShelterView.fxml");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * Opens alert center screen.
     */
    @FXML
    private void openCoordinationCenter(ActionEvent event) {

        try {

            Stage stage
                    = (Stage) ((javafx.scene.Node) event.getSource())
                            .getScene().getWindow();

            SceneManager.switchScene(
                    stage,
                    "CoordinationView.fxml");

        } catch (Exception e) {

            e.printStackTrace();
        }
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
    /**
     * Handles logout functionality.
     */
    /**
     * Handles logout functionality.
     */
    @FXML
    private void handleLogout(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Logout");

        alert.setHeaderText("Confirm Logout");

        alert.setContentText("Are you sure you want to logout?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()
                && result.get() == ButtonType.OK) {

            try {

                Stage stage
                        = (Stage) ((Node) event.getSource())
                                .getScene()
                                .getWindow();

                SceneManager.switchScene(
                        stage,
                        "LoginView.fxml");

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    /**
     * Displays live date and time.
     */
    private void initializeClock() {

        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        event -> {

                            lblDateTime.setText(
                                    LocalDate.now().format(
                                            DateTimeFormatter.ofPattern(
                                                    "dd MMM yyyy"))
                                    + " - "
                                    + LocalTime.now().format(
                                            DateTimeFormatter.ofPattern(
                                                    "HH:mm:ss")));
                        }));

        timeline.setCycleCount(Timeline.INDEFINITE);

        timeline.play();
    }

    /**
     * Loads incident feed.
     */
    private void loadIncidentFeed() {

        incidentFeedArea.setText(
                """
            🚨 Flood Watch - Maribyrnong River

            🔥 Grassfire Alert - Werribee South

            ⛈ Severe Storm Warning - Melbourne CBD

            🚑 Multi-Vehicle Accident - Monash Freeway

            ⚠ Hazardous Material Spill - Port Melbourne

            🚧 Road Closure - CityLink Tunnel
            """
        );
    }

    /**
     * Loads recent emergency activity.
     */
    private void loadRecentActivity() {

        activityArea.setText(
                """
            23:10 Ambulance dispatched to Monash Freeway

            23:05 SES deployed to Maribyrnong

            22:58 Fire Rescue assigned to Werribee

            22:51 Shelter activated at Footscray

            22:45 Police established traffic diversion
            """
        );
    }

    /**
     * Loads department operational status.
     */
    private void loadDepartmentStatus() {

        departmentStatusArea.setText(
                """
            Fire Rescue Victoria      AVAILABLE      12 Units

            Victoria Police          DEPLOYED       8 Units

            Ambulance Victoria       AVAILABLE      15 Units

            Victoria SES             ACTIVE         10 Units

            Emergency Management Victoria ONLINE     5 Teams
            """
        );
    }

    /**
     * Loads emergency system status.
     */
    private void loadSystemStatus() {

        lblSystemStatus.setText(
                "🟢 SYSTEM STATUS: NORMAL OPERATIONS");
    }
}
