import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lux.Lux;

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

    private Lux lux;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.jpg"));
    private Image luxImage = new Image(this.getClass().getResourceAsStream("/images/Lux.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setLux(Lux lux) {
        this.lux = lux;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        try {
            String input = userInput.getText();
            String response = lux.getResponse(input);
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getLuxDialog(response, luxImage)
            );
            userInput.clear();

            if (response.equals("Bye. Hope to see you again soon!")) {
                Thread.sleep(2000);
                Platform.exit();
            }
        } catch (InterruptedException e) {
            //ignore
        }
    }
}
