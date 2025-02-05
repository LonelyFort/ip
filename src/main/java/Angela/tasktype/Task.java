package Angela.tasktype;

/**
 * Represents a generic task with details and completion status.
 */
public class Task {
    // The details of the task.
    private String detail;

    // The completion status of the task.
    private boolean isCompleted;

    /**
     * Constructs a Task object with specified details.
     * By default, the task is not completed.
     *
     * @param detail The details of the task.
     */
    public Task(String detail) {
        this.detail = detail;
        this.isCompleted = false;
    }

    /**
     * An overloaded constructor for a general Task object with the specified end time, detail, and completion status.
     *
     * @param detail the detail or description of the task
     * @param isCompleted the completion status of the task
     */
    public Task(String detail, boolean isCompleted) {
        this.detail = detail;
        this.isCompleted = isCompleted;
    }

    /**
     * Returns the symbol representing the completion status of the task.
     * An "X" symbol if the task is completed, otherwise a blank space.
     *
     * @return The symbol representing the completion status.
     */
    public String completedSymbol() {
        return isCompleted ? "X" : " ";
    }

    /**
     * Marks the task as completed.
     */
    public void check() {
        this.isCompleted = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void uncheck() {
        this.isCompleted = false;
    }

    /**
     * Returns the completion status of the task.
     *
     * @return True if the task is completed, otherwise false.
     */
    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * Converts the Task into a specific string format for saving into the database.
     *
     * @return a string representation of the Task in the save format
     */
    public String toSaveFormat() {
        return String.format("|%s|%s", completedSymbol(), this.detail);
    }

    public boolean containsKeyword(String keyword) {
        return this.detail.contains(keyword);
    }

    /**
     * Returns a string representation of the task,
     * which includes the task detail and its completion status.
     *
     * @return A string representation of the task.
     */
    @Override
    public String toString() {
        return "[" + completedSymbol() + "] " + this.detail;
    }
}
