package Angela;

import Angela.util.Command;
import Angela.util.TimeOut;
import Angela.storage.Database;
import Angela.storage.TaskList;

import java.util.Scanner;

/**
 * Main class of Angela
 */
public class Angela {

    // private elements
    private Database database;
    private TaskList listData;
    private static String currentResponse;

    /**
     * Constructs an instance of the Angela class with the specified file path.
     * This constructor initializes the `listData` and `database` fields after a delay of 10 seconds.
     * The `listData` is initialized as a new `TaskList` object, and the `database` is initialized
     * as a new `Database` object using the provided file path and the `listData`.
     *
     * @param filePath The path to the file that will be used by the `Database` for storage or retrieval.
     *                 This path is passed to the `Database` constructor.
     * @see TaskList
     * @see Database
     */
    public Angela(String filePath) {
        TimeOut.setTimeout(() -> {
            this.listData = new TaskList();
            this.database = new Database(filePath, listData);
        }, 10000);
    }

    /**
     * Constructs an instance of the Angela class with the specified file path.
     * This constructor initializes the `listData` and `database` fields instantly for GUI Implementation.
     * The `listData` is initialized as a new `TaskList` object, and the `database` is initialized
     * as a new `Database` object using the provided file path and the `listData`.
     * In this constructor, the filePath is pre-determined.
     *
     * @see TaskList
     * @see Database
     */
    public Angela() {
        this.listData = new TaskList();
        this.database = new Database("savedFile/tasks.txt", listData);
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
            // ignores comments and empty lines
            if (input.isEmpty() || input.charAt(0) == '/') {
                continue;
            }
            Command.chatResponse(input, this.listData, this.database);
        }
    }

    /**
     * Prints onto the terminal when a user first initiated Angela.
     * Utilised setTimeout method to provide a more realistic experience.
     * ASCII art depicts Angela. from Lobotomy Corp.
     */
    private void greet() {
        TimeOut.setTimeout(() -> System.out.println("Initiating..."), 1000);
        TimeOut.setTimeout(() -> System.out.println("Application starting..."), 4000);
        TimeOut.setTimeout(() -> System.out.println(
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

    /**
     * Generates a greeting message for the GUI.
     *
     * @return a personalized welcome message for the manager.
     */
    public String greetGUI() {
        return "Welcome, Manager. I am your assistant AI, Angela. " +
                "I am here to support you as it's your first day.\n" +
                "I will provide practical advice and emotional support until you get used to your work. \n\n" +
                "What is your request?";
    }

    /**
     * Returns the response text of Angela to be displayed onto the GUI.
     *
     * @param input the command or query input to process.
     * @return the response generated based on the input.
     */
    public String getResponse(String input) {
        Command.chatResponse(input, this.listData, this.database);
        return currentResponse;
    }

    /**
     * Sets the response that Angela will reply onto the GUI.
     *
     * @param response the command or query input to process.
     */
    public static void setResponse(String response) {
        currentResponse = response;
    }


    /**
     * Main method for Angela. This is used for CLI implementation.
     *
     * @param args
     */
    public static void main(String[] args) {
        Angela session = new Angela("savedFile/tasks.txt");
        session.greet();
        TimeOut.setTimeout(() -> session.echo(), 10000);
    }
}
