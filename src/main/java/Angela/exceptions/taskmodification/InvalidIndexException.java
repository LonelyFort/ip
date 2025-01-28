package Angela.exceptions.taskmodification;

/**
 * Represents an exception that is thrown when an invalid index is accessed
 * in the task modification process.
 */
public class InvalidIndexException extends TaskModificationException {

    // The size of the task list.
    private int listSize;

    /**
     * Constructs an InvalidIndexException with the specified list size.
     *
     * @param listSize The size of the task list.
     */
    public InvalidIndexException(int listSize) {
        super();
        this.listSize = listSize;
    }

    /**
     * Returns a string representation of the exception,
     * which includes a message indicating the invalid index and the size of the task list.
     *
     * @return A string representation of the exception.
     */
    @Override
    public String toString() {
        return "Invalid index. There are " + listSize + " task(s) in the database.";
    }
}
