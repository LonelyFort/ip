package Exceptions.TaskModificationException;

public class InvalidIndexException extends TaskModificationException {

    private int listSize;

    public InvalidIndexException(int listSize) {
        super();
        this.listSize = listSize;
    }

    @Override
    public String toString() {
        return "Invalid index. There are " + listSize + " task(s) in the database.";
    }
}
