package angela.exceptions.printlist;

/**
 * Exception thrown when an invalid syntax is used for the find command.
 */
public class InvalidPrintSyntaxException extends PrintListException {

    /**
     * Returns a string representation of the exception, indicating that the syntax for the find command is invalid.
     *
     * @return a string representation of the exception
     */
    @Override
    public String toString() {
        return "Invalid syntax for find command. Please check the manual again Manager.";
    }
}
