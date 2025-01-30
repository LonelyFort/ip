package Angela.tasktype;

import java.time.LocalDateTime;
import Angela.util.DateTimeValueHandler;

/**
 * Represents an event task with a start and end time/date.
 */
public class Event extends Task {
    // The start time/date of the event.
    private LocalDateTime start;
    // The end time/date of the event.
    private LocalDateTime end;

    /**
     * Constructs an Event object with a specified start time/date, end time/date, and task detail.
     *
     * @param start The start time or date of the event.
     * @param end The end time or date of the event.
     * @param detail The details of the task.
     */
    public Event(LocalDateTime start, LocalDateTime end, String detail) {
        super(detail);
        this.start = start;
        this.end = end;
    }

    /**
     * An overloaded constructor for a Event object with the specified end time, detail, and completion status.
     *
     * @param start the start time of the event
     * @param end the end time of the event
     * @param detail the detail or description of the event
     * @param isCompleted the completion status of the event
     */
    public Event(LocalDateTime start, LocalDateTime end, String detail, boolean isCompleted) {
        super(detail, isCompleted);
        this.start = start;
        this.end = end;
    }

    /**
     * Converts the Event task into a specific string format for saving into the database.
     *
     * @return a string representation of the Event in the save format
     */
    @Override
    public String toSaveFormat() {
        return "E" + super.toSaveFormat() + "|"
                + DateTimeValueHandler.saveDateTime(start) + "|"
                + DateTimeValueHandler.saveDateTime(end);
    }

    /**
     * Returns a string representation of the event task,
     * which includes the task detail, start time/date, and end time/date.
     *
     * @return A string representation of the event task.
     */
    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)",
                super.toString(),
                DateTimeValueHandler.printDateTime(start),
                DateTimeValueHandler.printDateTime(end));
    }
}
