package warp;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import static javafx.scene.input.KeyEvent.KEY_PRESSED;

    public class StartScreenController implements EventHandler<KeyEvent> {
        @FXML private Label title;
        @FXML private Label start;
        @FXML private Label help;
        @FXML private AnchorPane startScreenPane;
        @FXML StartScreenView startScreenView;

        private boolean keyPressed;

        public StartScreenController() {
            this.keyPressed = false;
        }

        public void initialize() {
            this.title.setText("Warp");
            this.title.setFont(Font.loadFont("file:src/res/olga.ttf", 200));
            this.start.setText("Start Game");
            this.start.setFont(Font.loadFont("file:src/res/arcadia.ttf", 20));
            this.help.setText("Help");
            this.help.setFont(Font.loadFont("file:src/res/arcadia.ttf", 20));
            startScreenView.initialize();
        }

        public void handle(KeyEvent keyEvent) {
            KeyCode code = keyEvent.getCode();
            if(keyEvent.getEventType().equals(KEY_PRESSED)){
                handleKeyPressed(code);
            }
        }

        private void handleKeyPressed(KeyCode code) {
            if(code == KeyCode.DOWN || code == KeyCode.S){
                startScreenView.selector.setPosition(250,347);
            } else if (code == KeyCode.UP || code == KeyCode.W) {
                startScreenView.selector.setPosition(250,294);
            }
            else if(code == KeyCode.ENTER) {
                if(startScreenView.selector.getPosition().getY() == 294) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameView.fxml"));
                        Parent newRootNode = loader.load();
                        Scene scene = this.startScreenPane.getScene();
                        scene.setRoot(newRootNode);
                        GameController gameController = loader.getController();
                        scene.setOnKeyPressed(gameController);
                        scene.setOnKeyReleased(gameController);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }

                }
                else if(startScreenView.selector.getPosition().getY() == 347) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("HelpScreenView.fxml"));
                        Parent newRootNode = loader.load();
                        Scene scene = this.startScreenPane.getScene();
                        scene.setRoot(newRootNode);
                        HelpScreenController helpController = loader.getController();
                        scene.setOnKeyPressed(helpController);
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
            }
        }

        public double getFrameHeight() {
            return this.startScreenView.FRAME_HEIGHT;
        }

        public double getFrameWidth() {
            return this.startScreenView.FRAME_WIDTH;
        }
    }

