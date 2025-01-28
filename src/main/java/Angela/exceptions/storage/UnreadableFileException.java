package Angela.exceptions.storage;

public class UnreadableFileException extends Exception{
    @Override
    public String toString() {
        return "Database contains unreadable entry. Database integrity may have been compromised Manager.";
    }
}
