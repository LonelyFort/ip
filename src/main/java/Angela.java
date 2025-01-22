import TaskType.*;

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
            "uncheck"
    };
    private static final String[] PRINTCOMMANDS = {
            "list"
    };

    // List functions

    private boolean containsCommand(String[] cmdList, String cmd) {
        for (String cmdItem : cmdList) {
            if (cmd.equals(cmdItem)) return true;
        }
        return false;
    }

    // Chat response functions
    private void handlePrint() {
        if (listData.isEmpty()) {
            System.out.println("No data has been stored in the database.");
        } else {
            System.out.println("Loading current data from database...\n");

            for (int i = 0; i < listData.size(); i++) {
                System.out.println((i + 1) + ". " + listData.get(i).toString());
            }
        }
    }

    private void handleTaskModification(String input) {
        if (!input.contains(" ")) {
            System.out.println("Enter the rank of the list item you want to modify. Negative number is not accepted.");
            return;
        }
        if (listData.isEmpty()) {
            System.out.println("No task has been created. Create one first Manager.");
            return;
        }
        String action = input.substring(0, input.indexOf(" "));
        String details = input.substring(input.indexOf(" ") + 1);
        // Regex will check if details contains only numbers
        if (!details.matches("^\\d+$")) {
            System.out.println("Enter the rank of the list item you want to modify. Negative number is not accepted.");
            return;
        }
        int index = Integer.parseInt(details) - 1;
        if (index < 0 || index >= listData.size()) {
            System.out.println("Invalid index. There are " + listData.size() + " task(s) in the database.");
            return;
        }
        Task taskItem = listData.get(index);
        if (action.equals("check")) {
            if (taskItem.isCompleted()) {
                System.out.println("Task has already been marked as completed Manager.");
            } else {
                taskItem.check();
                System.out.println("Request received. Marking the following task as completed:\n" + taskItem);
            }
        } else {
            if (!taskItem.isCompleted()) {
                System.out.println("Task has already been marked as incomplete Manager.");
            } else {
                taskItem.uncheck();
                System.out.println("Request received. Marking the following task as incomplete:\n" + taskItem);
            }
        }
    }

    // Syntax todo: todo action deadline: deadline action by:Time event: event action from:Time to:Time

    private void handleTaskCreation(String input) {
        if (!input.contains(" ")) {
            System.out.println("Task description cannot be empty.");
            return;
        }
        String cmd = input.substring(0, input.indexOf(" ")).toLowerCase();
        String details = input.substring(input.indexOf(" ") + 1).strip();
        Task newTask;
        if (cmd.equals("todo")) {
            newTask = new ToDo(details);
        } else if (cmd.equals("deadline")) {
            if (!details.contains("by:")) {
                System.out.println("Invalid syntax for deadline command. Check the manual again Manager.");
                return;
            }
            String taskDesc = details.substring(0, details.indexOf("by:"));
            String end = details.substring(details.indexOf("by:") + 3);
            newTask = new Deadline(end, taskDesc);
        } else {
            if (!details.contains("from:") || !details.contains("to:")) {
                System.out.println("Invalid syntax for event command. Check the manual again Manager.");
                return;
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

    private void chatResponse(String input) {
        String cmd = input.split(" ")[0].toLowerCase();

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
            System.out.println("Invalid command. Check the manual again Manager.");
        }
    }

    private void echo() {
        Scanner scan = new Scanner(System.in);

        while (true) {
            String input = scan.nextLine().strip();

            // ignores comments and empty lines
            if (input.charAt(0) == '/') continue;

            chatResponse(input);
        }
    }

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

    public static void main(String[] args) {
        Angela session = new Angela();
        session.greet();
        setTimeout(() -> session.echo(), 10000);
    }
}
