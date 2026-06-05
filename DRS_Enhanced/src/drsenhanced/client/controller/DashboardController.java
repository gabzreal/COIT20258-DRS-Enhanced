package drsenhanced.client.controller;

import drsenhanced.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class DashboardController {

    @FXML
    private ListView<String> alertsList;

    @FXML
    public void initialize() {

        alertsList.getItems().addAll(
                "🔥 Fire Alert - Industrial Zone",
                "🌊 Flood Warning - North Bridge",
                "🚧 Road Closure - Downtown",
                "⚡ Power Outage - East Sector",
                "⛈ Severe Storm Warning"
        );
    }

    @FXML
    private void handleCitizen() {
        SceneManager.showCitizenAccess();
    }

    @FXML
    private void handleManager() {

        SceneManager.showCityManagerDashboard();
    }

    @FXML
    private void handleWorker() {
        System.out.println("Worker Login");
    }
}
