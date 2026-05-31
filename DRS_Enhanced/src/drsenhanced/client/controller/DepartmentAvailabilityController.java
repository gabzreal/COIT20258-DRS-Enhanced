package drsenhanced.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import drsenhanced.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class DepartmentAvailabilityController {

    @FXML
    private ComboBox<String> cmbDepartment;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private TextField txtAvailableUnits;

    @FXML
    public void initialize() {

        cmbDepartment.getItems().addAll(
                "Victoria Police",
                "Fire Rescue Victoria",
                "Ambulance Victoria",
                "Victoria SES",
                "Emergency Management Victoria"
        );

        cmbStatus.getItems().addAll(
                "Available",
                "Busy",
                "Unavailable"
        );
    }

    @FXML
    private void handleUpdateAvailability() {

        if (cmbDepartment.getValue() == null
                || cmbStatus.getValue() == null
                || txtAvailableUnits.getText().isBlank()) {

            System.out.println("Please complete all fields.");
            return;
        }

        System.out.println("Availability Updated");
    }

    @FXML
    private void handleRefresh() {

        System.out.println("Refresh Clicked");
    }

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
