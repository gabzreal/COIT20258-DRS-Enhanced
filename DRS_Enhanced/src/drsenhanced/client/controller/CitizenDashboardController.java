/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drsenhanced.client.controller;

/**
 * CitizenDashboardController manages citizen access to
 * disaster reports, live incident updates, safety guides,
 * emergency preparedness resources, and shelter information.
 *
 * @author Krishna Kakani -12279867
 */

import drsenhanced.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;

public class CitizenDashboardController {

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

        // My Reports
        reportList.getItems().addAll(
                "INC-1001 | Flood | Responding",
                "INC-1002 | Fire | Resolved",
                "INC-1003 | Storm Damage | Assessment Pending"
        );

        // Live Incident Updates
        incidentList.getItems().addAll(
                "🔥 Fire - Richmond",
                "🌊 Flood Warning - Southbank",
                "⚡ Power Outage - Footscray",
                "🌪 Storm Alert - Melbourne CBD",
                "🚧 Road Closure - Docklands"
        );

        // Safety Guide Types
        guideType.getItems().addAll(
                "Bushfire",
                "Flood",
                "Storm",
                "Heatwave"
        );

        guideType.setValue("Bushfire");

        guideText.setText(
                "Before:\n"
                + "• Prepare emergency kit\n"
                + "• Monitor VicEmergency alerts\n\n"
                + "During:\n"
                + "• Follow evacuation orders\n"
                + "• Leave early if instructed\n\n"
                + "After:\n"
                + "• Avoid damaged areas\n"
                + "• Follow emergency services advice"
        );

        guideType.setOnAction(event -> {

            String selected = guideType.getValue();

            switch (selected) {

                case "Bushfire":

                    guideText.setText(
                            "Before:\n"
                            + "• Prepare emergency kit\n"
                            + "• Monitor VicEmergency alerts\n\n"
                            + "During:\n"
                            + "• Follow evacuation orders\n"
                            + "• Leave early if instructed\n\n"
                            + "After:\n"
                            + "• Avoid damaged areas\n"
                            + "• Follow emergency services advice"
                    );
                    break;

                case "Flood":

                    guideText.setText(
                            "Before:\n"
                            + "• Move valuables to higher levels\n"
                            + "• Prepare emergency supplies\n\n"
                            + "During:\n"
                            + "• Never drive through flood water\n"
                            + "• Follow SES instructions\n\n"
                            + "After:\n"
                            + "• Avoid contaminated water"
                    );
                    break;

                case "Storm":

                    guideText.setText(
                            "Before:\n"
                            + "• Secure outdoor objects\n\n"
                            + "During:\n"
                            + "• Stay indoors\n"
                            + "• Avoid windows and power lines\n\n"
                            + "After:\n"
                            + "• Report hazards safely"
                    );
                    break;

                case "Heatwave":

                    guideText.setText(
                            "Before:\n"
                            + "• Stock water supplies\n\n"
                            + "During:\n"
                            + "• Stay hydrated\n"
                            + "• Avoid outdoor activities\n\n"
                            + "After:\n"
                            + "• Check on vulnerable people"
                    );
                    break;
            }
        });
    }

    @FXML
    private void handleReportDisaster() {

        SceneManager.showDisasterReport();
    }

    @FXML
    private void handleBack() {

        SceneManager.showCitizenLogin();

    }

    @FXML
    private void handleLogout() {

        SceneManager.showCitizenLogin();

    }

}
