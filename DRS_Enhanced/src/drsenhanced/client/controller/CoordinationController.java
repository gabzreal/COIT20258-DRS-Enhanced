/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package drsenhanced.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import drsenhanced.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * CoordinationController handles incident coordination and resource assignment
 * operations for the DRS Enhanced system.
 *
 * @author Krishna Kakani | 12279867
 */
public class CoordinationController {

    @FXML
    private ComboBox<String> cmbIncident;

    @FXML
    private ComboBox<String> cmbDepartment;

    @FXML
    private ComboBox<String> cmbResource;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private TextArea txtNotes;

    @FXML
    private ComboBox<String> cmbPriority;

  

    /**
     * Initializes coordination data.
     */
    @FXML
    public void initialize() {

        cmbIncident.getItems().addAll(
                "Flood Watch - Maribyrnong",
                "Grassfire Alert - Werribee South",
                "Storm Warning - Melbourne CBD",
                "Chemical Spill - Port Melbourne",
                "Traffic Incident - Monash Freeway"
        );

        cmbDepartment.getItems().addAll(
                "Fire Department",
                "Police Department",
                "Ambulance Service",
                "SES"
        );

        cmbResource.getItems().addAll(
                "FRV Engine",
                "SES Rescue Unit",
                "Ambulance Victoria Unit",
                "Victoria Police Patrol",
                "Emergency Shelter Team"
        );

        cmbStatus.getItems().addAll(
                "Pending",
                "In Progress",
                "Completed",
                "Escalated"
        );
        cmbPriority.getItems().addAll(
                "Low",
                "Medium",
                "High",
                "Critical"
        );

        

    }

    /**
     * Assigns resources to incidents.
     */
    @FXML
    private void handleAssignResource() {

        System.out.println("Resource Assigned");
    }

    /**
     * Updates incident status.
     */
    @FXML
    private void handleUpdateStatus() {

        System.out.println("Status Updated");
    }

    /**
     * Returns to dashboard.
     */
    /**
     * Returns to dashboard.
     */
    @FXML
    private void handleBack(ActionEvent event) {

        try {

            Stage stage
                    = (Stage) ((Node) event.getSource())
                            .getScene()
                            .getWindow();

            SceneManager.switchScene(
                    stage,
                    "Dashboard.fxml");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    
}
