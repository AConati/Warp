/**
 * GameController.java
 * Ari Conati & Grant Lee
 * 22 May 2018
 *
 * The class that manipulates the view to reflect the model.
 */

package warp;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import static javafx.scene.input.KeyEvent.KEY_PRESSED;

public class GameController implements EventHandler<KeyEvent> {
    final private double FRAMES_PER_SECOND = 20.0;
    public final int INCREASE_DIFFICULTY_TIMER = 1000;

    @FXML private Label healthLabel;
    @FXML private Label scoreLabel;
    @FXML private Label pauseLabel;
    @FXML private GameView gameView;
    private Model model;

    private AudioClip audioclip;

    private boolean paused;
    private Timer timer;
    private int difficultyIncreaseCounter;

    public GameController() {
        this.paused = false;
    }

    public void initialize() {
        this.model = new Model(this.gameView.FRAME_WIDTH,this.gameView.FRAME_HEIGHT);
        this.startTimer();
        this.update();
        this.difficultyIncreaseCounter = INCREASE_DIFFICULTY_TIMER;
        this.healthLabel.setFont(Font.loadFont("file:src/res/arcadia.ttf",20));
        this.pauseLabel.setFont(Font.loadFont("file:src/res/arcadia.ttf",20));
        this.scoreLabel.setFont(Font.loadFont("file:src/res/arcadia.ttf",20));
        this.model.loadHighScores("src/warp/highScores.txt");
        System.out.println(this.model.getHighScores());
        //this.model.writeHighScore("src/warp/highScores.txt", 5);
    }

    public double getFrameWidth() {
        return this.gameView.FRAME_WIDTH;
    }

    public double getFrameHeight() {
        return this.gameView.FRAME_HEIGHT;
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
                        checkBoundaries();
                        checkCollision();
                        updateAnimation();
                        scoreLabel.setText(String.format("Score: %d", model.getScore()));
                        healthLabel.setText(String.format("Life: %d", model.getPlayer().getLifeTotal()));

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

        if(model.isGameOver())  {
            this.gameView.getChildren().remove(model.getPlayer());
            this.gameView.getChildren().remove(model.getPlayer().getTranslocator());
        }

        if(difficultyIncreaseCounter == 0){
            this.difficultyIncreaseCounter = INCREASE_DIFFICULTY_TIMER;
            Shooter shooter = model.increaseDifficulty(this.gameView.FRAME_WIDTH, this.gameView.FRAME_HEIGHT);
            this.gameView.getChildren().add(shooter);
        }

        model.getChordStone().step();
        model.getPlayer().step();
        model.getPlayer().getTranslocator().step();
        model.getPlayer().getTranslocator().decelerate(model.TRANSLOCATOR_DECELERATION);

        for(Shooter shooter : model.getShooters()){
            Projectile newProjectile = shooter.shootIfReady(model.SHOOTER_BULLET_VELOCITY, model.getPlayer().getCenter());
            if(newProjectile != null)
                this.gameView.getChildren().add(newProjectile);

            Iterator<Projectile> iterator = shooter.getProjectiles().iterator();
            while(iterator.hasNext()) {
                Projectile projectile = iterator.next();
                projectile.decrementCycles();
                if(projectile.getCyclesUntilDisappear() <= 0) {
                    this.gameView.getChildren().remove(projectile);
                    projectile.getChildren().remove(projectile.getImageView());
                    iterator.remove();
                } else {
                    projectile.step();
                }
            }
        }
        this.difficultyIncreaseCounter--;
    }

