package exceptions.taskcreation;

public class InvalidDateException extends TaskCreationException {

    @Override
    public String toString() {
        return "Invalid date. Please enter the date and time in syntax yyyy-mm-dd HH:mm";
    }
}
