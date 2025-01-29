package Angela.exceptions.storage;

public class UnreadableFileException extends Exception {
    private int lineNum;

    public UnreadableFileException(int lineNum) {
        super();
        this.lineNum = lineNum;
    }
    @Override
    public String toString() {
        return String.format("Database contains an uninterpretable entry at line %d.\n", this.lineNum);
    }
}
