/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drsenhanced.client.controller;

import drsenhanced.util.SceneManager;
import javafx.fxml.FXML;

public class CitizenLoginController {

    @FXML
    private void handleLogin() {

        SceneManager.showCitizenDashboard();
    }

    @FXML
    private void handleBack() {

        SceneManager.showCitizenAccess();
    }
}
