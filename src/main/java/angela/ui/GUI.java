package angela.ui;

import angela.Angela;

/**
 * A class to handle response that is returned by Angela for GUI implementation.
 *
 */
public class GUI {

    /**
     * Stores response message inside Angela to be displayed onto the interface.
     *
     * @param response The string to be displayed.
     */
    public static void displayResponse(String response) {
        Angela.setResponse(response);
    }

    /**
     * Stores exception message inside Angela to be displayed onto the interface.
     *
     * @param e The exception whose message is to be displayed.
     */
    public static void displayError(Exception e) {
        Angela.setResponse(e.toString());
    }

    /**
     * Stores exception message inside Angela to be displayed onto the interface.
     * This is an overloaded method to handle exceptions not declared in Angela (e.g IOException),
     * in which it will take in special message as a string instead of exception.
     *
     * @param e the exception message to be printed on the console.
     */
    public static void displayError(String e) {
        Angela.setResponse(e);
    }
}
