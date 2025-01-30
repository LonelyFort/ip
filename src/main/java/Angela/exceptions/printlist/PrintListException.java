package Angela.exceptions.printlist;

/**
 * Exception thrown when an error occurs during the printing of list items.
 */
public class PrintListException extends Exception {

    /**
     * Returns a string representation of the exception, indicating that an error occurred while printing list items.
     *
     * @return a string representation of the exception
     */
    @Override
    public String toString() {
        return "Error printing list items.";
    }
}
