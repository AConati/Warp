package warp;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Font;

import static javafx.scene.input.KeyEvent.KEY_PRESSED;


public class StartScreenView implements EventHandler<KeyEvent> {

    @FXML
    private Label title;
    @FXML
    private Label start;
    private boolean keyPressed;

    public StartScreenView() {
        this.keyPressed = false;

    }

    public void initialize() {
        this.title.setText("Warp");
        this.title.setFont(Font.loadFont("file:src/res/olga.ttf", 40));
        this.start.setText("Press Any Key to Start Game");
        this.title.setFont(Font.loadFont("file:src/res/arcadia.ttf", 20));
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getEventType().equals(KEY_PRESSED)) {
            this.keyPressed = true;
        }
    }
}
