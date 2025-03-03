package angela.app;

import angela.Angela;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Angela angela;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/managerA.png"));
    private Image angelaImage = new Image(this.getClass().getResourceAsStream("/images/angela.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Angela instance */
    public void setSession(Angela angela) {
        this.angela = angela;
        String greet = this.angela.greetGui();
        dialogContainer.getChildren().add(DialogBox.getAngelaDialog(greet, angelaImage));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Angela's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = angela.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getAngelaDialog(response, angelaImage)
        );
        userInput.clear();
    }
}
