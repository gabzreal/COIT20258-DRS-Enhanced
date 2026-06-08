/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drsenhanced.client.controller;

/**
 * CityManagerDashboardController provides operational
 * monitoring of active incidents, resource capacity,
 * emergency shelters, and response activities.
 *
 * @author Krishna Kakani - 12279867
 */
import javafx.fxml.FXML;

import javafx.scene.control.ListView;
import drsenhanced.util.SceneManager;

public class CityManagerDashboardController {

    @FXML
    private ListView<String> incidentList;

    @FXML
    private ListView<String> responseLogList;

    @FXML
    public void initialize() {

        incidentList.getItems().addAll(
                "🔥 Fire - Melbourne CBD [HIGH]",
                "🌊 Flood - Docklands [MEDIUM]",
                "⛈ Storm Damage - Richmond [MEDIUM]",
                "⚡ Power Outage - Southbank [LOW]",
                "🏢 Building Collapse - Footscray [HIGH]"
        );

        responseLogList.getItems().addAll(
                "11:20 Fire Units Dispatched",
                "11:24 Ambulance Assigned",
                "11:31 Police Coordination Started",
                "11:38 Shelter Activated",
                "11:45 Situation Escalated"
        );
    }

    @FXML
    private void handleOpenIncident() {

        if (incidentList.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        SceneManager.showIncidentAssessment();
    }

    @FXML
    private void handleBack() {

        SceneManager.showDashboard();
    }
}
