package angela.exceptions.taskcreation;

/**
 * Represents an exception that is thrown when a task creation command
 * has an invalid syntax.
 */
public class InvalidSyntaxException extends TaskCreationException {

    // The command with the invalid syntax.
    private String cmd;

    /**
     * Constructs an InvalidSyntaxException with the specified command.
     *
     * @param cmd The command with the invalid syntax.
     */
    public InvalidSyntaxException(String cmd) {
        super();
        this.cmd = cmd;
    }

    /**
     * Returns a string representation of the exception,
     * indicating the invalid syntax for the specific command.
     *
     * @return A string representation of the exception.
     */
    @Override
    public String toString() {
        return String.format("Invalid syntax for %s command. " +
                "Check the manual again Manager.", cmd);
    }
}
