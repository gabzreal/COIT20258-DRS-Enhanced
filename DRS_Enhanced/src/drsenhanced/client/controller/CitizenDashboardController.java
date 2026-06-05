/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drsenhanced.client.controller;

import drsenhanced.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class CitizenDashboardController {

    @FXML
    private ListView<String> reportList;

    @FXML
    private ListView<String> notificationList;

    @FXML
    public void initialize() {

        reportList.getItems().addAll(
                "INC-1001 | Flood | Responding",
                "INC-1002 | Fire | Resolved",
                "INC-1003 | Storm Damage | Assessment Pending"
        );

        notificationList.getItems().addAll(
                "Flood Warning - Southbank",
                "Road Closure - Docklands",
                "Storm Alert - Melbourne CBD",
                "Power Outage - Footscray"
        );
    }

    @FXML
    private void handleLogout() {

        SceneManager.showDashboard();
    }
}
