package drsenhanced.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import drsenhanced.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class DisasterReportController {

    @FXML
    private ComboBox<String> cmbDisasterType;

    @FXML
    private TextField txtLocation;

    @FXML
    private TextField txtPeopleAffected;

    @FXML
    private TextArea txtDescription;

    @FXML
    private ComboBox<String> cmbPriority;

    @FXML
    private ComboBox<String> cmbSuburb;

    @FXML
    private TextField txtReportedBy;

    @FXML
    public void initialize() {

        cmbDisasterType.getItems().addAll(
                "Flood",
                "Grassfire",
                "Storm",
                "Hazardous Material Spill",
                "Road Traffic Incident",
                "Building Fire",
                "Medical Emergency"
        );
        cmbPriority.getItems().addAll(
                "Low",
                "Medium",
                "High",
                "Critical"
        );

        cmbSuburb.getItems().addAll(
                "Melbourne CBD",
                "Footscray",
                "Werribee",
                "Sunshine",
                "Broadmeadows",
                "Dandenong",
                "Frankston",
                "Geelong"
        );
    }

    @FXML
    private void handleSubmitReport() {

        if (cmbDisasterType.getValue() == null
                || txtLocation.getText().isBlank()
                || txtPeopleAffected.getText().isBlank()) {

            System.out.println("Please complete all required fields.");
            return;
        }

        System.out.println("Report Submitted");
    }

    @FXML
    private void handleClear() {

        cmbDisasterType.setValue(null);
        txtLocation.clear();
        txtPeopleAffected.clear();
        txtDescription.clear();
    }

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
