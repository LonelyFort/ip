package exceptions.taskcreation;

/**
 * Represents an exception that is thrown when a task is created with an empty detail.
 */
public class EmptyDetailException extends TaskCreationException {

    /**
     * Returns a string representation of the exception,
     * indicating that the task description cannot be empty.
     *
     * @return A string representation of the exception.
     */
    @Override
    public String toString() {
        return "Task description cannot be empty.";
    }
}
