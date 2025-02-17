package angela.exceptions.chatresponse;

/**
 * Represents an exception that is thrown when an invalid command
 * is issued in the chat response process.
 */
public class ChatResponseException extends Exception {

    /**
     * Returns a string representation of the exception,
     * indicating that an invalid command was issued and suggesting
     * to check the manual again.
     *
     * @return A string representation of the exception.
     */
    @Override
    public String toString() {
        return "Invalid command. Check the manual again Manager.";
    }
}
