package Angela.storage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import Angela.tasktype.Deadline;
import Angela.tasktype.Event;
import Angela.tasktype.ToDo;
import Angela.util.DateTimeValueHandler;

import Angela.exceptions.storage.UnreadableFileException;

//Database Class inspired by Clifong StorageManager class
public class Database {
    private String storagePath;

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
            while (input != null) {
                savedTaskParser(input, taskList);
                input = fileInput.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        } catch (UnreadableFileException e) {
            System.out.println(e);
        }
    }
    public void savedTaskParser(String line, TaskList taskList) throws UnreadableFileException {
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
            throw new UnreadableFileException();
        }
    }

    //Inspired by Clifong StorageManager updateSavedFile
    public void updateSavedTask(TaskList taskList)  {
        try {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(this.storagePath, false));
            fileWriter.write(taskList.saveAllTask());
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error occurred while updating database.");
        }
    }
}
