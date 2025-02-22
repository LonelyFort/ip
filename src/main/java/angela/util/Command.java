package angela.util;

import angela.exceptions.chatresponse.ChatResponseException;

import angela.exceptions.printlist.EmptyListException;
import angela.exceptions.printlist.InvalidPrintSyntaxException;
import angela.exceptions.printlist.PrintListException;

import angela.exceptions.taskcreation.DateOrderException;
import angela.exceptions.taskcreation.InvalidSyntaxException;
import angela.exceptions.taskcreation.EmptyDetailException;
import angela.exceptions.taskcreation.InvalidDateException;
import angela.exceptions.taskcreation.TaskCreationException;

import angela.exceptions.taskmodification.InvalidIndexException;
import angela.exceptions.taskmodification.ListEmptyException;
import angela.exceptions.taskmodification.TaskModificationException;
import angela.exceptions.taskmodification.WrongSyntaxException;

import angela.storage.Database;
import angela.storage.TaskList;

import angela.tasktype.Deadline;
import angela.tasktype.Event;
import angela.tasktype.Task;
import angela.tasktype.ToDo;

import angela.ui.GUI;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

/**
 * Command class to handle responses from console inputs.
 */
public class Command {
    // Commands
    private static final String[] TASK_CREATION_COMMANDS = {
            // TODO
            "todo",
            "t",
            "todoi",
            "ti",

            // Deadline
            "deadline",
            "d",
            "deadlinei",
            "di",

            // Event
            "event",
            "e",
            "eventi",
            "ei"
    };
    private static final String[] MODIFY_TASK_COMMANDS = {
            "check",
            "uncheck",
            "delete",
            "marki",
            "unmarki",
            "c", //check
            "uc", //uncheck
            "del", //delete
            "mi", //mark impt
            "ui" //unmark impt
    };
    private static final String[] PRINT_COMMANDS = {
            "list",
            "find",
            "findi",
            "l", //list
            "f", //find
            "fi" //findi
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
     * prints out the current data filtered by a specific keyword,
     * or it prints out list filtered by importance.
     *
     * @throws EmptyListException if the list data is empty
     */
    private static void handlePrint(String input, TaskList listData) throws PrintListException {
        if (listData.isEmpty()) {
            throw new EmptyListException();
        }

        String lowerCaseInput = input.toLowerCase();

        //case statements
        boolean isPrintListInput = lowerCaseInput.equals("list") || lowerCaseInput.equals("l");
        boolean isFindImptInput = lowerCaseInput.equals("findi") || lowerCaseInput.equals("fi");

        if (isPrintListInput) {
            GUI.displayResponse("Loading current data from database...\n" + listData.printList());
        } else if (isFindImptInput) {
            GUI.displayResponse("Loading current data from database...\n" + listData.printFilterByImportanceList());
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

            //case statements
            boolean isFindCmd = command.equals("find") || command.equals("f");
            boolean isFindImptCmd = command.equals("findi") || command.equals("fi");

            if (isFindCmd) {
                GUI.displayResponse("Loading current data from database that matched the keyword...\n" +
                    listData.printFilteredByKeywordList(keywords));
            } else if (isFindImptCmd) {
                GUI.displayResponse("Loading current data from database that matched the keyword...\n" +
                    listData.printFilteredImptList(keywords));
            } else {
                throw new PrintListException();
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

        String command = input.substring(0, input.indexOf(" ")).toLowerCase();
        assert containsCommand(MODIFY_TASK_COMMANDS, command) : "Incorrectly passed non-modification " +
                "commands to handle task modification function.";

        String details = input.substring(input.indexOf(" ") + 1).strip();
        // Regex will check if details contains only numbers
        if (!details.matches("^\\d+$")) {
            throw new WrongSyntaxException();
        }

        int index = Integer.parseInt(details) - 1;
        if (index < 0 || index >= listData.size()) {
            throw new InvalidIndexException(listData.size());
        }

        Task taskItem = listData.get(index);

        // case statements
        boolean isCheckCmd = command.equals("check") || command.equals("c");
        boolean isUncheckedCmd = command.equals("uncheck") || command.equals("uc");
        boolean isDeleteCmd = command.equals("delete") || command.equals("del");
        boolean isMarkImptCmd = command.equals("marki") || command.equals("mi");
        boolean isUnmarkImptCmd = command.equals("unmarki") || command.equals("ui");

        if (isCheckCmd) {
            if (taskItem.isCompleted()) {
                GUI.displayResponse("Task has already been marked as completed Manager.");
                return;
            }
            taskItem.check();
            GUI.displayResponse("Request received. Marking the following task as completed:\n" + taskItem);
            database.updateSavedTask(listData);
        } else if (isUncheckedCmd) {
            if (!taskItem.isCompleted()) {
                GUI.displayResponse("Task has already been marked as incomplete Manager.");
                return;
            }
            taskItem.uncheck();
            GUI.displayResponse("Request received. Marking the following task as incomplete:\n" + taskItem);
            database.updateSavedTask(listData);
        } else if (isDeleteCmd) {
            listData.remove(index);
            GUI.displayResponse(
                    "Request received. Removing the following task from the database: \n\n" +
                            "   " + taskItem + "\n\n" +
                            "You have " + listData.size() + " tasks on the list."
            );
            
            database.updateSavedTask(listData);
        } else if (isMarkImptCmd) {
            if (taskItem.isImportant()) {
                GUI.displayResponse("Task has already been marked as important Manager.");
                return;
            }
            taskItem.markImportant();
            GUI.displayResponse("Request received. Marking the following task as important:\n" + taskItem);
            database.updateSavedTask(listData);
        } else if (isUnmarkImptCmd) {
            if (!taskItem.isImportant()) {
                GUI.displayResponse("Task is not marked as important Manager.");
                return;
            }
            taskItem.unmarkImportant();
            GUI.displayResponse("Request received. Removing the priority on the following task:\n" + taskItem);
            database.updateSavedTask(listData);
        } else {
            throw new TaskModificationException();
        }
    }

    /**
     * Handles the creation of a new task based on the provided input.
     * The input must follow a specific syntax:
     * - "todo <taskDetails> or t <taskDetails>" for ToDo tasks
     * - "deadline <taskDetails> by:<endDateTime>
     *     or d <taskDetails> by:<endDateTime>" for Deadline tasks
     * - "event <taskDetails> from:<startDateTime> to:<endDateTime>
     *     or e <taskDetails> from:<startDateTime> to:<endDateTime>" for Event tasks
     * One can indicate "i" after command to mark task as important.
     * (e.g todoi/ti to mark todo as important)
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
        boolean isImportant = cmd.endsWith("i");
        String details = input.substring(input.indexOf(" ") + 1).strip();
        if (details.isEmpty()) {
            throw new EmptyDetailException();
        }
        Task newTask;
        String taskType;

        // case statements
        boolean isTodoTask = cmd.equals("todo") || cmd.equals("todoi")
                || cmd.equals("t") || cmd.equals("ti");
        boolean isDeadlineTask = cmd.equals("deadline") || cmd.equals("deadlinei")
                || cmd.equals("d") || cmd.equals("di");
        boolean isEventTask = cmd.equals("event") || cmd.equals("eventi")
                || cmd.equals("e") || cmd.equals("ei");

        if (isTodoTask) {
            newTask = new ToDo(details, isImportant);
            taskType = "todo";
        } else if (isDeadlineTask) {
            if (!details.contains("by:")) {
                throw new InvalidSyntaxException(cmd);
            }

            String taskDesc = details.substring(0, details.indexOf("by:"));
            if (taskDesc.isEmpty()) {
                throw new EmptyDetailException();
            }
            String end = details.substring(details.indexOf("by:") + 3).strip();
            LocalDateTime endDateTime;

            try {
                endDateTime = DateTimeValueHandler.parseDateTime(end);
            } catch (DateTimeParseException e) {
                throw new InvalidDateException();
            }

            newTask = new Deadline(endDateTime, taskDesc, isImportant);
            taskType = "deadline";
        } else if (isEventTask) {
            if (!details.contains("from:") || !details.contains("to:")) {
                throw new InvalidSyntaxException(cmd);
            }

            String taskDesc = details.substring(0, details.indexOf("from:"));
            if (taskDesc.isEmpty()) {
                throw new EmptyDetailException();
            }
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

            newTask = new Event(startDateTime, endDateTime, taskDesc, isImportant);
            taskType = "event";
        } else {
            throw new TaskCreationException();
        }
        
        listData.add(newTask);
        GUI.displayResponse(
                "Request received. Adding the following " + taskType + " into the database: \n\n" +
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
            GUI.displayResponse("You need to understand Chesed. Suffering of the employees is " +
                    "crucial for the company's success.");
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
        String strippedInput = input.strip();
        String cmd = strippedInput.split(" ")[0].toLowerCase();

        try {
            if (containsCommand(PRINT_COMMANDS, cmd)) {
                handlePrint(strippedInput, listData);
            } else if (containsCommand(MODIFY_TASK_COMMANDS, cmd)) {
                handleTaskModification(strippedInput, listData, database);
            } else if (containsCommand(TASK_CREATION_COMMANDS, cmd)) {
                handleTaskCreation(strippedInput, listData, database);
            } else if (containsCommand(EASTER_EGG_COMMANDS, cmd)) {
                handleEasterEgg(strippedInput);
            } else if (cmd.equals("manual")) {
                GUI.displayResponse("You may find the full manual at: " +
                        "https://lonelyfort.github.io/ip/ .");
            } else if (cmd.equals("exit")) {
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
