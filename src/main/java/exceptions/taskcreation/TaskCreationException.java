package exceptions.taskcreation;

public class TaskCreationException extends Exception {

    @Override
    public String toString() {
        return "Error creating new tasks.";
    }
}
