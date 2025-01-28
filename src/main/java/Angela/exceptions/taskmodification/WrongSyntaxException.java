package Angela.exceptions.taskmodification;

/**
 * Represents an exception that is thrown when the syntax used
 * for modifying a task is incorrect.
 */
public class WrongSyntaxException extends TaskModificationException {

    /**
     * Returns a string representation of the exception,
     * indicating that the rank of the list item to be modified should be entered,
     * and that negative numbers are not accepted.
     *
     * @return A string representation of the exception.
     */
    @Override
    public String toString() {
        return "Enter the rank of the list item you want to modify. Negative number is not accepted.";
    }
}
