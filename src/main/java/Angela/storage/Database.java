package Angela.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import Angela.tasktype.Deadline;
import Angela.tasktype.Event;
import Angela.tasktype.ToDo;
import Angela.util.DateTimeValueHandler;
import Angela.ui.GUI;

import Angela.exceptions.storage.UnreadableFileException;

/**
 * Database class to save entries stored in taskList into a readable file. (e.g txt file)
 * Solution inspired by Clifong's StorageManager
 * https://github.com/Clifong/ip/blob/master/src/main/java/Acheron/Storage/StorageManager.java
 */
public class Database {
    // stores the save path of the file
    private String storagePath;

    /**
     * Constructs a new Database instance, initializes the storage path,
     * and loads tasks from the specified file.
     *
     * @param storagePath the path to the storage file
     * @param taskList the list of tasks to be populated with data from the storage file
     */
    public Database(String storagePath, TaskList taskList) {
        this.storagePath = storagePath;

        if (Files.notExists(Path.of(storagePath).getParent())) {
            new File(String.valueOf(Path.of(storagePath).getParent())).mkdir();
        }

        if (Files.notExists(Path.of(storagePath))) {
            try {
                Path file = Paths.get(storagePath);
                Files.createFile(file);
            } catch (IOException e) {
                System.out.println("An error has occurred while creating folder. Check system settings Manager.");
            }
        }

        try {
            BufferedReader fileInput = new BufferedReader(new FileReader(storagePath));
            String input = fileInput.readLine();
            int lineNum = 1;

            while (input != null) {
                savedTaskParser(input, taskList, lineNum);
                input = fileInput.readLine();
                lineNum++;
            }
        } catch (IOException e) {
            GUI.displayError("An error has occurred while reading from saved file. " +
                            "Database integrity may have been compromised.");
        } catch (UnreadableFileException e) {
            GUI.displayError(e);
        }
    }

    /**
     * Parses a line of text representing a task and adds the task to the provided TaskList.
     * The line is expected to be in a specific format, with fields separated by the '|' character.
     *
     * @param line the line of text representing the task
     * @param taskList the list to which the parsed task will be added
     * @param lineNum the line number of the task in the input file (used for error reporting)
     * @throws UnreadableFileException if the line format is not recognized
     */
    public void savedTaskParser(String line, TaskList taskList, int lineNum) throws UnreadableFileException {
        String[] split = line.split("\\|");
        boolean isCompleted = split[1].equals("X");
        String taskName = split[2];

        if (split[0].equals("T")) {
            taskList.add(new ToDo(taskName, isCompleted));
        } else if (split[0].equals("D")) {
            LocalDateTime end = DateTimeValueHandler.parseDateTime(split[3]);
            taskList.add(new Deadline(end, taskName, isCompleted));
        } else if (split[0].equals("E")) {
            LocalDateTime start = DateTimeValueHandler.parseDateTime(split[3]);
            LocalDateTime end = DateTimeValueHandler.parseDateTime(split[4]);
            taskList.add(new Event(start, end, taskName, isCompleted));
        } else {
            throw new UnreadableFileException(lineNum);
        }
    }

    /**
     * Updates the saved tasks in the storage file with the current list of tasks.
     *
     * @param taskList the list of tasks to be saved to the storage file
     */
    public void updateSavedTask(TaskList taskList) {
        try {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(this.storagePath, false));
            fileWriter.write(taskList.saveAllTask());
            fileWriter.close();
        } catch (IOException e) {
            GUI.displayError("Error occurred while updating database.");
        }
    }
}
