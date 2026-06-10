package drsenhanced.client;

import drsenhanced.model.Incident;
import drsenhanced.util.SessionContext;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Loads every active FXML view to verify controller bindings.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class ClientViewSmokeTest extends Application {

    private static final List<String> VIEWS = List.of(
            "/drsenhanced/client/view/Dashboard.fxml",
            "/drsenhanced/client/view/LoginView.fxml",
            "/drsenhanced/client/view/CitizenAccessView.fxml",
            "/drsenhanced/client/view/CitizenLoginView.fxml",
            "/drsenhanced/client/view/CreateAccountView.fxml",
            "/drsenhanced/client/view/CitizenDashboardView.fxml",
            "/drsenhanced/client/view/DisasterReportView.fxml",
            "/drsenhanced/client/view/CityManagerLoginView.fxml",
            "/drsenhanced/client/view/CityManagerDashboardView.fxml",
            "/drsenhanced/client/view/IncidentAssessmentView.fxml",
            "/drsenhanced/client/view/EmergencyWorkerLoginView.fxml",
            "/drsenhanced/client/view/EmergencyWorkerDashboardView.fxml");

    @Override
    public void start(Stage stage) throws Exception {
        for (String view : VIEWS) {
            if (view.endsWith("IncidentAssessmentView.fxml")) {
                verifySelectedIncident(view);
            } else {
                FXMLLoader.load(ClientViewSmokeTest.class.getResource(view));
            }
            System.out.println("Loaded " + view);
        }
        System.out.println("All client views loaded successfully.");
        Platform.exit();
    }

    private void verifySelectedIncident(String view) throws Exception {
        SessionContext.setSelectedIncident(new Incident(
                42, "Flood", "North District", "HIGH", "ACTIVE", null));
        Parent root = FXMLLoader.load(ClientViewSmokeTest.class.getResource(view));
        new Scene(root);
        root.applyCss();

        requireLabel(root, "#incidentTitleLabel", "FLOOD INCIDENT");
        requireLabel(root, "#incidentLocationLabel", "North District");
        requireLabel(root, "#incidentPriorityLabel", "Priority: HIGH");
        requireLabel(root, "#incidentDetailsLabel", "Incident #42");
        SessionContext.setSelectedIncident(null);
    }

    private void requireLabel(Parent root, String selector, String expectedText) {
        Label label = (Label) root.lookup(selector);
        if (label == null || !label.getText().contains(expectedText)) {
            throw new IllegalStateException(
                    selector + " did not contain '" + expectedText + "'");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
