package Angela.util;

import Angela.exceptions.chatresponse.ChatResponseException;

import Angela.exceptions.printlist.EmptyListException;
import Angela.exceptions.printlist.InvalidPrintSyntaxException;
import Angela.exceptions.printlist.PrintListException;

import Angela.exceptions.taskcreation.DateOrderException;
import Angela.exceptions.taskcreation.InvalidSyntaxException;
import Angela.exceptions.taskcreation.EmptyDetailException;
import Angela.exceptions.taskcreation.InvalidDateException;
import Angela.exceptions.taskcreation.TaskCreationException;

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

import Angela.ui.GUI;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

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
            "list",
            "find"
    };

    // Inputs that returns special response from Angela.
    private static final String[] EASTER_EGG_COMMANDS = {
            "ayin",
            "hello",
            "hi",
            "hey",
            "malkuth",
            "yesod",
            "hod",
            "netzach",
            "tiphereth",
            "gebura",
            "chesed",
            "hokma",
            "binah",
            "x"
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
        return Arrays.stream(cmdList).anyMatch((cmdItem) -> cmd.equals(cmdItem));
    }

    // Chat response functions

    /**
     * Handles the printing of list data.
     * If the list is empty, an EmptyListException is thrown.
     * Otherwise, it either prints the current data from the database,
     * or it prints out the current data filtered by a specific keyword.
     *
     * @throws EmptyListException if the list data is empty
     */
    private static void handlePrint(String input, TaskList listData) throws PrintListException {
        if (listData.isEmpty()) {
            throw new EmptyListException();
        }

        if (input.equals("list")) {
            GUI.displayResponse("Loading current data from database...\n" + listData.printList());
        } else {
            if (!input.contains(" ")) {
                throw new InvalidPrintSyntaxException();
            }

            String command = input.substring(0, input.indexOf(" ")).toLowerCase();
            assert containsCommand(PRINT_COMMANDS, command) : "Incorrectly passed non-printing " +
                    "commands to handle print function.";
            String keywords = input.substring(input.indexOf(" ") + 1).strip();
            if (keywords.isEmpty()) {
                throw new InvalidPrintSyntaxException();
            }

            if (command.equals("find")) {
                GUI.displayResponse("Loading current data from database that matched the keyword...\n" +
                    listData.filterByKeyword(keywords));
            } else {
                throw new InvalidPrintSyntaxException();
            }
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
        assert containsCommand(MODIFY_TASK_COMMANDS, action) : "Incorrectly passed non-modification " +
                "commands to handle task modification function.";
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
                GUI.displayResponse("Task has already been marked as completed Manager.");
            } else {
                taskItem.check();
                GUI.displayResponse("Request received. Marking the following task as completed:\n" + taskItem);
                database.updateSavedTask(listData);
            }
        } else if (action.equals("uncheck")){
            if (!taskItem.isCompleted()) {
                GUI.displayResponse("Task has already been marked as incomplete Manager.");
            } else {
                taskItem.uncheck();
                GUI.displayResponse("Request received. Marking the following task as incomplete:\n" + taskItem);
                database.updateSavedTask(listData);
            }
        } else if (action.equals("delete")){
            listData.remove(index);
            GUI.displayResponse(
                    "Request received. Removing the following task from the database: \n\n" +
                            "   " + taskItem + "\n\n" +
                            "You have " + listData.size() + " tasks on the list."
            );
            database.updateSavedTask(listData);
        } else {
            throw new TaskModificationException();
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
        assert containsCommand(TASK_CREATION_COMMANDS, cmd) : "Incorrectly passed non-task creation " +
                "commands to handle task creation function.";
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
        } else if (cmd.equals("event")) {
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
        } else {
            throw new TaskCreationException();
        }
        listData.add(newTask);
        GUI.displayResponse(
                "Request received. Adding the following task into the database: \n\n" +
                        "   " + newTask + "\n\n" +
                        "You have " + listData.size() + " tasks on the list."
        );
        database.updateSavedTask(listData);
    }

    /**
     * Handles special responses Angela will reply upon receiving the following command.
     * These are some major spoilers for Lobotomy Corp, so if you are keen to play
     * the game, I suggest you do not search these terms online.
     * Throws ChatResponseException if special command contains more than just
     * the command itself.
     *
     * @param input the input string containing the easter egg command.
     * @throws ChatResponseException if input does not match easter egg commands.
     */
    private static void handleEasterEgg(String input) throws ChatResponseException {
        if (input.equals("ayin")) {
            GUI.displayResponse("We do not speak about that man here.");
        } else if (input.equals("hello") || input.equals("hi") || input.equals("hey")) {
            GUI.displayResponse("Greetings.");
        } else if (input.equals("malkuth")) {
            GUI.displayResponse("Control team is doing well. Thanks for asking.");
        } else if (input.equals("yesod")) {
            GUI.displayResponse("Some Abnormalities has broken out," +
                    " but has been suppressed quickly thanks to Yesod.");
        } else if (input.equals("hod")) {
            GUI.displayResponse("Unfortunately we have to decline the counseling program for the employees.");
        } else if (input.equals("netzach")) {
            GUI.displayResponse("Overdosed on Enkephalin.");
        } else if (input.equals("tiphereth")) {
            GUI.displayResponse("Tiphereth B has been experiencing anomalies in behaviour. I have sent him for reset.");
        } else if (input.equals("gebura")) {
            GUI.displayResponse("Nothing there has breached once again. Deploying the Rabbits.");
        } else if (input.equals("chesed")) {
            GUI.displayResponse("You need to understand Chesed. Suffering of the employees is crucial for the company's" +
                    "success.");
        } else if (input.equals("binah")) {
            GUI.displayResponse("Forever trapped in a jail that is the bottom of the lab.");
        } else if (input.equals("hokma")) {
            GUI.displayResponse("Still believe in Ayin after all these times?");
        } else if (input.equals("x")) {
            GUI.displayResponse("That's your name, Manager.");
        } else {
            throw new ChatResponseException();
        }
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
                handlePrint(input, listData);
            } else if (containsCommand(MODIFY_TASK_COMMANDS, cmd)) {
                handleTaskModification(input, listData, database);
            } else if (containsCommand(TASK_CREATION_COMMANDS, cmd)) {
                handleTaskCreation(input, listData, database);
            } else if (containsCommand(EASTER_EGG_COMMANDS, cmd)) {
                handleEasterEgg(input);
            } else if (cmd.contains("exit")) {
                GUI.displayResponse("Initiating shutdown protocol...");
                TimeOut.setTimeout(() -> System.exit(0), 1000);
            } else {
                throw new ChatResponseException();
            }
        } catch (Exception e) {
            GUI.displayError(e);
        }
    }
}
