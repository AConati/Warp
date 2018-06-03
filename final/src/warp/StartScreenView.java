package warp;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

import static javafx.scene.input.KeyEvent.KEY_PRESSED;


public class StartScreenView extends Group {

    public final double FRAME_HEIGHT = 600;
    public final double FRAME_WIDTH = 800;

    private boolean keyPressed;
    Selector selector = new Selector();

    public StartScreenView() {
        this.keyPressed = false;
    }

    public void initialize() {
        selector.setPosition(250,294);
        this.getChildren().add(selector);
    }


}
