/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drsenhanced.client.controller;
/**
 * CreateAccountController handles citizen account
 * registration and validation processes.
 *
 * @author Krishna Kakani - 12279867
 */
import drsenhanced.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class CreateAccountController {

    @FXML
    private ComboBox<String> suburbCombo;

    @FXML
    public void initialize() {

        suburbCombo.getItems().addAll(
                "Melbourne CBD",
                "Docklands",
                "Southbank",
                "Richmond",
                "Carlton",
                "Brunswick",
                "Footscray",
                "St Kilda",
                "North Melbourne",
                "Fitzroy"
        );
    }

    @FXML
    private void handleCreateAccount() {

        System.out.println("Account Created");

        SceneManager.showCitizenDashboard();
    }

    @FXML
    private void handleBack() {

        SceneManager.showCitizenAccess();
    }
}
