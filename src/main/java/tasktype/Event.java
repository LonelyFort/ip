package tasktype;

/**
 * Represents an event task with a start and end time/date.
 */
public class Event extends Task {
    // The start time/date of the event.
    private String start;
    // The end time/date of the event.
    private String end;

    /**
     * Constructs an Event object with a specified start time/date, end time/date, and task detail.
     *
     * @param start The start time or date of the event.
     * @param end The end time or date of the event.
     * @param detail The details of the task.
     */
    public Event(String start, String end, String detail) {
        super(detail);
        this.start = start;
        this.end = end;
    }

    /**
     * Returns a string representation of the event task,
     * which includes the task detail, start time/date, and end time/date.
     *
     * @return A string representation of the event task.
     */
    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)", super.toString(), start, end);
    }
}
