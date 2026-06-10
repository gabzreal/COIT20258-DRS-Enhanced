package drsenhanced.util;

import drsenhanced.model.Incident;
import drsenhanced.model.User;

/**
 * Stores the authenticated user and currently selected incident.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public final class SessionContext {

    private static User currentUser;
    private static Incident selectedIncident;

    private SessionContext() {
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static Incident getSelectedIncident() {
        return selectedIncident;
    }

    public static void setSelectedIncident(Incident incident) {
        selectedIncident = incident;
    }

    public static void clear() {
        currentUser = null;
        selectedIncident = null;
    }
}
