package tasktype;
import java.time.LocalDateTime;
import util.DateTimeValueHandler;

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
