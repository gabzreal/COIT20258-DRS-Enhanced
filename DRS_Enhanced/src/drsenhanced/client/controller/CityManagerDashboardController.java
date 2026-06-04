/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drsenhanced.client.controller;

import javafx.fxml.FXML;

import javafx.scene.control.ListView;
import drsenhanced.util.SceneManager;

public class CityManagerDashboardController {

    @FXML
    private ListView<String> incidentList;

  

  

    @FXML
    public void initialize() {

        incidentList.getItems().addAll(
                "🔥 Fire - Melbourne CBD [HIGH]",
                "🌊 Flood - Docklands [MEDIUM]",
                "⛈ Storm Damage - Richmond [MEDIUM]",
                "⚡ Power Outage - Southbank [LOW]",
                "🏢 Building Collapse - Footscray [HIGH]"
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
