package angela.exceptions.taskcreation;

/**
 * Exception thrown when an attempt is made to create a task with an end date and time
 * that is earlier than the start date and time.
 */
public class DateOrderException extends TaskCreationException {

    /**
     * Returns a string representation of the exception,
     * indicating that the end date and time cannot be earlier than the start date and time.
     *
     * @return a string representation of the exception
     */
    @Override
    public String toString() {
        return "End date and time cannot be earlier than the start date and time. We are not in a time loop.";
    }
}
