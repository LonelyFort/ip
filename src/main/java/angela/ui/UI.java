package angela.ui;

/**
 * UI class to handle display of responses in the console.
 */
public class UI {

    /**
     * Displays response in a simple 2 line UI.
     *
     * @param response The string to be displayed.
     */
    public static void displayResponse(String response) {
        System.out.println("________________________________________________________\n"
                + response + "\n"
                + "________________________________________________________");
    }

    /**
     * Displays an error message in a simple UI. A line of asterisks is displayed
     * to differentiate normal responses to error messages.
     *
     * @param e The exception whose message is to be displayed.
     */
    public static void displayError(Exception e) {
        System.out.println("________________________________________________________\n" +
                "************************ ERROR! ************************\n" +
                e + "\n" +
                "************************ ERROR! ************************\n" +
                "________________________________________________________"
        );
    }

    /**
     * Displays an error message in a simple UI. A line of asterisks is displayed
     * to differentiate normal responses to error messages. This is an overloaded
     * method to handle exceptions not declared in Angela (e.g IOException), in
     * which it will take in special message as a string instead of exception.
     *
     * @param e the exception message to be printed on the console.
     */
    public static void displayError(String e) {
        System.out.println("________________________________________________________\n" +
                "************************ ERROR! ************************\n" +
                e + "\n" +
                "************************ ERROR! ************************\n" +
                "________________________________________________________"
        );
    }
}
