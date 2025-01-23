package tasktype;

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
