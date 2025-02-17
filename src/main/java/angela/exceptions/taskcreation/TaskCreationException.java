package angela.exceptions.taskcreation;

/**
 * Exception thrown when an error occurs during the creation of a new task.
 */
public class TaskCreationException extends Exception {

    /**
     * Returns a string representation of the exception, indicating that an error occurred while creating new tasks.
     *
     * @return a string representation of the exception
     */
    @Override
    public String toString() {
        return "Error creating new tasks.";
    }
}
