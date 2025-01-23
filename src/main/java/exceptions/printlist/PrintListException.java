package exceptions.printlist;

public class PrintListException extends Exception {

    @Override
    public String toString() {
        return "Error printing list items.";
    }
}
