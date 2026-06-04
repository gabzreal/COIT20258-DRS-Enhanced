/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drsenhanced.client.controller;

import drsenhanced.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class CitizenAccessController {

    @FXML
    private ListView<String> alertsList;

    @FXML
    public void initialize() {

        alertsList.getItems().addAll(
                "🌊 Flood Warning - Southbank",
                "🚧 Road Closure - Docklands",
                "⚡ Power Outage - Footscray",
                "⛈ Storm Warning - Melbourne CBD",
                "🔥 Fire Alert - Richmond"
        );
    }

    @FXML
    private void handleLogin() {

        SceneManager.showCitizenDashboard();
    }

    @FXML
    private void handleCreateAccount() {

        SceneManager.showCreateAccount();
    }

    @FXML
    private void handleReportDisaster() {

        SceneManager.showDisasterReport();
    }

    @FXML
    private void handleBack() {

        SceneManager.showDashboard();
    }
}
