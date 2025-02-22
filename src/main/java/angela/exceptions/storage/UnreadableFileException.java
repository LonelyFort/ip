package angela.exceptions.storage;

/**
 * Exception thrown when an unreadable entry is encountered in the database file.
 */
public class UnreadableFileException extends Exception {
    private int lineNum;

    /**
     * Constructs a new UnreadableFileException with the specified line number.
     *
     * @param lineNum the line number of the unreadable entry
     */
    public UnreadableFileException(int lineNum) {
        super();
        this.lineNum = lineNum;
    }

    /**
     * Returns a string representation of the exception, indicating the line number of the unreadable entry.
     *
     * @return a string representation of the exception
     */
    @Override
    public String toString() {
        return String.format(
                """
                CRITICAL ERROR: Database contains an uninterpretable entry at line %d.
                
                This may be caused by file corruption, a fault in the fileWriter or
                an unintended change in the tasks.txt file.
                
                In order to protect your saved task integrity, please check and fix
                any saved tasks that do not follow the format shown below:
                
                    TODO tasks: T| |TASK_NAME or T|X|TASK_NAME if completed.
                    
                    DEADLINE tasks: D| |TASK_NAME|YYYY-MM-DD HH:mm or
                    D| |TASK_NAME|YYYY-MM-DD HH:mm if completed.
                    
                    EVENT tasks: E| |TASK_NAME|YYYY-MM-DD HH:mm|YYYY-MM-DD HH:mm or
                    E|X|TASK_NAME|YYYY-MM-DD HH:mm|YYYY-MM-DD HH:mm if completed.
                
                If the task is barely intelligible to identify the task type,
                please delete the line entry.
                After fixing the issue, please save the txt file and rerun Angela.
                """, this.lineNum);
    }
}