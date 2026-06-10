package drsenhanced.client.controller;

import drsenhanced.model.Incident;
import drsenhanced.model.User;
import drsenhanced.service.DispatchService;
import drsenhanced.util.SceneManager;
import drsenhanced.util.SessionContext;
import java.sql.SQLException;
import java.util.Locale;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 * Persists manager dispatch decisions for the selected incident.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class IncidentAssessmentController {

    private final DispatchService dispatchService = new DispatchService();

    @FXML
    private Label dispatchStatusLabel;

    @FXML
    private Label incidentTitleLabel;

    @FXML
    private Label incidentLocationLabel;

    @FXML
    private Label incidentPriorityLabel;

    @FXML
    private Label incidentDetailsLabel;

    @FXML
    private ProgressBar riskProgressBar;

    @FXML
    private Label riskLevelLabel;

    @FXML
    private Label recommendedActionLabel;

    @FXML
    private void initialize() {
        displayIncident(SessionContext.getSelectedIncident());
    }

    @FXML
    private void handleDispatch() {
        Incident incident = SessionContext.getSelectedIncident();
        User manager = SessionContext.getCurrentUser();
        if (incident == null || manager == null) {
            showStatus("Select an incident from the dashboard first.", false);
            return;
        }

        try {
            var result = dispatchService.dispatchIncident(
                    incident.getIncidentId(),
                    manager.getUserId(),
                    "Emergency Response Unit");
            incident.setAssignedWorker(result.workerUsername());
            incident.setStatus("DISPATCHED");
            displayIncident(incident);
            showStatus("Assigned to " + result.workerUsername(), true);
        } catch (SQLException e) {
            showStatus("Dispatch could not be saved", false);
        }
    }

    @FXML
    private void handleBack() {
        SceneManager.goBack();
    }

    private void displayIncident(Incident incident) {
        if (incident == null) {
            incidentTitleLabel.setText("NO INCIDENT SELECTED");
            incidentLocationLabel.setText("Return to the dashboard and select an incident.");
            incidentPriorityLabel.setText("Priority: UNKNOWN");
            incidentDetailsLabel.setText("Incident details unavailable");
            riskProgressBar.setProgress(0);
            riskLevelLabel.setText("UNKNOWN");
            recommendedActionLabel.setText("Select an incident for assessment");
            return;
        }

        String type = normalize(incident.getType(), "Unknown");
        String severity = normalize(incident.getSeverity(), "Unknown").toUpperCase(Locale.ROOT);
        String status = normalize(incident.getStatus(), "Unknown");
        String worker = normalize(incident.getAssignedWorker(), "Unassigned");

        incidentTitleLabel.setText(type.toUpperCase(Locale.ROOT) + " INCIDENT");
        incidentLocationLabel.setText(normalize(incident.getLocation(), "Location unavailable"));
        incidentPriorityLabel.setText("Priority: " + severity);
        incidentDetailsLabel.setText("Incident #" + incident.getIncidentId()
                + "  |  Status: " + status
                + "  |  Assigned worker: " + worker);

        double risk = riskFor(severity);
        riskProgressBar.setProgress(risk);
        riskLevelLabel.setText(severity + " (" + Math.round(risk * 100) + "%)");
        recommendedActionLabel.setText(recommendationFor(type, severity));
    }

    private double riskFor(String severity) {
        return switch (severity) {
            case "CRITICAL" -> 0.95;
            case "HIGH" -> 0.80;
            case "MEDIUM" -> 0.55;
            case "LOW" -> 0.30;
            default -> 0.10;
        };
    }

    private String recommendationFor(String type, String severity) {
        if ("CRITICAL".equals(severity) || "HIGH".equals(severity)) {
            return "Immediate multi-agency response for " + type.toLowerCase(Locale.ROOT);
        }
        if ("MEDIUM".equals(severity)) {
            return "Dispatch response team and continue monitoring";
        }
        return "Monitor incident and prepare local response";
    }

    private String normalize(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value.trim();
    }

    private void showStatus(String message, boolean success) {
        dispatchStatusLabel.setText(message);
        dispatchStatusLabel.setStyle(
                "-fx-text-fill:" + (success ? "#2E7D32" : "#C62828") + ";"
                + "-fx-font-weight:bold;");
    }
}
