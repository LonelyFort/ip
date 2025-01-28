package Angela.exceptions.taskmodification;

/**
 * Represents an exception that is thrown when attempting to modify
 * a task list that is empty.
 */
public class ListEmptyException extends TaskModificationException {

    /**
     * Returns a string representation of the exception,
     * indicating that the task list is empty and instructing the manager to create a task first.
     *
     * @return A string representation of the exception.
     */
    @Override
    public String toString() {
        return "No task has been created. Create one first Manager.";
    }
}
