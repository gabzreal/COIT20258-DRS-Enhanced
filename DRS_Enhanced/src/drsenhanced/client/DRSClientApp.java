/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package drsenhanced.client;

import drsenhanced.database.DatabaseInitializer;
import drsenhanced.util.SceneManager;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * DRSClientApp launches the Disaster Response System
 * client application and initializes the primary stage.
 *
 * @author Krishna Kakani - 12279867
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class DRSClientApp extends Application {

    @Override
    public void start(Stage stage) throws SQLException {
        try {
            DatabaseInitializer.initialize();
        } catch (SQLException e) {
            showDatabaseError(e);
            throw e;
        }
        SceneManager.setPrimaryStage(stage);
        SceneManager.showDashboard();
    }

    private void showDatabaseError(SQLException error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Database Setup Failed");
        alert.setHeaderText("The application could not prepare its database.");
        alert.setContentText(
                "Check that MySQL is running and that DRS_DB_USER and "
                + "DRS_DB_PASSWORD are correct.\n\n"
                + error.getMessage());
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
