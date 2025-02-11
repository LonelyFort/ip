package Angela.app;

import java.io.IOException;

import Angela.Angela;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main component for the GUI.
 */
public class App extends Application {

    private Angela session = new Angela();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
<<<<<<< HEAD
            fxmlLoader.<MainWindow>getController().setSession(session); // inject the Angela instance
=======
            stage.setTitle("Angela");
            Image icon = new Image(getClass().getResourceAsStream("/images/lobcorp_logo.png"));
            stage.getIcons().add(icon);
            fxmlLoader.<MainWindow>getController().setSession(session);  // inject the Angela instance
>>>>>>> master
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
