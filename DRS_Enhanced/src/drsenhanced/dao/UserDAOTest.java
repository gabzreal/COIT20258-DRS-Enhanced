package drsenhanced.dao;

import drsenhanced.util.PasswordUtil;

/**
 * UserDAOTest is used to test user database operations.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class UserDAOTest {

    /**
     * Main method for testing user insertion.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        UserDAO userDAO = new UserDAO();

        String hashedPassword
                = PasswordUtil.hashPassword("admin123");

        boolean success = userDAO.addUser(
                "admin",
                hashedPassword,
                "System Administrator",
                "ADMIN"
        );

        if (success) {

            System.out.println("User added successfully.");

        } else {

            System.out.println("Failed to add user.");
        }
    }
}