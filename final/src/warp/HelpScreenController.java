package warp;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

public class HelpScreenController implements EventHandler<KeyEvent> {
    @FXML private BorderPane helpScreenPane;
    @FXML private Label helpText;
    @FXML private Label helpLabel;

    public HelpScreenController(){}

    public void initialize() {
        this.helpLabel.setFont(Font.loadFont("file:src/res/arcadia.ttf",25));
        this.helpLabel.setText("\n\t\t\tHelp");
        this.helpText.setFont(Font.loadFont("file:src/res/arcadia.ttf",15));
        //this.helpText.setStyle("-fx-text-fill: white");

        helpText.setText("  Move your character with the \n  arrow keys or with WASD.\n\n" +
        "  Press E to throw your translocator \n  and E again to teleport to it.\n\n" +
        "  Press R to recall your translocator \n  after it has been thrown.\n\n" +
        "  Avoid the projectiles that are shot\n  by the turrets that spawn.\n\n" +
        "  Red turrets shoot infrequently,\n  but target your character.\n\n" +
        "  Yellow turrets shoot frequently,\n  but can only shoot in one direction.\n\n" +
        "  Collect as many magic rocks \n  as you can as you survive!\n\n" +
        "  (ESC to exit)");
    }

    public void handle(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        if(code == KeyCode.DOWN || code == KeyCode.S) {
            System.out.println("HELLO");
        }
        if(code == KeyCode.ESCAPE) {
            try {
                Parent newRootNode = FXMLLoader.load(getClass().getResource("StartScreenView.fxml"));
                Scene scene = this.helpScreenPane.getScene();
                scene.setRoot(newRootNode);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
