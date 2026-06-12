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
import drsenhanced.model.Incident;
import drsenhanced.model.User;
import drsenhanced.service.IncidentService;
import drsenhanced.util.SessionContext;
import drsenhanced.model.Incident;
import drsenhanced.model.User;
import drsenhanced.service.IncidentService;
import drsenhanced.util.SessionContext;
import javafx.scene.control.Label;
import java.sql.SQLException;

public class EmergencyWorkerDashboardController {

    @FXML
    private TextArea updateArea;

    @FXML
    private ListView<String> activityList;

    private final IncidentService incidentService
            = new IncidentService();

    @FXML
    private Label incidentLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label priorityLabel;

    @FXML
    private Label incidentIdLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Label affectedLabel;

    @FXML
    private Label unitsLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label commandLabel;

    @FXML
    public void initialize() {

        User worker = SessionContext.getCurrentUser();

        if (worker == null) {
            return;
        }

        try {

            for (Incident incident
                    : incidentService.getActiveIncidents()) {

                if (worker.getUsername().equals(
                        incident.getAssignedWorker())) {

                    loadMission(incident);
                    return;
                }
            }

            incidentLabel.setText("No assigned incident");

        } catch (Exception e) {

            incidentLabel.setText("Unable to load mission");
        }
    }

    private void loadMission(Incident incident) {

        incidentLabel.setText(
                "Incident: " + incident.getType());

        locationLabel.setText(
                "Location: " + incident.getLocation());

        priorityLabel.setText(
                "Priority: " + incident.getSeverity());

        incidentIdLabel.setText(
                "Incident ID : INC-" + incident.getIncidentId());

        typeLabel.setText(
                "Type : " + incident.getType());

        affectedLabel.setText(
                "Affected Citizens : Unknown");

        unitsLabel.setText(
                "Required Units : 1");

        statusLabel.setText(
                "Area Status : " + incident.getStatus());

        commandLabel.setText(
                "Command Centre : " + incident.getLocation());

        activityList.getItems().add(
                "Assigned to Incident #"
                + incident.getIncidentId());
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

        User worker = SessionContext.getCurrentUser();

        if (worker == null) {
            return;
        }

        try {

            for (Incident incident
                    : incidentService.getActiveIncidents()) {

                if (worker.getUsername().equals(
                        incident.getAssignedWorker())) {

                    incidentService.updateStatus(
                            incident.getIncidentId(),
                            "RESOLVED");

                    activityList.getItems().add(
                            "✅ Mission Completed Successfully");

                    Alert alert
                            = new Alert(Alert.AlertType.INFORMATION);

                    alert.setTitle("Mission Completed");
                    alert.setHeaderText(null);
                    alert.setContentText(
                            "Incident has been marked as resolved.");

                    alert.showAndWait();

                    return;
                }
            }

        } catch (SQLException e) {

            Alert alert
                    = new Alert(Alert.AlertType.ERROR);

            alert.setHeaderText("Update Failed");
            alert.setContentText(
                    "Unable to update incident status.");

            alert.showAndWait();
        }
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
