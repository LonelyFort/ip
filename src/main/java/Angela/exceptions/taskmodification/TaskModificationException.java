package Angela.exceptions.taskmodification;

/**
 * Represents a generic exception that occurs during task modification.
 */
public class TaskModificationException extends Exception {

    /**
     * Returns a string representation of the exception,
     * indicating an error occurred while modifying the task list.
     *
     * @return A string representation of the exception.
     */
    @Override
    public String toString() {
        return "Error modifying the list.";
    }
}
