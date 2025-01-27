package exceptions.taskcreation;

/**
 * Exception thrown when an invalid date is provided during task creation.
 */
public class InvalidDateException extends TaskCreationException {

    /**
     * Returns a string representation of the exception.
     * This includes the error message indicating the correct date and time syntax.
     *
     * @return a string representation of the exception
     */
    @Override
    public String toString() {
        return "Invalid date. Please enter the date and time in syntax yyyy-mm-dd HH:mm.";
    }
}
