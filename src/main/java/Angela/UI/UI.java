package Angela.UI;

public class UI {
    public static void displayResponse(String response) {
        System.out.println("________________________________________________________\n"
                + response + "\n"
                + "________________________________________________________");
    }

    public static void displayError(Exception e) {
        System.out.println("________________________________________________________\n" +
                "************************ ERROR! ************************\n" +
                e + "\n" +
                "************************ ERROR! ************************\n" +
                "________________________________________________________"
        );
    }

    //handles any errors outside of declared exceptions.
    public static void displayError(String e) {
        System.out.println("________________________________________________________\n" +
                "************************ ERROR! ************************\n" +
                e + "\n" +
                "************************ ERROR! ************************\n" +
                "________________________________________________________"
        );
    }
}
