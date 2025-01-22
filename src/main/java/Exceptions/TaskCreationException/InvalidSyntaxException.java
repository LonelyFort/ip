package Exceptions.TaskCreationException;

public class InvalidSyntaxException extends TaskCreationException {

    private String cmd;
    public InvalidSyntaxException(String cmd) {
        super();
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return String.format("Invalid syntax for %s command. Check the manual again Manager.", cmd);
    }
}
