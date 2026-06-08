/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drsenhanced.client;
/**
 * DRSClientApp launches the Disaster Response System
 * client application and initializes the primary stage.
 *
 * @author Krishna Kakani - 12279867
 */
import drsenhanced.util.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class DRSClientApp extends Application {

    @Override
    public void start(Stage stage) {

        SceneManager.setPrimaryStage(stage);

        SceneManager.showDashboard();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
