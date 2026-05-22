package drsenhanced.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DateTimeUtil handles date and time formatting
 * for the DRS-Enhanced system.
 *
 * @author Gabriel Fernandez Balbuena - 12292617
 */
public class DateTimeUtil {

    /**
     * Returns the current date and time as formatted text.
     *
     * @return formatted current date and time
     */
    public static String getCurrentDateTime() {

        DateTimeFormatter formatter
                = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return LocalDateTime.now().format(formatter);
    }
}