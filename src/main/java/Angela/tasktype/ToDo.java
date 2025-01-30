package Angela.tasktype;

/**
 * Represents a simple to-do task.
 */
public class ToDo extends Task {

    /**
     * Constructs a ToDo object with specified details.
     *
     * @param detail The details of the to-do task.
     */
    public ToDo(String detail) {
        super(detail);
    }

    /**
     * An overloaded constructor for a ToDo object with the specified end time, detail, and completion status.
     *
     * @param detail the detail or description of the ToDo
     * @param isCompleted the completion status of the ToDo
     */
    public ToDo(String detail, boolean isCompleted) {
        super(detail, isCompleted);
    }

    /**
     * Converts the ToDO task into a specific string format for saving into the database.
     *
     * @return a string representation of the ToDo in the save format
     */
    @Override
    public String toSaveFormat() {
        return "T" + super.toSaveFormat();
    }

    /**
     * Returns a string representation of the to-do task,
     * which includes the task detail.
     *
     * @return A string representation of the to-do task.
     */
    @Override
    public String toString() {
        return String.format("[T]%s", super.toString());
    }
}
