package Exceptions.ChatResponseException;

public class ChatResponseException extends Exception {

    @Override
    public String toString() {
        return "Invalid command. Check the manual again Manager.";
    }
}
