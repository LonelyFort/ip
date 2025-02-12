package Angela.tasktype;

/**
 * Represents a generic task with details and completion status.
 */
public class Task {
    // The details of the task.
    private String detail;

    // The completion status of the task.
    private boolean isCompleted;

    // Whether this task is marked as important.
    private boolean isImportant;

    /**
     * A Constructor for a general Task object with the specified
     * detail, and whether this task is tagged as
     * important.
     *
     * @param detail the detail or description of the task
     * @param isImportant whether this task is tagged as important.
     */
    public Task(String detail, boolean isImportant) {
        this.detail = detail;
        this.isImportant = isImportant;
    }

    /**
     * An overloaded constructor for a general Task object with the specified
     * end time, detail, completion status and whether this task is tagged as
     * important.
     *
     * @param detail the detail or description of the task
     * @param isCompleted the completion status of the task
     * @param isImportant whether the task is tagged as important
     */
    public Task(String detail, boolean isCompleted, boolean isImportant) {
        this.detail = detail;
        this.isCompleted = isCompleted;
        this.isImportant = isImportant;
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
        return String.format("%s|%s|%s", importantMark(), completedSymbol(), this.detail);
    }

    /**
     * Checks if the task detail contains the specified keyword.
     *
     * @param keyword the keyword to be searched for within the task.
     * @return true if the keyword is found within the detail, false otherwise.
     */
    public boolean containsKeyword(String keyword) {
        return this.detail.contains(keyword);
    }

    /**
     * Checks if the current object is marked as important.
     *
     * @return true if the object is marked as important, false otherwise.
     */
    public boolean isImportant() {
        return this.isImportant;
    }

    /**
     * Marks the current task object as important.
     */
    public void markImportant() {
        this.isImportant = true;
    }

    /**
     * Remove the current task object's importance.
     */
    public void unmarkImportant() {
        this.isImportant = false;
    }

    /**
     * Returns a string indicating the importance of the current object.
     *
     * @return "*" if the object is marked as important, an empty string otherwise.
     */
    public String importantMark() {
        return this.isImportant ? "*" : "";
    }

    /**
     * Returns a string representation of the task,
     * which includes the task detail, its completion status and whether
     * task is tagged as import.
     *
     * @return A string representation of the task.
     */
    @Override
    public String toString() {
        return "[" + completedSymbol() + "] " + this.detail;
    }
}
