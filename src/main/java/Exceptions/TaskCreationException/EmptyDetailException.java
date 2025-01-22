package Exceptions.TaskCreationException;

public class EmptyDetailException extends TaskCreationException {

    @Override
    public String toString() {
        return "Task description cannot be empty.";
    }
}
