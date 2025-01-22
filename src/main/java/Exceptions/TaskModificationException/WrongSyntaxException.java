package Exceptions.TaskModificationException;

public class WrongSyntaxException extends TaskModificationException {

    @Override
    public String toString() {
        return "Enter the rank of the list item you want to modify. Negative number is not accepted.";
    }
}
