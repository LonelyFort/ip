package Angela.tasktype;
import java.time.LocalDateTime;
import Angela.util.DateTimeValueHandler;

/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {
    // The end time or date of the deadline.
    private LocalDateTime end;

    /**
     * Constructs a Deadline object with a specified end time/date and task detail.
     *
     * @param end The end time or date of the deadline.
     * @param detail The details of the task.
     */
    public Deadline(LocalDateTime end, String detail) {
        super(detail);
        this.end = end;
    }

    /**
     * An overloaded constructor for a Deadline object with the specified end time, detail, and completion status.
     *
     * @param end the end time of the deadline
     * @param detail the detail or description of the deadline
     * @param isCompleted the completion status of the deadline
     */
    public Deadline(LocalDateTime end, String detail, boolean isCompleted) {
        super(detail, isCompleted);
        this.end = end;
    }

    /**
     * Converts the Deadline task into a specific string format for saving into the database.
     *
     * @return a string representation of the Deadline in the save format
     */
    @Override
    public String toSaveFormat() {
        return "D" + super.toSaveFormat() + "|" + DateTimeValueHandler.saveDateTime(end);
    }

    /**
     * Returns a string representation of the deadline task,
     * which includes the task detail and the end time/date.
     *
     * @return A string representation of the deadline task.
     */
    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), DateTimeValueHandler.printDateTime(end));
    }
}
