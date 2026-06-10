package drsenhanced.service;

import drsenhanced.dao.UserDAO;
import drsenhanced.model.User;
import drsenhanced.util.SessionContext;
import java.util.Optional;

/**
 * Authenticates users and manages the application session.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class AuthenticationService {

    private final UserDAO userDAO = new UserDAO();

    public Optional<User> login(
            String username, String password, String requiredRole) {
        Optional<User> user = userDAO.authenticate(username, password)
                .filter(candidate -> roleMatches(
                candidate.getRole(), requiredRole));
        user.ifPresent(SessionContext::setCurrentUser);
        return user;
    }

    public void logout() {
        SessionContext.clear();
    }

    private boolean roleMatches(String actualRole, String requiredRole) {
        if (requiredRole == null || requiredRole.isBlank()) {
            return true;
        }
        return normalizeRole(actualRole).equals(normalizeRole(requiredRole));
    }

    private String normalizeRole(String role) {
        return role == null
                ? ""
                : role.replace(" ", "").toLowerCase();
    }
}
