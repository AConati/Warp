/**
 * Controller.java
 * Ari Conati & Grant Lee
 * 22 May 2018
 *
 * The class that manipulates the view to reflect the model.
 */

package opus;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.util.Timer;
import java.util.TimerTask;

import static javafx.scene.input.KeyEvent.KEY_PRESSED;

public class Controller implements EventHandler<KeyEvent> {
    final private double FRAMES_PER_SECOND = 20.0;

    @FXML private Button pauseButton;
    @FXML private Label scoreLabel;
    @FXML private AnchorPane gameBoard;
    @FXML private ChordStone stone;
    @FXML private Player player;
    @FXML private Shooter shooter;
    @FXML private Translocator glissando;
    @FXML private PlayerView playerView;
    private Model model;

    private int score;
    private boolean paused;
    private Timer timer;

    public Controller() {
        this.paused = false;
        this.score = 0;
    }

    public void initialize() {
        this.model = new Model();
        this.startTimer();
        this.update();
    }

    public double getFrameHeight() {
        return playerView.FRAME_HEIGHT;
    }

    public double getFrameWidth() {
        return playerView.FRAME_WIDTH;
    }

    /**
     * Survival game needs to keep track of time survived
     */
    private void startTimer() {
        this.timer = new java.util.Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        updateAnimation();
                    }
                });
            }
        };

        long frameTimeInMilliseconds = (long)(1000.0 / FRAMES_PER_SECOND);
        this.timer.schedule(timerTask, 0, frameTimeInMilliseconds);
    }

    /**
     * Code that is responsible for updating the position of objects
     */
    private void updateAnimation() {
        //ChordStone Movement

        if (model.getChordStone().getPosition().getX() + model.getChordStone().getWidth() >= playerView.FRAME_WIDTH && model.getChordStone().getVelocity().getX() > 0) {
            model.getChordStone().makeSound();
            model.getChordStone().setVelocity(-model.getChordStone().getVelocity().getX(), model.getChordStone().getVelocity().getY());
        }

        else if(model.getChordStone().getPosition().getX() < 0 && model.getChordStone().getVelocity().getX() < 0) {
            model.getChordStone().makeSound();
            model.getChordStone().setVelocity(-model.getChordStone().getVelocity().getX(), model.getChordStone().getVelocity().getY());
        }

        else if(model.getChordStone().getPosition().getY() + model.getChordStone().getHeight() >= playerView.FRAME_HEIGHT && model.getChordStone().getVelocity().getY() > 0) {
            model.getChordStone().makeSound();
            model.getChordStone().setVelocity(model.getChordStone().getVelocity().getX(), -model.getChordStone().getVelocity().getY());
        }

        else if(model.getChordStone().getPosition().getY() < 0 && model.getChordStone().getVelocity().getX() < 0) {
            model.getChordStone().makeSound();
            model.getChordStone().setVelocity(model.getChordStone().getVelocity().getX(), -model.getChordStone().getVelocity().getY());
        }

        //Player Movement

        if(model.getPlayer().getPosition().getX()  >= playerView.FRAME_WIDTH) {
            model.getPlayer().setPosition(-model.getPlayer().getWidth(),model.getPlayer().getPosition().getY());
        }

        model.getChordStone().step();
        model.getPlayer().step();
        model.getPlayer().getTranslocator().step();
        model.getPlayer().getTranslocator().decelerate(1);
        System.out.println(model.getPlayer().getTranslocator().getImageView().getX());
        System.out.println(model.getPlayer().getTranslocator().getImageView().getY());
        System.out.println(model.getPlayer().getTranslocator().getPosition());

    }

    @Override
    /**
     * Code required for controlling movement with keys
     *
     * @param keyEvent user input
     */
    public void handle(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        if(keyEvent.getEventType().equals(KEY_PRESSED)){
            handleKeyPressed(code);
        } else {
            handleKeyReleased(code);
        }
    }

    private void handleKeyPressed(KeyCode code) {
        double stepSize = 10;
        if(code == KeyCode.LEFT || code == KeyCode.A){
            double yVel = model.getPlayer().getVelocity().getY();
            model.getPlayer().setVelocity(-stepSize, yVel);
            model.getPlayer().getChildren().set(0,model.getPlayer().makeImage("res/topdownsmall.png", 0,150, 50,80,4,400));
        }
        else if(code == KeyCode.RIGHT || code == KeyCode.D) {
            double yVel = model.getPlayer().getVelocity().getY();
            model.getPlayer().setVelocity(stepSize, yVel);
            model.getPlayer().getChildren().set(0, model.getPlayer().makeImage("res/topdownsmall.png", 0,230, 50,80,4,400));
        }
        else if(code == KeyCode.UP || code == KeyCode.W) {
            double xVel = model.getPlayer().getVelocity().getX();
            model.getPlayer().setVelocity(xVel, -stepSize);
            model.getPlayer().getChildren().set(0, model.getPlayer().makeImage("res/topdownsmall.png", 0,80, 50,80,4,400));
        }
        else if(code == KeyCode.DOWN || code == KeyCode.S) {
            double xVel = model.getPlayer().getVelocity().getX();
            model.getPlayer().setVelocity(xVel, stepSize);
            model.getPlayer().getChildren().set(0, model.getPlayer().makeImage("res/topdownsmall.png", 0,0, 50,80,4,400));
        }
        else if(code == KeyCode.E) {
            if(model.getPlayer().getTranslocator().isThrown()) {
                model.getPlayer().teleport();
            } else {
                model.getPlayer().throwTranslocator(0, 10);
            }
        }
    }

    private void handleKeyReleased(KeyCode code) {
        if(code == KeyCode.LEFT || code == KeyCode.A){
            double yVel = model.getPlayer().getVelocity().getY();
            model.getPlayer().setVelocity(0, yVel);
        }
        else if(code == KeyCode.RIGHT || code == KeyCode.D) {
            double yVel = model.getPlayer().getVelocity().getY();
            model.getPlayer().setVelocity(0, yVel);
        }
        else if(code == KeyCode.UP || code == KeyCode.W) {
            double xVel = model.getPlayer().getVelocity().getX();
            model.getPlayer().setVelocity(xVel, 0);
        }
        else if(code == KeyCode.DOWN || code == KeyCode.S) {
            double xVel = model.getPlayer().getVelocity().getX();
            model.getPlayer().setVelocity(xVel, 0);
        }

    }

    /**
     * Code for pausing the game
     * @param actionEvent game event
     */
    public void onPauseButton(ActionEvent actionEvent) {
        if (this.paused) {
            this.pauseButton.setText("Pause");
            this.startTimer();
        } else {
            this.pauseButton.setText("Continue");
            this.timer.cancel();
        }
        this.paused = !this.paused;
    }

    private void update() {
        this.playerView.update(this.model);
    }
}
