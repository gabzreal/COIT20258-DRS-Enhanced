package drsenhanced.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * PasswordUtil provides SHA-256 password hashing
 * functionality for DRS-Enhanced.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class PasswordUtil {

    /**
     * Hashes a password using SHA-256.
     *
     * @param password plain text password
     * @return hashed password
     */
    public static String hashPassword(String password) {

        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hashBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();

            for (byte b : hashBytes) {

                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {

            return null;
        }
    }
}