package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * A utility class to handle date and time values.
 * Solution largely inspired by Clifong https://github.com/Clifong/ip.
 */
public class DateTimeValueHandler {

    public static LocalDateTime parseDateTime(String date) throws DateTimeParseException {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.US));
    }

    public static String printDateTime(LocalDateTime date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm");

        return dateFormatter.format(date);
    }
}
