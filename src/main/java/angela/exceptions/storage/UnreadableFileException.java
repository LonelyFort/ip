package angela.exceptions.storage;

/**
 * Exception thrown when an unreadable entry is encountered in the database file.
 */
public class UnreadableFileException extends Exception {
    private int lineNum;

    /**
     * Constructs a new UnreadableFileException with the specified line number.
     *
     * @param lineNum the line number of the unreadable entry
     */
    public UnreadableFileException(int lineNum) {
        super();
        this.lineNum = lineNum;
    }

    /**
     * Returns a string representation of the exception, indicating the line number of the unreadable entry.
     *
     * @return a string representation of the exception
     */
    @Override
    public String toString() {
        return String.format("Database contains an uninterpretable entry at line %d.\n", this.lineNum);
    }
}
