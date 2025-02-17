package angela.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * A utility class to handle date and time values.
 */
public class DateTimeValueHandler {

    /**
     * Parses a date and time string into a LocalDateTime object.
     * The input date string must be in the format "yyyy-MM-dd HH:mm".
     *
     * @param date the date and time string to be parsed
     * @return the parsed LocalDateTime object
     * @throws DateTimeParseException if the date string cannot be parsed
     */
    public static LocalDateTime parseDateTime(String date) throws DateTimeParseException {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.US));
    }

    /**
     * Formats a LocalDateTime object into a date and time string.
     * The output string will be in the format "yyyy/MM/dd hh:mm".
     *
     * @param date the LocalDateTime object to be formatted
     * @return the formatted date and time string
     */
    public static String printDateTime(LocalDateTime date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm");
        return dateFormatter.format(date);
    }

    /**
     * Converts a {@link LocalDateTime} object into a formatted string representation.
     * The date and time are formatted using the pattern "yyyy-MM-dd hh:mm", which represents
     * the year, month, day, hour, and minute in a human-readable format.
     *
     * @param date The {@link LocalDateTime} object to be formatted. Must not be null.
     * @return A string representation of the provided date and time, formatted as "yyyy-MM-dd hh:mm".
     */
    public static String saveDateTime(LocalDateTime date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
        return dateFormatter.format(date);
    }
}
