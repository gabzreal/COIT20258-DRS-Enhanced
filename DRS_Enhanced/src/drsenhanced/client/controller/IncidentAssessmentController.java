/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drsenhanced.client.controller;


import drsenhanced.util.SceneManager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class IncidentAssessmentController {

    @FXML
    private Label dispatchStatusLabel;

    @FXML
    private void handleDispatch() {
        dispatchStatusLabel.setText(
                "✓ Dispatch Successful"
        );

        dispatchStatusLabel.setStyle(
                "-fx-text-fill:#2E7D32;"
                + "-fx-font-weight:bold;"
        );
    }

    @FXML
    private void handleBack() {
        SceneManager.showCityManagerDashboard();
    }
}
