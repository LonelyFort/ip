package Exceptions.PrintListException;

public class EmptyListException extends PrintListException {
    @Override
    public String toString() {
        return "No data has been stored in the database.";
    }
}
