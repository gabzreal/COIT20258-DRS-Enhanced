/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drsenhanced.client.controller;

/**
 * EmergencyWorkerDashboardController allows emergency workers to view mission
 * details, update field status, request additional support, and complete
 * incidents.
 *
 * @author Krishna Kakani - 12279867
 */
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import drsenhanced.util.SceneManager;

public class EmergencyWorkerDashboardController {

    @FXML
    private TextArea updateArea;

    @FXML
    private ListView<String> activityList;

    @FXML
    public void initialize() {

        activityList.getItems().addAll(
                "11:20 Assigned to Incident",
                "11:24 Team Dispatched",
                "11:30 Travelling to Site",
                "11:35 ETA Updated"
        );
    }

    @FXML
    private void handleSubmitUpdate() {

        String update = updateArea.getText();

        if (!update.trim().isEmpty()) {

            activityList.getItems().add(
                    "Worker Update: " + update
            );

            updateArea.clear();
        }
    }

    @FXML
    private void handleRequestAmbulance() {

        activityList.getItems().add(
                "🚑 Ambulance support requested"
        );
    }

    @FXML
    private void handleRequestPolice() {

        activityList.getItems().add(
                "👮 Police support requested"
        );
    }

    @FXML
    private void handleRequestShelter() {

        activityList.getItems().add(
                "🏠 Shelter team requested"
        );
    }

    @FXML
    private void handleCompleteMission() {

        activityList.getItems().add(
                "✅ Mission Completed Successfully"
        );

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mission Completed");
        alert.setHeaderText(null);
        alert.setContentText("Incident has been marked as resolved.");
        alert.showAndWait();
    }

    @FXML
    private void handleBack() {
        SceneManager.showDashboard();
    }

    @FXML
    private void handleLogout() {
        SceneManager.logout();
    }
}
