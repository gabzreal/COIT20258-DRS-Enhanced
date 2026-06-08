/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drsenhanced.client.controller;
/**
 * EmergencyWorkerLoginController manages emergency worker
 * authentication and access to assigned missions.
 *
 * @author Krishna Kakani -12279867
 */
import drsenhanced.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class EmergencyWorkerLoginController {

    @FXML
    private ListView<String> alertList;

    @FXML
    public void initialize() {

        alertList.getItems().addAll(
                "🔥 Fire - Melbourne CBD",
                "🌊 Flood - Docklands",
                "⚡ Power Outage - Richmond",
                "⛈ Storm Damage - Carlton",
                "🏢 Building Collapse - Footscray"
        );

    }

    @FXML
    private void handleLogin() {

        SceneManager.showEmergencyWorkerDashboard();

    }

    @FXML
    private void handleBack() {

        SceneManager.showDashboard();

    }

}
