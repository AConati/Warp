package warp;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;


public class GameOverScreenController implements EventHandler<KeyEvent> {

    @FXML private BorderPane gameOverScreenPane;
    @FXML private Label gameOverLabel;
    @FXML private Label gameOverText;

    private int score = 0;

    public GameOverScreenController(){
    }

    public void initialize() {
        this.gameOverLabel.setFont(Font.loadFont("file:src/res/arcadia.ttf",150));
        this.gameOverLabel.setText("GAME\nOVER");
        this.gameOverText.setFont(Font.loadFont("file:src/res/arcadia.ttf",20));

    }



    public void handle(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        if(code == KeyCode.ENTER) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("StartScreenView.fxml"));
                Parent newRootNode = loader.load();
                Scene scene = this.gameOverScreenPane.getScene();
                scene.setRoot(newRootNode);
                StartScreenController startScreenController = loader.getController();
                scene.setOnKeyPressed(startScreenController);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void setScore(int score) {
        this.score = score;
        this.gameOverText.setText( String.format("Your score: %d", this.score) +
                "\nPress ENTER to return\n to the main menu.");
    }


}
