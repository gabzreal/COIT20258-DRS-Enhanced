package drsenhanced.util;

import drsenhanced.model.User;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controls scene transitions, route guards, and Back navigation history.
 * @author Krishna Kakani -12279867
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public final class SceneManager {

    private static final double WIDTH = 1400;
    private static final double HEIGHT = 850;

    private static final Route DASHBOARD = new Route(
            "/drsenhanced/client/view/Dashboard.fxml",
            "DRS Enhanced - Dashboard", null, false);
    private static final Route LOGIN = new Route(
            "/drsenhanced/client/view/LoginView.fxml",
            "DRS Enhanced - Login", null, true);
    private static final Route CITIZEN_ACCESS = new Route(
            "/drsenhanced/client/view/CitizenAccessView.fxml",
            "Citizen Access", null, true);
    private static final Route CITIZEN_LOGIN = new Route(
            "/drsenhanced/client/view/CitizenLoginView.fxml",
            "Citizen Login", null, true);
    private static final Route CREATE_ACCOUNT = new Route(
            "/drsenhanced/client/view/CreateAccountView.fxml",
            "Create Account", null, true);
    private static final Route CITIZEN_DASHBOARD = new Route(
            "/drsenhanced/client/view/CitizenDashboardView.fxml",
            "Citizen Dashboard", "Citizen", false);
    private static final Route DISASTER_REPORT = new Route(
            "/drsenhanced/client/view/DisasterReportView.fxml",
            "Report Emergency", null, false);
    private static final Route MANAGER_LOGIN = new Route(
            "/drsenhanced/client/view/CityManagerLoginView.fxml",
            "City Manager Login", null, true);
    private static final Route MANAGER_DASHBOARD = new Route(
            "/drsenhanced/client/view/CityManagerDashboardView.fxml",
            "City Manager Dashboard", "CityManager", false);
    private static final Route INCIDENT_ASSESSMENT = new Route(
            "/drsenhanced/client/view/IncidentAssessmentView.fxml",
            "Incident Assessment", "CityManager", false);
    private static final Route WORKER_LOGIN = new Route(
            "/drsenhanced/client/view/EmergencyWorkerLoginView.fxml",
            "Emergency Worker Portal", null, true);
    private static final Route WORKER_DASHBOARD = new Route(
            "/drsenhanced/client/view/EmergencyWorkerDashboardView.fxml",
            "Emergency Worker Dashboard", "EmergencyWorker", false);

    private static final Deque<Route> history = new ArrayDeque<>();
    private static Stage primaryStage;
    private static Route currentRoute;

    private SceneManager() {
    }

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
        primaryStage.setMinWidth(WIDTH);
        primaryStage.setMinHeight(HEIGHT);
        primaryStage.setMaximized(true);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void switchScene(String fxmlPath, String title) {
        navigate(new Route(fxmlPath, title, null, false));
    }

    public static void goBack() {
        while (!history.isEmpty()) {
            Route previous = history.pop();
            if (isAllowed(previous) && !skipForAuthenticatedUser(previous)) {
                show(previous, false);
                return;
            }
        }
        show(defaultRoute(), false);
    }

    public static void logout() {
        SessionContext.clear();
        history.clear();
        currentRoute = null;
        show(DASHBOARD, false);
    }

    public static void showLogin() {
        navigate(LOGIN);
    }

    public static void showDashboard() {
        navigate(DASHBOARD);
    }

    public static void showCitizenAccess() {
        navigate(CITIZEN_ACCESS);
    }

    public static void showDisasterReport() {
        navigate(DISASTER_REPORT);
    }

    public static void showCitizenLogin() {
        navigate(CITIZEN_LOGIN);
    }

    public static void showCreateAccount() {
        navigate(CREATE_ACCOUNT);
    }

    public static void showCitizenDashboard() {
        navigate(CITIZEN_DASHBOARD);
    }

    public static void showCityManagerLogin() {
        navigate(MANAGER_LOGIN);
    }

    public static void showCityManagerDashboard() {
        navigate(MANAGER_DASHBOARD);
    }

    public static void showIncidentAssessment() {
        navigate(INCIDENT_ASSESSMENT);
    }

    public static void showEmergencyWorkerLogin() {
        navigate(WORKER_LOGIN);
    }

    public static void showEmergencyWorkerDashboard() {
        navigate(WORKER_DASHBOARD);
    }

    public static void showCommandCentre() {
        showCityManagerDashboard();
    }

    public static void showFieldOperations() {
        showEmergencyWorkerDashboard();
    }

    public static void showShelterManagement() {
        showCitizenDashboard();
    }

    public static void showSeverityAnalysis() {
        showIncidentAssessment();
    }

    public static void showInfrastructureImpact() {
        showIncidentAssessment();
    }

    private static void navigate(Route requestedRoute) {
        Route route;
        if (skipForAuthenticatedUser(requestedRoute)) {
            route = defaultRoute();
        } else {
            route = isAllowed(requestedRoute)
                    ? requestedRoute
                    : loginRouteFor(requestedRoute.requiredRole());
        }
        if (currentRoute != null && !currentRoute.equals(route)) {
            history.push(currentRoute);
        }
        show(route, false);
    }

    private static void show(Route route, boolean addHistory) {
        if (addHistory && currentRoute != null) {
            history.push(currentRoute);
        }
        try {
            FXMLLoader loader = new FXMLLoader(
                    SceneManager.class.getResource(route.fxmlPath()));
            Parent root = loader.load();
            primaryStage.setTitle(route.title());
            primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
            primaryStage.centerOnScreen();
            primaryStage.show();
            currentRoute = route;
        } catch (IOException e) {
            System.err.println("Failed to load FXML: " + route.fxmlPath());
            e.printStackTrace();
        }
    }

    private static boolean isAllowed(Route route) {
        if (route.requiredRole() == null) {
            return true;
        }
        User user = SessionContext.getCurrentUser();
        return user != null
                && normalizeRole(user.getRole()).equals(
                        normalizeRole(route.requiredRole()));
    }

    private static boolean skipForAuthenticatedUser(Route route) {
        return SessionContext.getCurrentUser() != null
                && route.authenticationEntry();
    }

    private static Route defaultRoute() {
        User user = SessionContext.getCurrentUser();
        if (user == null) {
            return DASHBOARD;
        }
        return switch (normalizeRole(user.getRole())) {
            case "citizen" ->
                CITIZEN_DASHBOARD;
            case "citymanager" ->
                MANAGER_DASHBOARD;
            case "emergencyworker" ->
                WORKER_DASHBOARD;
            default ->
                DASHBOARD;
        };
    }

    private static Route loginRouteFor(String role) {
        return switch (normalizeRole(role)) {
            case "citizen" ->
                CITIZEN_ACCESS;
            case "citymanager" ->
                MANAGER_LOGIN;
            case "emergencyworker" ->
                WORKER_LOGIN;
            default ->
                LOGIN;
        };
    }

    private static String normalizeRole(String role) {
        return role == null
                ? ""
                : role.replace(" ", "").toLowerCase();
    }

    private record Route(String fxmlPath, String title,
            String requiredRole, boolean authenticationEntry) {

    }
}