    /**
     * Code that checks for contact between certain sprites
     */
    public void checkCollision() {
        //ChordStone and Player
        double CstoneCenterPositionX = this.model.getChordStone().getCenterX();
        double CstoneCenterPositionY = this.model.getChordStone().getCenterY();
        double playerPositionX = this.model.getPlayer().getPosition().getX();
        double playerPositionY = this.model.getPlayer().getPosition().getY();
        double playerPositionXOuter = this.model.getPlayer().getXOuter();
        double playerPositionYOuter = this.model.getPlayer().getYOuter();

        if(CstoneCenterPositionX >= playerPositionX && CstoneCenterPositionX <= playerPositionXOuter &&
                CstoneCenterPositionY >= playerPositionY && CstoneCenterPositionY <= playerPositionYOuter) {
            this.model.getChordStone().makeSound();
            this.model.spawnChordStone(this.gameView.FRAME_WIDTH - this.model.getChordStone().getWidth(), this.gameView.FRAME_HEIGHT - this.model.getChordStone().getHeight());
        }
        for(Shooter shooter : this.model.getShooters()) {
            //Projectile and Player

            audioclip = new AudioClip(getClass().getResource("/res/Bump.wav").toString());

            Iterator<Projectile> iterator = shooter.getProjectiles().iterator();
            while (iterator.hasNext()) {
                Projectile projectile = iterator.next();
                double projectileX = projectile.getPosition().getX();
                double projectileY = projectile.getPosition().getY();
                double projectileOuterX = projectile.getXOuter();
                double projectileOuterY = projectile.getYOuter();

                if ((projectileOuterX >= playerPositionX && projectileX <= playerPositionXOuter) && (projectileY <= playerPositionYOuter && projectileOuterY >= playerPositionY)) {
                    iterator.remove();
                    this.gameView.getChildren().remove(projectile);
                    this.audioclip.play();
                    this.model.getPlayer().takeDamage(1);
                    this.model.getChordStone().makeSound();
                    if(model.getPlayer().getLifeTotal() > 0)
                        this.model.getPlayer().takeDamage(1);
                }
            }
        }
    }

    /**
     * Code that checks for the boundaries of sprites and teleports to them to their correct position
     */
    public void checkBoundaries() {

        //Player Movement

        if(model.getPlayer().getPosition().getX() >= gameView.FRAME_WIDTH && model.getPlayer().getVelocity().getX() > 0) {
            model.getPlayer().setPosition(-model.getPlayer().getWidth(),model.getPlayer().getPosition().getY());
        }
        else if(model.getPlayer().getPosition().getX() < 0 && model.getPlayer().getVelocity().getX() < 0) {
            model.getPlayer().setPosition(gameView.FRAME_WIDTH + model.getPlayer().getWidth(), model.getPlayer().getPosition().getY());
        }
        else if(model.getPlayer().getPosition().getY() >= gameView.FRAME_HEIGHT && model.getPlayer().getVelocity().getY() > 0) {
            model.getPlayer().setPosition(model.getPlayer().getPosition().getX(), -model.getPlayer().getHeight());
        }
        else if(model.getPlayer().getPosition().getY() < 0 && model.getPlayer().getVelocity().getY() < 0) {
            model.getPlayer().setPosition(model.getPlayer().getPosition().getX(), gameView.FRAME_HEIGHT + model.getPlayer().getHeight());
        }

        //Translocator Movement

        if (model.getPlayer().getTranslocator().getPosition().getX() + model.getPlayer().getTranslocator().getWidth() >= gameView.FRAME_WIDTH && model.getPlayer().getTranslocator().getVelocity().getX() > 0) {
            model.getPlayer().getTranslocator().setPosition(-model.getPlayer().getTranslocator().getWidth(),model.getPlayer().getTranslocator().getPosition().getY());
        }
        else if(model.getPlayer().getTranslocator().getPosition().getX() < 0 && model.getPlayer().getTranslocator().getVelocity().getX() < 0) {
            model.getPlayer().getTranslocator().setPosition(gameView.FRAME_WIDTH + model.getPlayer().getTranslocator().getWidth(), model.getPlayer().getTranslocator().getPosition().getY());
        }
        else if(model.getPlayer().getTranslocator().getPosition().getY() >= gameView.FRAME_HEIGHT && model.getPlayer().getTranslocator().getVelocity().getY() > 0) {
            model.getPlayer().getTranslocator().setPosition(model.getPlayer().getTranslocator().getPosition().getX(), -model.getPlayer().getTranslocator().getHeight());
        }
        else if(model.getPlayer().getTranslocator().getPosition().getY() < 0 && model.getPlayer().getTranslocator().getVelocity().getY() < 0) {
            model.getPlayer().getTranslocator().setPosition(model.getPlayer().getTranslocator().getPosition().getX(), gameView.FRAME_HEIGHT + model.getPlayer().getTranslocator().getHeight());
        }
    }
    
    @Override
    /**
     * Code required for controlling movement with keys
     *
     * @param keyEvent user input
     */
    public void handle(KeyEvent keyEvent) {
        if(model.isGameOver())
            return;
        KeyCode code = keyEvent.getCode();
        if(keyEvent.getEventType().equals(KEY_PRESSED)){
            handleKeyPressed(code);
        } else {
            handleKeyReleased(code);
        }
    }

