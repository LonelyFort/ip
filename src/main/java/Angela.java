import TaskType.*;
import Exceptions.PrintListException.*;
import Exceptions.TaskModificationException.*;
import Exceptions.TaskCreationException.*;
import Exceptions.ChatResponseException.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Angela {

    // Commands and database
    private List<Task> listData = new ArrayList<>();
    private static final String[] TASKCREATIONCOMMANDS = {
            "todo",
            "deadline",
            "event"
    };
    private static final String[] MODIFYTASKCOMMANDS = {
            "check",
            "uncheck",
            "delete"
    };
    private static final String[] PRINTCOMMANDS = {
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
    private boolean containsCommand(String[] cmdList, String cmd) {
        for (String cmdItem : cmdList) {
            if (cmd.equals(cmdItem)) return true;
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
    private void handlePrint() throws PrintListException {
        if (listData.isEmpty()) {
            throw new EmptyListException();
        } else {
            System.out.println("Loading current data from database...\n");

            for (int i = 0; i < listData.size(); i++) {
                System.out.println((i + 1) + ". " + listData.get(i).toString());
            }
        }
    }

    /**
     * Modifies a task in listData based on the provided input.
     * The input must follow a specific syntax: "<action> <taskIndex>".
     * The method can perform actions recognised in MODIFYTASKCOMMANDS array.
     * If the list is empty, a ListEmptyException is thrown.
     * If the input syntax is incorrect, a WrongSyntaxException is thrown.
     * If the task index is invalid, an InvalidIndexException is thrown.
     *
     * @param input the input string containing the action and task index
     * @throws WrongSyntaxException if the input syntax is incorrect
     * @throws ListEmptyException if the list data is empty
     * @throws InvalidIndexException if the task index is invalid
     */
    private void handleTaskModification(String input) throws TaskModificationException {
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
                System.out.println("Task has already been marked as completed Manager.");
            } else {
                taskItem.check();
                System.out.println("Request received. Marking the following task as completed:\n" + taskItem);
            }
        } else if (action.equals("uncheck")){
            if (!taskItem.isCompleted()) {
                System.out.println("Task has already been marked as incomplete Manager.");
            } else {
                taskItem.uncheck();
                System.out.println("Request received. Marking the following task as incomplete:\n" + taskItem);
            }
        } else {
            listData.remove(index);
            System.out.println(
                    "Request received. Removing the following task from the database: \n\n" +
                            "   " + taskItem + "\n\n" +
                            "You have " + listData.size() + " tasks on the list."
            );
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
    private void handleTaskCreation(String input) throws TaskCreationException {
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
            String end = details.substring(details.indexOf("by:") + 3);
            newTask = new Deadline(end, taskDesc);
        } else {
            if (!details.contains("from:") || !details.contains("to:")) {
                throw new InvalidSyntaxException(cmd);
            }
            String taskDesc = details.substring(0, details.indexOf("from:"));
            String start = details.substring(details.indexOf("from:") + 5, details.indexOf("to:"));
            String end = details.substring(details.indexOf("to:") + 3);
            newTask = new Event(start, end, taskDesc);
        }
        listData.add(newTask);
        System.out.println(
                "Request received. Adding the following task into the database: \n\n" +
                        "   " + newTask + "\n\n" +
                        "You have " + listData.size() + " tasks on the list."
        );
    }

    /**
     * Handles the response to a chat command input.
     * It determines the command from the input and delegates the task to the appropriate handler.
     * Supported commands include print, modify task, task creation, and shutdown.
     * If the command is not recognized, a ChatResponseException is thrown.
     *
     * @param input the input string containing the chat command
     */
    private void chatResponse(String input) {
        String cmd = input.split(" ")[0].toLowerCase();

        try {
            if (containsCommand(PRINTCOMMANDS, cmd)) {
                handlePrint();
            } else if (containsCommand(MODIFYTASKCOMMANDS, cmd)) {
                handleTaskModification(input);
            } else if (containsCommand(TASKCREATIONCOMMANDS, cmd)) {
                handleTaskCreation(input);
            } else if (cmd.contains("bye")) {
                System.out.println("Initiating shutdown protocol...");
                System.exit(0);
            } else {
                throw new ChatResponseException();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Continuously reads input from the user and processes chat commands.
     * Comments (lines starting with '/') are ignored.
     * The input is stripped of leading and trailing whitespace before being processed.
     * The method runs indefinitely in a loop, handling chat commands via the chatResponse method.
     */
    private void echo() {
        Scanner scan = new Scanner(System.in);

        while (true) {
            String input = scan.nextLine().strip();
            // ignores comments
            if (input.charAt(0) == '/') continue;
            chatResponse(input);
        }
    }

    /**
     * Prints onto the terminal when a user first initiated Angela.
     * Utilised setTimeout method to provide a more realistic experience.
     * ASCII art depicts Angela from Lobotomy Corp.
     */
    private void greet() {
        setTimeout(() -> System.out.println("Initiating..."), 1000);
        setTimeout(() -> System.out.println("Application starting..."), 4000);
        setTimeout(() -> System.out.println(
                """
                                                  
                                                  @@@@@@@@@@                                           \s
                                             ##@@@*........@@@@@@@@@@##/                               \s
                                      ****@@@ .........................@@@*       ***#@**              \s
                                     @@@..@...............................@@@  @@@(.....%@@            \s
                                   @@.......................................@@@@...........@@          \s
                                @@@.........................................,##.....,.......%@         \s
                               @@.....................@,....................##@.......@.......@@       \s
                             /@.................................,@........../##.........,.....#@       \s
                            @@...........(#........,...........................@%..............@@      \s
                           @@,..........((%((@...@((#((%,...........#...........*@.............@@      \s
                           @...........&@(((@(&.*(@#@((&  @..........@..../......@@#...........@@      \s
                           @...........   .@# /.@          @..............,...../@@............@@      \s
                           @ .........@                     (...................@@@............@@      \s
                           @@.........                       .@......*..........@@@........... ,@      \s
                            @@...&....                  ,*/  * @....*..........*@@@..../.......#@@     \s
                             /@....@.. %((.#                    .. *.......,...@@@@.............@@     \s
                               @..../                     @@#/%(   ............@  @.............@@     \s
                                @@@..,@@@@@%.(         %           ......./....@@@@......,......(@     \s
                                   @&#@                           *.......*....%@@@......*.......@@#   \s
                                   @@....                         *............*@@@..............@@#   \s
                                    @....%                       @.......(......@@@..............@@#   \s
                                    @*.....@                  ..(.......@.......@@@..............@@#   \s
                                    @@......*..@          @  ,.@.......@........%@&..............&@#   \s
                                    @@.......#.........@ .    *.......&..........@/..............*@#   \s
                                    @@.......@.......@%##@   *.......%...........@*....&.........@@#   \s
                                    @@,........(%(@*###    @........#............*...,@.........@@     \s
                                    @@......@&&&@####(/&&&&.........*(,((........*..,@@.....,.,@@*     \s
                                    @@...%&&&&&%##@&&&&&&&.........    /*........@/@@@@.......@@#      \s
                                    @@@&&&&&&&@&&&&&&&@@&*........@   . ...........,@@@,....#@@@       \s
                                   @@&&&&&&&@&@&&&&&&&&&&...../...     %.............@@,...@@@         \s
                                   @@&&&&&&&&&&&&&&&&&&&......@..%    @...............@...@@           \s
                                   @@&&&&&&@&&&&&&&&&&&&,,..,.../   ,(................@.@@@            \s
                                 @@..&&&&&&@&&&&&&&&&&&&@...#      (*,................,..@@            \s
                               @@.,..,@&&&@&@&&&&&&&&&&&@.&         .......................@@          \s
                             /@.......%#&&&&&@&&&&&&&&&&&(         .....*....................@@@       \s
                           @@@......,.#  @&&&&&&&&&&&&&@          ,..............%.........,..,@@      \s
                          @@......,..*    &&&&@&&&&&&&@          %.......%........*.............@@     \s
                        @@@........,.&     &&@@&&&&&&,          &.................................@@@  \s
                        @@...........%      &&&&&&&&           #*........,*........................#@  \s
                        @@,..........%       &@&@&@           #...,...,...*,........,,.....,,,..,,,,.@ \s
                        @@........,..#       @&&&@           . #..........,.,..,,,,,,,...,,,,,...,.,,.@\s
                        @@......,#...@        &&@         *     #..........#,,,,,,,,,,,..,,,,,...,,,,.(@
                        @@......,....(        &&         #       ........,,....,,,,,,,,,,..,,,,,,,,,,,.@
                        @@&.....,.....    //, @         %         ..,,,,,....,,,,,,,...,,...,,,,,,,,,,,@
                          @....#..... (  (          #&  #@.        (,.,,,,,,%,,,,,,,,,,,,.,.,,,,,,,,,,.@
                          @@..*,....#/&&   %.&   *%&@&##@& /         .,,,,,,.,,,,,,,,,,,,,,@.,,,,,,,,,@@
                           @@.@%....  &&/   /      /%/@&&&@           &,,,,,,#,,,,,,,,,,,,,@@....,,..@@\s
                            @@@@..., /&&&@          @&&&&&&@            @,,,,@*,,,,,,,,,,,,@@@.,,,,,@@ \s
                               @@..@//@&&&&&   /   &&&&&&&&&,(            &,,@@,,,,,,,,,,,,@@@@,,,,@@  \s
                                @%.  //&&&&&&&&&&&&&&&&&&&&&& #             %@@,,,,,,,,,,,@@ @@..@@#   \s
                                @@&#////@&&&&&&&&&&&&&&&&&&&&& %              @,,,,,,,,,,@@   @@@@     \s
                                @@. ////@&&&&&&&&@&&&&&&&&&&&&& *               .,,,,,.@@**   ***      \s
                                @@(//////&&&&&&&&@&&&&&&&&&&&&&&                 ..,#@@#               \s
                                @% //////&&&&&&&&&&&&&&&&&&&&&&&&                  (@#                 \s
                               @@*////////&&&&&&&&@&&&&&&&&&&&&&&/                   *@@               \s
                               @%./////////&&&&&&@&&&&&&&&&&&&&&&//                    /@@@            \s
                               @*///////////&&&&&&&@&&&&&&&&&&&&&//(                     #@@           \s
                             /@.#///////////%&&&&&&@&&&&&&&&&&&&&///& *                    @@          \s
                             /@ /////////////@&&&&&&@&&&&&&&&&&&&////& (                    @@         \s
                            @@.@//////////////@&&&&&@&&&&&&&&&&&@/////@ #                   (@         \s
                            @@ ////////////////@&&&&&&&&&&&&&&&&@//////% %                   @@@       \s
                           @@(#/////////////////&&&&&@&&&&&&&&&&%///////& *                   @@@      \s
                           @  //////////////////(&&&&@&&&&&&&&&&@////////@ .                   @@      \s
                          @@(%///////////////////@&&&&&&&&&&&&&&@/////////@                     @@     \s
                          @  /////////////////////&&&&@&&&&&&&&&&//////////#                    @@     \s
                        @@@.(//////////////////////@&&&&&&&&&&&&&(///////////  .                 @@#   \s
                        @@  ////////////////////////@&&&&&&&&&&&&@///////////*( #                 @#   \s
                        @@( /////////////////////////&&@&&&&&&&&&&@@@@@@@@@@@%/@                  @@@  \s
                        @@ #////////////////////////@@&@&&&&&&&&&@@@           @@                  @@  \s
                        @@ /////////////////////@@@ @@@@&&&&&&&&&@              @@@ %              @@@ \s
                        @@ //////////////////@@       @@&&&&&&&&&@                @@.              ,@@ \s
                        @@,///////////////@@@         @@&&&&&&&&@@                  @@ .            @@ \s
                        @@%////////////@@             @@&&&&&&&&@                   @@@ *           &@@\s
                        @@@//////////@@@              @@&&&&&&&@@                    *@@             @@\s
                          @////////%@                 @@&&&&&&&@@                      @#           *@ \s
                          @@//////@@                  @@&&&&&&@@                        @.         @@@ \s
                          *@////@@@                   @@&&&&&&@@                        @@        @%*  \s
                           @@//@@                     @@&&&&&&@@                         @@ *   @@#/   \s
                           @@&@@                      @@&&&&&&@@                         @@@ #@@@      \s
                            @@@                       @@&&&&&&@@                           @@@@@       \s
                                                    @@&&&&&&&&@@                           @@          \s
                                                  @@&&&&&&&&&&@@                                       \s
                                                 @@@@#@&&&&&&&@@                                       \s
                                                 @####&&&&&&&&@@                                       \s
                                                  @@@@&&&&&&&&@@                                       \s
                                                    @@&&&&&&&&%@                                       \s
                                                    @##@&&&&@##@                                       \s
                                                    @@########@@                                       \s
                                                      @@####@@@                                        \s
                        
                        
                Welcome, Manager. I am your assistant AI, Angela. 
                I am here to support you as it's your first day. 
                I will provide practical advice and emotional support until you get used to your work.
                
                What is your request?                        
                """
        ), 7000);
    }

    //@@author Oleg Mikhailov-reused
    //Reused from https://stackoverflow.com/questions/26311470/what-is-the-equivalent-of-javascript-settimeout-in-java
    public static void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }
    //@@author

    /**
     * Main method for Angela.
     * @param args
     */
    public static void main(String[] args) {
        Angela session = new Angela();
        session.greet();
        setTimeout(() -> session.echo(), 10000);
    }
}
