package drsenhanced.client.controller;

import drsenhanced.model.Incident;
import drsenhanced.model.User;
import drsenhanced.service.CitizenService;
import drsenhanced.service.IncidentService;
import drsenhanced.util.SceneManager;
import drsenhanced.util.SessionContext;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * Displays citizen reports, active incidents, and safety guidance.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class CitizenDashboardController {

    private final CitizenService citizenService = new CitizenService();
    private final IncidentService incidentService = new IncidentService();
    @FXML
    private ListView<String> reportList;

    @FXML
    private ListView<String> incidentList;

    @FXML
    private ComboBox<String> guideType;

    @FXML
    private Label guideText;

    @FXML
    public void initialize() {
        loadDatabaseData();

        guideType.getItems().addAll(
                "Bushfire", "Flood", "Storm", "Heatwave");
        guideType.setValue("Bushfire");
        updateGuide("Bushfire");
        guideType.setOnAction(event -> updateGuide(guideType.getValue()));
    }

    @FXML
    private void handleReportDisaster() {
        SceneManager.showDisasterReport();
    }

    @FXML
    private void handleBack() {
        SceneManager.goBack();
    }

    @FXML
    private void handleLogout() {
        SceneManager.logout();
    }

    private void loadDatabaseData() {
        User user = SessionContext.getCurrentUser();
        if (user == null) {
            reportList.getItems().add("Login required to view reports");
        } else {
            try {
                for (Incident incident
                        : citizenService.getCitizenIncidents(user.getUserId())) {
                    reportList.getItems().add(formatIncident(incident));
                }
                if (reportList.getItems().isEmpty()) {
                    reportList.getItems().add("No reports submitted");
                }
            } catch (java.sql.SQLException e) {
                reportList.getItems().add("Reports unavailable");
            }
        }

        try {
            for (Incident incident : incidentService.getActiveIncidents()) {
                incidentList.getItems().add(
                        incident.getType() + " - " + incident.getLocation()
                        + " [" + incident.getStatus() + "]");
            }
            if (incidentList.getItems().isEmpty()) {
                incidentList.getItems().add("No active incidents");
            }
        } catch (java.sql.SQLException e) {
            incidentList.getItems().add("Live incidents unavailable");
        }
    }

    private String formatIncident(Incident incident) {
        return "INC-" + incident.getIncidentId()
                + " | " + incident.getType()
                + " | " + incident.getStatus();
    }

    private void updateGuide(String selected) {
        String text = switch (selected) {
            case "Flood" -> "Before:\n"
                    + "- Move valuables to higher levels\n"
                    + "- Prepare emergency supplies\n\n"
                    + "During:\n"
                    + "- Never drive through flood water\n"
                    + "- Follow SES instructions\n\n"
                    + "After:\n- Avoid contaminated water";
            case "Storm" -> "Before:\n- Secure outdoor objects\n\n"
                    + "During:\n- Stay indoors\n"
                    + "- Avoid windows and power lines\n\n"
                    + "After:\n- Report hazards safely";
            case "Heatwave" -> "Before:\n- Stock water supplies\n\n"
                    + "During:\n- Stay hydrated\n"
                    + "- Avoid outdoor activities\n\n"
                    + "After:\n- Check on vulnerable people";
            default -> "Before:\n- Prepare emergency kit\n"
                    + "- Monitor VicEmergency alerts\n\n"
                    + "During:\n- Follow evacuation orders\n"
                    + "- Leave early if instructed\n\n"
                    + "After:\n- Avoid damaged areas\n"
                    + "- Follow emergency services advice";
        };
        guideText.setText(text);
    }
}
