package angela.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class DateTImeValueHandlerTest {

    @Test
    public void parseDateTimeTest() {
        assertEquals(LocalDateTime.of(2025,12,31,0,0), DateTimeValueHandler.parseDateTime("2025-12-31 00:00"));
        assertEquals(LocalDateTime.of(9999,1,1,23,59), DateTimeValueHandler.parseDateTime("9999-01-01 23:59"));
    }

    @Test
    public void parseDateTimeExceptionTest() {
        try {
            assertEquals(LocalDateTime.of(2025,12,31,0,0), DateTimeValueHandler.parseDateTime("2025-12-32 00:00"));
            fail();
        } catch (DateTimeParseException e) {
            assertEquals("Text '2025-12-32 00:00' could not be parsed: Invalid value for DayOfMonth (valid values 1 - 28/31): 32", e.getMessage());
        }

        try {
            assertEquals(LocalDateTime.of(2025,12,31,0,0), DateTimeValueHandler.parseDateTime("2025-12-32 24:60"));
            fail();
        } catch (DateTimeParseException e) {
            assertEquals("Text '2025-12-32 24:60' could not be parsed: Invalid value for DayOfMonth (valid values 1 - 28/31): 32", e.getMessage());
        }

        try {
            assertEquals(LocalDateTime.of(2024,12,31,22,22), DateTimeValueHandler.parseDateTime("2024/12/31 22:22"));
            fail();
        } catch (DateTimeParseException e) {
            assertEquals("Text '2024/12/31 22:22' could not be parsed at index 4", e.getMessage());
        }
    }

    @Test
    public void printDateTimeTest() {
        assertEquals("2025/12/25 01:33", DateTimeValueHandler.printDateTime(LocalDateTime.of(2025, 12, 25, 13, 33)));

        assertEquals("0001/01/01 11:59", DateTimeValueHandler.printDateTime(LocalDateTime.of(1,1,1,23,59)));
    }

    @Test
    public void saveDateTime() {
        assertEquals("2025-12-25 01:33", DateTimeValueHandler.saveDateTime(LocalDateTime.of(2025, 12, 25, 13, 33)));

        assertEquals("0001-01-01 11:59", DateTimeValueHandler.saveDateTime(LocalDateTime.of(1,1,1,23,59)));
    }
}
