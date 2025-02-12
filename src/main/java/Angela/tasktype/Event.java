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
     * A Constructor for a Deadline object with the specified start time/date,
     * end time/date, detail, and whether this task is tagged as important.
     *
     * @param start The start time or date of the event.
     * @param end The end time or date of the event.
     * @param detail the detail or description of the task
     * @param isImportant whether this task is tagged as important.
     */
    public Event(LocalDateTime start, LocalDateTime end, String detail, boolean isImportant) {
        super(detail, isImportant);
        this.start = start;
        this.end = end;
    }

    /**
     * An overloaded constructor for a Event object with the specified
     * end time, detail, completion status and whether this task is tagged as
     * important.
     *
     * @param start The start time or date of the event.
     * @param end The end time or date of the event.
     * @param detail the detail or description of the task
     * @param isCompleted the completion status of the task
     * @param isImportant whether the task is tagged as important
     */
    public Event(LocalDateTime start, LocalDateTime end, String detail, boolean isCompleted, boolean isImportant) {
        super(detail, isCompleted, isImportant);
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
        return String.format("[E%s]%s (from: %s to: %s)",
                super.importantMark(),
                super.toString(),
                DateTimeValueHandler.printDateTime(start),
                DateTimeValueHandler.printDateTime(end));
    }
}
