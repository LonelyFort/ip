package Angela.exceptions.taskcreation;

public class DateOrderException extends TaskCreationException {

    @Override
    public String toString() {
        return "End date and time cannot be earlier than the start date and time. We are not in a time loop.";
    }
}
