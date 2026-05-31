/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drsenhanced.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import drsenhanced.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * EvacuationShelterController handles evacuation shelter operations for the DRS
 * Enhanced system.
 *
 * @author Krishna Kakani | 12279867
 */
public class EvacuationShelterController {

    @FXML
    private TextField txtPeopleAffected;

    @FXML
    private ComboBox<String> cmbShelter;

    @FXML
    private Label lblCapacity;

    @FXML
    private Label lblOccupied;

    @FXML
    private Label lblRemaining;

    /**
     * Initializes shelter information.
     */
    @FXML
    public void initialize() {

        cmbShelter.getItems().addAll(
                "Footscray Community Centre",
                "Sunshine Civic Centre",
                "Melbourne Convention Centre",
                "Werribee Community Hall",
                "Broadmeadows Emergency Shelter"
        );

        lblCapacity.setText("500");
        lblOccupied.setText("150");
        lblRemaining.setText("350");
    }

    /**
     * Checks shelter capacity.
     */
    @FXML
    private void handleCheckCapacity() {

        System.out.println("Checking Shelter Capacity...");
    }

    /**
     * Assigns affected people to shelter.
     */
    @FXML
    private void handleAssignShelter() {

        if (txtPeopleAffected.getText().isBlank()
                || cmbShelter.getValue() == null) {

            System.out.println("Please complete all fields.");
            return;
        }

        System.out.println("Shelter Assigned");
    }

    /**
     * Returns to dashboard.
     */
    @FXML
    private void handleBack(ActionEvent event) {

        try {

            Stage stage
                    = (Stage) ((javafx.scene.Node) event.getSource())
                            .getScene().getWindow();

            SceneManager.switchScene(
                    stage,
                    "Dashboard.fxml");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
