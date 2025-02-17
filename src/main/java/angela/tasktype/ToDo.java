package angela.tasktype;

/**
 * Represents a simple to-do task.
 */
public class ToDo extends Task {

    /**
     * A Constructor for a ToDo object with the specified
     * detail, and whether this task is tagged as
     * important.
     *
     * @param detail the detail or description of the task
     * @param isImportant whether this task is tagged as important.
     */
    public ToDo(String detail, boolean isImportant) {
        super(detail, isImportant);
    }

    /**
     * An overloaded constructor for a ToDo object with the specified
     * detail, completion status and whether this task is tagged as
     * important.
     *
     * @param detail the detail or description of the task
     * @param isCompleted the completion status of the task
     * @param isImportant whether the task is tagged as important
     */
    public ToDo(String detail, boolean isCompleted, boolean isImportant) {
        super(detail, isCompleted, isImportant);
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
        return String.format("[T%s]%s", super.importantMark(), super.toString());
    }
}
