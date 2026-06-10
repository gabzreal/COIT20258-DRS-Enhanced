/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drsenhanced.client.controller;

/**
 * IncidentAssessmentController supports incident analysis, risk evaluation,
 * resource allocation, and emergency response recommendations.
 *
 * @author Krishna Kakani -12279867
 */
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
