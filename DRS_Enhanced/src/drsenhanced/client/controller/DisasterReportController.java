/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package drsenhanced.client.controller;

/**
 * DisasterReportController manages disaster report creation, submission, and
 * incident reporting.
 *
 * @author Krishna Kakani - 12279867
 */
import drsenhanced.model.User;
import drsenhanced.service.CitizenService;
import drsenhanced.util.SessionContext;
import drsenhanced.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DisasterReportController {

    private final CitizenService citizenService = new CitizenService();

    @FXML
    private Label severityLabel;

    @FXML
    private Label impactLabel;

    @FXML
    private Label safetyLabel;

    @FXML
    private Label selectedTypeLabel;

    private String selectedDisasterType;

    @FXML
    private TextField locationField;

    @FXML
    private Label selectedSeverityLabel;

    private String selectedSeverity;

    @FXML
    private Label severityStatusLabel;
    @FXML
    private VBox severityCard;

    @FXML
    private void selectFire() {

        selectedDisasterType = "Fire";
        selectedTypeLabel.setText("Selected: FIRE");
    }

    @FXML
    private void selectFlood() {

        selectedDisasterType = "Flood";
        selectedTypeLabel.setText("Selected: FLOOD");
    }

    @FXML
    private void selectStorm() {

        selectedDisasterType = "Storm";
        selectedTypeLabel.setText("Selected: STORM");
    }

    @FXML
    private void selectCollapse() {

        selectedDisasterType = "Building Collapse";
        selectedTypeLabel.setText("Selected: BUILDING COLLAPSE");
    }

    @FXML
    private void selectOutage() {

        selectedDisasterType = "Power Outage";
        selectedTypeLabel.setText("Selected: POWER OUTAGE");
    }

    @FXML
    private void selectOther() {

        selectedDisasterType = "Other";
        selectedTypeLabel.setText("Selected: OTHER");
    }

    @FXML
    private void selectMinor() {

        selectedSeverity = "Minor";

        selectedSeverityLabel.setText(
                "Selected Severity: MINOR"
        );
    }

    @FXML
    private void selectModerate() {

        selectedSeverity = "Moderate";

        selectedSeverityLabel.setText(
                "Selected Severity: MODERATE"
        );
    }

    @FXML
    private void selectSevere() {

        selectedSeverity = "Severe";

        selectedSeverityLabel.setText(
                "Selected Severity: SEVERE"
        );
    }

    @FXML
    private void handleUpload() {

        System.out.println("Upload evidence");
    }

    @FXML
    private void handleLiveLocation() {

        locationField.setText(
                "Flinders Street Station, Melbourne VIC"
        );
    }

    @FXML
    private void handleSubmit() {

        if (selectedDisasterType == null) {

            severityLabel.setText(
                    "Select disaster type first"
            );

            return;
        }

        if (locationField.getText().isBlank()) {

            severityLabel.setText(
                    "Location is required"
            );

            return;
        }

        String disaster = selectedDisasterType;
        String location = locationField.getText();

        if ("Severe".equals(selectedSeverity)) {

            severityLabel.setText("🔴 HIGH");

            severityStatusLabel.setText(
                    "Immediate response required • ETA 5 mins"
            );

            severityCard.setStyle(
                    "-fx-background-color:#FFEBEE;"
                    + "-fx-padding:20;"
                    + "-fx-background-radius:15;"
                    + "-fx-border-radius:15;"
                    + "-fx-border-color:#E53935;"
                    + "-fx-border-width:3;"
            );

        } else if ("Moderate".equals(selectedSeverity)) {

            severityLabel.setText("🟡 MEDIUM");

            severityStatusLabel.setText(
                    "Assessment recommended • ETA 15 mins"
            );

            severityCard.setStyle(
                    "-fx-background-color:#FFF8E1;"
                    + "-fx-padding:20;"
                    + "-fx-background-radius:15;"
                    + "-fx-border-radius:15;"
                    + "-fx-border-color:#F9A825;"
                    + "-fx-border-width:3;"
            );

        } else if ("Minor".equals(selectedSeverity)) {

            severityLabel.setText("🟢 LOW");

            severityStatusLabel.setText(
                    "Monitor situation • ETA 30 mins"
            );

            severityCard.setStyle(
                    "-fx-background-color:#E8F5E9;"
                    + "-fx-padding:20;"
                    + "-fx-background-radius:15;"
                    + "-fx-border-radius:15;"
                    + "-fx-border-color:#43A047;"
                    + "-fx-border-width:3;"
            );

        } else {

            severityLabel.setText("🟡 MEDIUM");

            severityStatusLabel.setText(
                    "Auto-detected severity"
            );

            severityCard.setStyle(
                    "-fx-background-color:#FFF8E1;"
                    + "-fx-padding:20;"
                    + "-fx-background-radius:15;"
                    + "-fx-border-radius:15;"
                    + "-fx-border-color:#F9A825;"
                    + "-fx-border-width:3;"
            );
        }
        switch (disaster) {

            case "Fire":

                impactLabel.setText(
                        "✓ Buildings\n"
                        + "✓ Roads\n"
                        + "✓ Public Transport\n\n"
                        + "Risk Level: HIGH"
                );

                break;

            case "Flood":

                impactLabel.setText(
                        "✓ Roads\n"
                        + "✓ Drainage Systems\n"
                        + "✓ Residential Areas\n\n"
                        + "Risk Level: MEDIUM"
                );

                break;

            case "Storm":

                impactLabel.setText(
                        "✓ Power Network\n"
                        + "✓ Roads\n"
                        + "✓ Trees & Infrastructure\n\n"
                        + "Risk Level: MEDIUM"
                );

                break;

            case "Building Collapse":

                impactLabel.setText(
                        "✓ Buildings\n"
                        + "✓ Surrounding Roads\n\n"
                        + "Risk Level: HIGH"
                );

                break;

            case "Power Outage":

                impactLabel.setText(
                        "✓ Electricity Network\n"
                        + "✓ Traffic Signals\n\n"
                        + "Risk Level: MEDIUM"
                );

                break;

            default:

                impactLabel.setText(
                        "Infrastructure review pending."
                );
        }

        

        switch (disaster) {

            case "Fire":

                safetyLabel.setText(
                        "⚠ Avoid smoke exposure\n\n"
                        + "🚶 Move to a safe area\n\n"
                        + "📞 Triple Zero (000)\n\n"
                        + "🚒 Wait for emergency crews"
                );

                break;

            case "Flood":

                safetyLabel.setText(
                        "🌊 Move to higher ground\n\n"
                        + "🚫 Do not enter floodwater\n\n"
                        + "📞 Triple Zero (000)"
                );

                break;

            case "Storm":

                safetyLabel.setText(
                        "🏠 Stay indoors\n\n"
                        + "⚡ Avoid power lines\n\n"
                        + "📞 Triple Zero (000)"
                );

                break;

            case "Building Collapse":

                safetyLabel.setText(
                        "🏢 Keep away from structure\n\n"
                        + "🚧 Follow emergency barriers\n\n"
                        + "📞 Triple Zero (000)"
                );

                break;

            default:

                safetyLabel.setText(
                        "⚠ Follow emergency services advice\n\n"
                        + "📞 Triple Zero (000)"
                );
        }

        User citizen = SessionContext.getCurrentUser();

        int citizenId = citizen != null
                ? citizen.getUserId()
                : 0;

        try {
            String severity = switch (selectedSeverity == null
                    ? "" : selectedSeverity) {
                case "Severe" ->
                    "HIGH";
                case "Minor" ->
                    "LOW";
                default ->
                    "MEDIUM";
            };
            var report = citizenService.submitReport(
                    citizenId,
                    selectedDisasterType,
                    location,
                    severity);
            severityStatusLabel.setText(
                    "Report " + report.getReportId()
                    + " saved. Incident " + report.getIncidentId()
                    + " is pending assessment.");
        } catch (java.sql.SQLException e) {
            severityStatusLabel.setText(
                    "Report could not be saved. Check database connection.");
        }

        System.out.println(
                "Incident Submitted: "
                + selectedDisasterType
        );

    }

    @FXML
    private void handleBack() {

        SceneManager.goBack();

    }
}
