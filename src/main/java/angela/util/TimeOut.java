package angela.util;

/**
 * Utility class to mimic setTimeout function on JavaScript.
 */
public class TimeOut {
    //@@author Oleg Mikhailov-reused
    //Reused from https://stackoverflow.com/questions/26311470/what-is-the-equivalent-of-javascript-settimeout-in-java
    public static void setTimeout(Runnable runnable, int delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            } catch (Exception e) {
                System.err.println(e);
            }
        }).start();
    }
    //@@author
}
