import java.util.Scanner;

public class Athena {

    private void echo() {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();

        if (input.toLowerCase().equals("bye")) {
            System.out.println("Thank you for using Athena. Initiating shutdown protocol...");
            System.exit(0);
        } else {
            System.out.println(input + "\n");
            echo();
        }
    }

    private void greet() {
        setTimeout(() -> System.out.println("Initiating..."), 1000);
        setTimeout(() -> System.out.println("Application starting..."), 4000);
        setTimeout(() -> System.out.println(
                """
                         _                 _            _       _    _            _             _         \s
                        / /\\              /\\ \\         / /\\    / /\\ /\\ \\         /\\ \\     _    / /\\       \s
                       / /  \\             \\_\\ \\       / / /   / / //  \\ \\       /  \\ \\   /\\_\\ / /  \\      \s
                      / / /\\ \\            /\\__ \\     / /_/   / / // /\\ \\ \\     / /\\ \\ \\_/ / // / /\\ \\     \s
                     / / /\\ \\ \\          / /_ \\ \\   / /\\ \\__/ / // / /\\ \\_\\   / / /\\ \\___/ // / /\\ \\ \\    \s
                    / / /  \\ \\ \\        / / /\\ \\ \\ / /\\ \\___\\/ // /_/_ \\/_/  / / /  \\/____// / /  \\ \\ \\   \s
                   / / /___/ /\\ \\      / / /  \\/_// / /\\/___/ // /____/\\    / / /    / / // / /___/ /\\ \\  \s
                  / / /_____/ /\\ \\    / / /      / / /   / / // /\\____\\/   / / /    / / // / /_____/ /\\ \\ \s
                 / /_________/\\ \\ \\  / / /      / / /   / / // / /______  / / /    / / // /_________/\\ \\ \\\s
                / / /_       __\\ \\_\\/_/ /      / / /   / / // / /_______\\/ / /    / / // / /_       __\\ \\_\\
                \\_\\___\\     /____/_/\\_\\/       \\/_/    \\/_/ \\/__________/\\/_/     \\/_/ \\_\\___\\     /____/_/
                        
                Good morning, afternoon or evening, this is Athena at your service.
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
        Athena session = new Athena();
        session.greet();
        session.echo();
    }
}
