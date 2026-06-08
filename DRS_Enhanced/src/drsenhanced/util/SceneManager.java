/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drsenhanced.util;
/**
 * SceneManager controls application navigation and
 * handles scene transitions between system views.
 *
 * @author Krishna Kakani -12279867
 */
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class SceneManager {

    private static Stage primaryStage;

    // Window settings
    private static final double MIN_WIDTH = 1280;
    private static final double MIN_HEIGHT = 720;

    private SceneManager() {
    }

    /**
     * Called once from DRSClientApp
     */
    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;

        primaryStage.setMinWidth(1400);
        primaryStage.setMinHeight(850);

        primaryStage.setMaximized(true);
    }

    /**
     * Returns current stage
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Generic scene loader
     */
    public static void switchScene(String fxmlPath, String title) {

        try {

            FXMLLoader loader
                    = new FXMLLoader(SceneManager.class.getResource(fxmlPath));

            Parent root = loader.load();

            Scene scene = new Scene(root, 1400, 850);

            primaryStage.setTitle(title);

            primaryStage.setScene(scene);

            primaryStage.centerOnScreen();

            primaryStage.show();

        } catch (IOException e) {

            System.err.println(
                    "Failed to load FXML: " + fxmlPath
            );

            e.printStackTrace();
        }
    }

    // ==================================================
    // LOGIN
    // ==================================================
    public static void showLogin() {

        switchScene(
                "/drsenhanced/client/view/LoginView.fxml",
                "DRS Enhanced - Login"
        );
    }

    // ==================================================
    // HOME DASHBOARD
    // ==================================================
    public static void showDashboard() {

        switchScene(
                "/drsenhanced/client/view/Dashboard.fxml",
                "DRS Enhanced - Dashboard"
        );
    }

    // ==================================================
    // CITIZEN
    // ==================================================
    public static void showCitizenAccess() {

        switchScene(
                "/drsenhanced/client/view/CitizenAccessView.fxml",
                "Citizen Access"
        );
    }

    public static void showDisasterReport() {

        switchScene(
                "/drsenhanced/client/view/DisasterReportView.fxml",
                "Report Emergency"
        );
    }

    public static void showCitizenLogin() {

        switchScene(
                "/drsenhanced/client/view/CitizenLoginView.fxml",
                "Citizen Login"
        );
    }

    public static void showCreateAccount() {

        switchScene(
                "/drsenhanced/client/view/CreateAccountView.fxml",
                "Create Account"
        );
    }

    public static void showCitizenDashboard() {

        switchScene(
                "/drsenhanced/client/view/CitizenDashboardView.fxml",
                "Citizen Dashboard"
        );
    }

    // ==================================================
    // COMMAND CENTRE / CITY MANAGER
    // ==================================================
    public static void showCommandCentre() {

        switchScene(
                "/drsenhanced/client/view/CoordinationView.fxml",
                "Disaster Command Centre"
        );
    }

    public static void showCityManagerDashboard() {

        switchScene(
                "/drsenhanced/client/view/CityManagerDashboardView.fxml",
                "City Manager Dashboard"
        );
    }

    public static void showIncidentAssessment() {

        switchScene(
                "/drsenhanced/client/view/IncidentAssessmentView.fxml",
                "Incident Assessment"
        );
    }

    // ==================================================
    // EMERGENCY WORKER
    // ==================================================
    public static void showFieldOperations() {

        switchScene(
                "/drsenhanced/client/view/DepartmentAvailabilityView.fxml",
                "Emergency Worker Terminal"
        );
    }

    // ==================================================
    // EVACUATION SHELTERS
    // ==================================================
    public static void showShelterManagement() {

        switchScene(
                "/drsenhanced/client/view/EvacuationShelterView.fxml",
                "Evacuation Shelter Management"
        );
    }

    // ==================================================
    // FUTURE FEATURE 1
    // ==================================================
    public static void showSeverityAnalysis() {

        switchScene(
                "/drsenhanced/client/view/SeverityAnalysisView.fxml",
                "Severity Analysis"
        );
    }

    // ==================================================
    // FUTURE FEATURE 2
    // ==================================================
    public static void showInfrastructureImpact() {

        switchScene(
                "/drsenhanced/client/view/InfrastructureImpactView.fxml",
                "Infrastructure Impact Analysis"
        );
    }

    public static void showEmergencyWorkerLogin() {

        switchScene(
                "/drsenhanced/client/view/EmergencyWorkerLoginView.fxml",
                "Emergency Worker Portal"
        );

    }

    public static void showEmergencyWorkerDashboard() {

        switchScene(
                "/drsenhanced/client/view/EmergencyWorkerDashboardView.fxml",
                "Emergency Worker Dashboard"
        );

    }

    // ==================================================
    // LOGOUT
    // ==================================================
    public static void logout() {

        showLogin();
    }
}
