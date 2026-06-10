package drsenhanced.client.controller;

import drsenhanced.model.Incident;
import drsenhanced.model.StatusUpdate;
import drsenhanced.model.User;
import drsenhanced.service.DispatchService;
import drsenhanced.service.IncidentService;
import drsenhanced.service.WorkerService;
import drsenhanced.util.SceneManager;
import drsenhanced.util.SessionContext;
import java.sql.SQLException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

/**
 * Provides database-backed emergency worker mission operations.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class EmergencyWorkerDashboardController {

    private final IncidentService incidentService = new IncidentService();
    private final WorkerService workerService = new WorkerService();
    private final DispatchService dispatchService = new DispatchService();
    private Incident currentIncident;

    @FXML
    private TextArea updateArea;

    @FXML
    private ListView<String> activityList;

    @FXML
    public void initialize() {
        User worker = SessionContext.getCurrentUser();
        if (worker == null) {
            activityList.getItems().add("Worker login required");
            return;
        }

        try {
            List<Incident> incidents = incidentService.getAssignedIncidents(
                    worker.getUsername());
            if (incidents.isEmpty()) {
                activityList.getItems().add("No assigned mission");
                return;
            }
            currentIncident = incidents.get(0);
            SessionContext.setSelectedIncident(currentIncident);
            activityList.getItems().add(
                    "Assigned: " + currentIncident.getType()
                    + " - " + currentIncident.getLocation());
            for (StatusUpdate update : workerService.getIncidentUpdates(
                    currentIncident.getIncidentId())) {
                activityList.getItems().add(
                        update.getTimestamp() + " " + update.getMessage());
            }
        } catch (SQLException e) {
            activityList.getItems().add("Mission data unavailable");
        }
    }

    @FXML
    private void handleSubmitUpdate() {
        User worker = SessionContext.getCurrentUser();
        String update = updateArea.getText().trim();
        if (worker == null || currentIncident == null || update.isEmpty()) {
            return;
        }
        try {
            workerService.submitStatusUpdate(
                    currentIncident.getIncidentId(),
                    worker.getUserId(),
                    update);
            activityList.getItems().add("Worker Update: " + update);
            updateArea.clear();
        } catch (SQLException e) {
            showError("Status update could not be saved.");
        }
    }

    @FXML
    private void handleRequestAmbulance() {
        requestSupport("Ambulance");
    }

    @FXML
    private void handleRequestPolice() {
        requestSupport("Police");
    }

    @FXML
    private void handleRequestShelter() {
        requestSupport("Shelter Team");
    }

    @FXML
    private void handleCompleteMission() {
        if (currentIncident == null) {
            return;
        }
        try {
            incidentService.updateStatus(
                    currentIncident.getIncidentId(), "RESOLVED");
            activityList.getItems().add("Mission completed successfully");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mission Completed");
            alert.setHeaderText(null);
            alert.setContentText("Incident has been marked as resolved.");
            alert.showAndWait();
        } catch (SQLException e) {
            showError("Incident status could not be updated.");
        }
    }

    @FXML
    private void handleBack() {
        SceneManager.goBack();
    }

    private void requestSupport(String resourceType) {
        if (currentIncident == null) {
            return;
        }
        try {
            dispatchService.requestSupport(
                    currentIncident.getIncidentId(), resourceType);
            activityList.getItems().add(resourceType + " requested");
        } catch (SQLException e) {
            showError("Support request could not be saved.");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Database operation failed");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
