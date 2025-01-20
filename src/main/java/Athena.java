public class Athena {

    private void greet() {
        setTimeout(() -> System.out.println("Initiating..."), 1000);
        setTimeout(() -> System.out.println("Application starting..."), 4000);
        setTimeout(() -> System.out.println(
                """
                Good morning, afternoon or evening, this is Athena at your service.
                What is your request?                        
                """
        ), 7000);
        setTimeout(() -> System.out.println(
                "Thank you for using Athena. Initiating shutdown protocol..."
        ), 10000);
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
    }
}
