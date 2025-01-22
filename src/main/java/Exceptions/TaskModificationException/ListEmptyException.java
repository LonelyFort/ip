package Exceptions.TaskModificationException;

public class ListEmptyException extends TaskModificationException {
    @Override
    public String toString() {
        return "No task has been created. Create one first Manager.";
    }
}
