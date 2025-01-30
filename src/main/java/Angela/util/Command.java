package Angela.util;

import Angela.exceptions.chatresponse.ChatResponseException;
import Angela.exceptions.printlist.EmptyListException;
import Angela.exceptions.printlist.PrintListException;
import Angela.exceptions.taskcreation.*;
import Angela.exceptions.taskmodification.InvalidIndexException;
import Angela.exceptions.taskmodification.ListEmptyException;
import Angela.exceptions.taskmodification.TaskModificationException;
import Angela.exceptions.taskmodification.WrongSyntaxException;
import Angela.storage.Database;
import Angela.storage.TaskList;
import Angela.tasktype.Deadline;
import Angela.tasktype.Event;
import Angela.tasktype.Task;
import Angela.tasktype.ToDo;

import Angela.UI.UI;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Command class to handle responses from console inputs.
 */
public class Command {
    // Commands
    private static final String[] TASK_CREATION_COMMANDS = {
            "todo",
            "deadline",
            "event"
    };
    private static final String[] MODIFY_TASK_COMMANDS = {
            "check",
            "uncheck",
            "delete"
    };
    private static final String[] PRINT_COMMANDS = {
            "list"
    };

    // List functions

    /**
     * Checks if a given command is present in the list of commands.
     *
     * @param cmdList an array of strings representing the list of commands
     * @param cmd the command to search for in the list
     * @return true if the command is found in the list, false otherwise
     */
    private static boolean containsCommand(String[] cmdList, String cmd) {
        for (String cmdItem : cmdList) {
            if (cmd.equals(cmdItem)) {
                return true;
            }
        }
        return false;
    }

    // Chat response functions

    /**
     * Handles the printing of list data.
     * If the list is empty, an EmptyListException is thrown.
     * Otherwise, it prints the current data from the database.
     *
     * @throws EmptyListException if the list data is empty
     */
    private static void handlePrint(TaskList listData) throws PrintListException {
        if (listData.isEmpty()) {
            throw new EmptyListException();
        } else {
            UI.displayResponse("Loading current data from database...\n" + listData.printList());
        }
    }


    /**
     * Modifies a task in listData based on the provided input.
     * The input must follow a specific syntax: "<action> <taskIndex>".
     * The method can perform actions recognised in MODIFYTASKCOMMANDS array.
     * If the list is empty, a ListEmptyException is thrown.
     * If the input syntax is incorrect, a WrongSyntaxException is thrown.
     * If the task index is invalid or out of bounds, an InvalidIndexException is thrown.
     *
     * @param input the input string containing the action and task index
     * @throws WrongSyntaxException if the input syntax is incorrect
     * @throws ListEmptyException if the list data is empty
     * @throws InvalidIndexException if the task index is invalid, or out of bounds
     */
    private static void handleTaskModification(String input, TaskList listData, Database database) throws TaskModificationException {
        if (!input.contains(" ")) {
            throw new WrongSyntaxException();
        }
        if (listData.isEmpty()) {
            throw new ListEmptyException();
        }
        String action = input.substring(0, input.indexOf(" "));
        String details = input.substring(input.indexOf(" ") + 1);
        // Regex will check if details contains only numbers
        if (!details.matches("^\\d+$")) {
            throw new WrongSyntaxException();
        }
        int index = Integer.parseInt(details) - 1;
        if (index < 0 || index >= listData.size()) {
            throw new InvalidIndexException(listData.size());
        }
        Task taskItem = listData.get(index);
        if (action.equals("check")) {
            if (taskItem.isCompleted()) {
                UI.displayResponse("Task has already been marked as completed Manager.");
            } else {
                taskItem.check();
                UI.displayResponse("Request received. Marking the following task as completed:\n" + taskItem);
                database.updateSavedTask(listData);
            }
        } else if (action.equals("uncheck")){
            if (!taskItem.isCompleted()) {
                UI.displayResponse("Task has already been marked as incomplete Manager.");
            } else {
                taskItem.uncheck();
                UI.displayResponse("Request received. Marking the following task as incomplete:\n" + taskItem);
                database.updateSavedTask(listData);
            }
        } else {
            listData.remove(index);
            UI.displayResponse(
                    "Request received. Removing the following task from the database: \n\n" +
                            "   " + taskItem + "\n\n" +
                            "You have " + listData.size() + " tasks on the list."
            );
            database.updateSavedTask(listData);
        }
    }

