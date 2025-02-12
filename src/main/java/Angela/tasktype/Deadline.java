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
     * A Constructor for a Deadline object with the specified
     * end time, detail, and whether this task is tagged as
     * important.
     *
     * @param end the end time of the deadline
     * @param detail the detail or description of the task
     * @param isImportant whether this task is tagged as important.
     */
    public Deadline(LocalDateTime end, String detail, boolean isImportant) {
        super(detail, isImportant);
        this.end = end;
    }

    /**
     * An overloaded constructor for a Deadline object with the specified
     * end time, detail, completion status and whether this task is tagged as
     * important.
     *
     * @param end the end time of the deadline
     * @param detail the detail or description of the task
     * @param isCompleted the completion status of the task
     * @param isImportant whether the task is tagged as important
     */
    public Deadline(LocalDateTime end, String detail, boolean isCompleted, boolean isImportant) {
        super(detail, isCompleted, isImportant);
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
        return String.format("[D%s]%s (by: %s)", super.importantMark(),
                super.toString(), DateTimeValueHandler.printDateTime(end));
    }
}