    private void handleKeyPressed(KeyCode code) {
        double stepSize = model.getPlayer().getSpeed();
        if(code == KeyCode.LEFT || code == KeyCode.A){
            double yVel = model.getPlayer().getVelocity().getY();
            model.getPlayer().setVelocity(-stepSize, yVel);
            model.getPlayer().refreshAnimation(0, 160);
            model.getPlayer().getAnimation().play();
            model.getPlayer().setDirection(Player.Direction.LEFT);
        } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
            double yVel = model.getPlayer().getVelocity().getY();
            model.getPlayer().setVelocity(stepSize, yVel);
            model.getPlayer().refreshAnimation(0, 230);
            model.getPlayer().getAnimation().play();
            model.getPlayer().setDirection(Player.Direction.RIGHT);
        } else if (code == KeyCode.UP || code == KeyCode.W) {
            double xVel = model.getPlayer().getVelocity().getX();
            model.getPlayer().setVelocity(xVel, -stepSize);
            model.getPlayer().refreshAnimation(0, 90);
            model.getPlayer().getAnimation().play();
            model.getPlayer().setDirection(Player.Direction.UP);
        } else if (code == KeyCode.DOWN || code == KeyCode.S) {
            double xVel = model.getPlayer().getVelocity().getX();
            model.getPlayer().setVelocity(xVel, stepSize);
            model.getPlayer().refreshAnimation(0, 10);
            model.getPlayer().getAnimation().play();
            model.getPlayer().setDirection(Player.Direction.DOWN);
        } else if (code == KeyCode.E) {
            if (model.getPlayer().getTranslocator().isThrown()) {
                model.getPlayer().teleport();
            } else {
                double angle = calculateThrowingAngle(model.getPlayer());
                model.getPlayer().throwTranslocator(angle, model.getPlayer().getThrowPower());
            }
        } else if (code == KeyCode.ESCAPE) {

            audioclip = new AudioClip(getClass().getResource("/res/Pause.wav").toString());

            if (this.paused) {
                audioclip.play();
                this.startTimer();
                this.pauseLabel.setText("");
            } else {
                audioclip.play();
                this.timer.cancel();
                this.pauseLabel.setText("Paused");
            }
            this.paused = !this.paused;
        }
        else if(code == KeyCode.R) {
            model.getPlayer().recallTranslocator();
        }
    }

    private void handleKeyReleased(KeyCode code) {
        if(code == KeyCode.LEFT || code == KeyCode.A){
            double yVel = model.getPlayer().getVelocity().getY();
            model.getPlayer().setVelocity(0, yVel);
            model.getPlayer().getAnimation().pause();
        }
        else if(code == KeyCode.RIGHT || code == KeyCode.D) {
            double yVel = model.getPlayer().getVelocity().getY();
            model.getPlayer().setVelocity(0, yVel);
            model.getPlayer().getAnimation().pause();
        }
        else if(code == KeyCode.UP || code == KeyCode.W) {
            double xVel = model.getPlayer().getVelocity().getX();
            model.getPlayer().setVelocity(xVel, 0);
            model.getPlayer().getAnimation().pause();
        }
        else if(code == KeyCode.DOWN || code == KeyCode.S) {
            double xVel = model.getPlayer().getVelocity().getX();
            model.getPlayer().setVelocity(xVel, 0);
            model.getPlayer().getAnimation().pause();
        }

    }

    public static double calculateThrowingAngle(Player player) {
        double xVelocity = player.getVelocity().getX();
        double yVelocity = player.getVelocity().getY();
        if(xVelocity > 0) {
            if(yVelocity > 0) {
                return 45;
            } else if (yVelocity < 0){
                return 315;
            } else {
                return 0;
            }
        }
        else if (xVelocity < 0) {
            if(yVelocity > 0) {
                return 135;
            } else if (yVelocity < 0) {
                return 225;
            } else {
                return 180;
            }
        }
        else {
            if(yVelocity > 0) {
                return 90;
            } else if(yVelocity < 0) {
                return 270;
            } else {
                switch(player.getDirection()){
                    case RIGHT:
                        return 0;
                    case LEFT:
                        return 180;
                    case DOWN:
                        return 90;
                    case UP:
                        return 270;
                    default:
                        return 0;
                }
            }
        }
    }

    private void update() {
        this.gameView.update(this.model);
    }
}