    /**
     * Handles the creation of a new task based on the provided input.
     * The input must follow a specific syntax:
     * - "todo <taskDetails>" for ToDo tasks
     * - "deadline <taskDetails> by:<endDateTime>" for Deadline tasks
     * - "event <taskDetails> from:<startDateTime> to:<endDateTime>" for Event tasks
     * If the input syntax is incorrect, an InvalidSyntaxException is thrown.
     * If the input details are empty, an EmptyDetailException is thrown.
     *
     * @param input the input string containing the command and task details
     * @throws EmptyDetailException if the input details are empty
     * @throws InvalidSyntaxException if the input syntax is incorrect
     */
    private static void handleTaskCreation(String input, TaskList listData, Database database) throws TaskCreationException {
        if (!input.contains(" ")) {
            throw new EmptyDetailException();
        }
        String cmd = input.substring(0, input.indexOf(" ")).toLowerCase();
        String details = input.substring(input.indexOf(" ") + 1).strip();
        Task newTask;
        if (cmd.equals("todo")) {
            newTask = new ToDo(details);
        } else if (cmd.equals("deadline")) {
            if (!details.contains("by:")) {
                throw new InvalidSyntaxException(cmd);
            }
            String taskDesc = details.substring(0, details.indexOf("by:"));
            String end = details.substring(details.indexOf("by:") + 3).strip();
            LocalDateTime endDateTime;
            try {
                endDateTime = DateTimeValueHandler.parseDateTime(end);
            } catch (DateTimeParseException e) {
                throw new InvalidDateException();
            }
            newTask = new Deadline(endDateTime, taskDesc);
        } else {
            if (!details.contains("from:") || !details.contains("to:")) {
                throw new InvalidSyntaxException(cmd);
            }
            String taskDesc = details.substring(0, details.indexOf("from:"));
            String start = details.substring(details.indexOf("from:") + 5, details.indexOf("to:")).strip();
            String end = details.substring(details.indexOf("to:") + 3).strip();
            LocalDateTime startDateTime;
            LocalDateTime endDateTime;
            try {
                startDateTime = DateTimeValueHandler.parseDateTime(start);
                endDateTime = DateTimeValueHandler.parseDateTime(end);
            } catch (DateTimeParseException e) {
                throw new InvalidDateException();
            }
            if (endDateTime.isBefore(startDateTime)) {
                throw new DateOrderException();
            }
            newTask = new Event(startDateTime, endDateTime, taskDesc);
        }
        listData.add(newTask);
        UI.displayResponse(
                "Request received. Adding the following task into the database: \n\n" +
                        "   " + newTask + "\n\n" +
                        "You have " + listData.size() + " tasks on the list."
        );
        database.updateSavedTask(listData);
    }

    /**
     * Handles the response to a chat command input.
     * It determines the command from the input and delegates the task to the appropriate handler.
     * Supported commands include print, modify task, task creation, and shutdown.
     * If the command is not recognized, a ChatResponseException is thrown.
     *
     * @param input the input string containing the chat command
     */
    public static void chatResponse(String input, TaskList listData, Database database) {
        String cmd = input.split(" ")[0].toLowerCase();

        try {
            if (containsCommand(PRINT_COMMANDS, cmd)) {
                handlePrint(listData);
            } else if (containsCommand(MODIFY_TASK_COMMANDS, cmd)) {
                handleTaskModification(input, listData, database);
            } else if (containsCommand(TASK_CREATION_COMMANDS, cmd)) {
                handleTaskCreation(input, listData, database);
            } else if (cmd.contains("bye")) {
                UI.displayResponse("Initiating shutdown protocol...");
                System.exit(0);
            } else {
                throw new ChatResponseException();
            }
        } catch (Exception e) {
            UI.displayError(e);
        }
    }
}
