package drsenhanced.client;

import drsenhanced.model.User;
import drsenhanced.util.SceneManager;
import drsenhanced.util.SessionContext;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * Verifies navigation history and authenticated route guards.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class ClientNavigationSmokeTest extends Application {

    @Override
    public void start(Stage stage) {
        SceneManager.setPrimaryStage(stage);

        SceneManager.showDashboard();
        requireTitle(stage, "DRS Enhanced - Dashboard");

        SceneManager.showCitizenAccess();
        requireTitle(stage, "Citizen Access");

        SessionContext.setCurrentUser(
                new User(1, "citizen", "", "Citizen"));
        SceneManager.showCitizenDashboard();
        requireTitle(stage, "Citizen Dashboard");

        SceneManager.goBack();
        requireTitle(stage, "DRS Enhanced - Dashboard");

        SceneManager.showCitizenAccess();
        requireTitle(stage, "Citizen Dashboard");

        SceneManager.showDisasterReport();
        requireTitle(stage, "Report Emergency");
        SceneManager.goBack();
        requireTitle(stage, "Citizen Dashboard");

        SceneManager.logout();
        requireTitle(stage, "DRS Enhanced - Dashboard");
        if (SessionContext.getCurrentUser() != null) {
            throw new IllegalStateException("Logout did not clear session");
        }

        SceneManager.showCitizenDashboard();
        requireTitle(stage, "Citizen Access");

        System.out.println("Navigation smoke test passed.");
        Platform.exit();
    }

    private void requireTitle(Stage stage, String expected) {
        if (!expected.equals(stage.getTitle())) {
            throw new IllegalStateException(
                    "Expected '" + expected + "' but was '"
                    + stage.getTitle() + "'");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
