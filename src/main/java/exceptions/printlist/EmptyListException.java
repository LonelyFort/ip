package exceptions.printlist;

/**
 * Represents an exception that is thrown when there is an attempt
 * to print a list that is empty.
 */
public class EmptyListException extends PrintListException {

    /**
     * Returns a string representation of the exception,
     * indicating that no data has been stored in the database.
     *
     * @return A string representation of the exception.
     */
    @Override
    public String toString() {
        return "No data has been stored in the database.";
    }
}
