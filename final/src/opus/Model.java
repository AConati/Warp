/**
 * Model.java
 * Ari Conati & Grant Lee
 * 22 May 2018
 *
 * The class that represents the model/the underlying game logic.
 */
package opus;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Model {
    public final int SMART_SHOOTER_FIRE_RATE = 60;
    public final int SHOOTER_FIRE_RATE = 30;
    public final int SHOOTER_PROJECTILE_LIFE = 10;

    private Player player;
    private List<Shooter> shooters = new ArrayList<Shooter>();
    private ChordStone chordStone;

    public enum DifficultyModifier {
        ADD_SHOOTER, ADD_SMART_SHOOTER;
    }

    private boolean gameOver = false;
    private int score;
    private DifficultyModifier nextDifficultyMod = DifficultyModifier.ADD_SHOOTER;

    //Initialize instance variables
    public Model(double xBoundary, double yBoundary) {
        this.initialize(xBoundary, yBoundary);
        this.score = 0;
    }

    private void initialize(double xBoundary, double yBoundary) {
        Point2D playerPosition = new Point2D(10,10);
        player = new Player("hero", 5, playerPosition);
        player.setVelocity(0,0);

        Point2D shooterPosition = new Point2D(100,100);
        Shooter shooter = new Shooter(SHOOTER_PROJECTILE_LIFE, shooterPosition, true);
        shooter.setFireRate(SMART_SHOOTER_FIRE_RATE);
        shooters.add(shooter);

        chordStone = new ChordStone();
        chordStone.setPosition(Math.random()*(xBoundary - this.getChordStone().getWidth()), Math.random()*(yBoundary - this.getChordStone().getHeight()));
        chordStone.setVelocity(0,0);
    }


    public Player getPlayer() {
        return this.player;
    }

    public List<Shooter> getShooters() {
        return this.shooters;
    }

    public ChordStone getChordStone() {
        return this.chordStone;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /*
     * Sets the chord stone's location to a new location and increments the player's score by 1.
     */

    public void spawnChordStone(double xBoundary, double yBoundary) {
        double newXPosition = Math.random()*xBoundary;
        double newYPosition = Math.random()*yBoundary;
        this.chordStone.setPosition(newXPosition, newYPosition);
        this.chordStone.makeSound();
        this.score++;
    }

    /*
     * Determines if the player is in contact with the chord stone based on the player and chordstone's size
     * and their respective positions.
     *
     * @return true if the player's location overlaps with that of the chord stone; false otherwise.
     */

    public boolean playerCollidesWithStone() {
        return true;
    }

    /*
     * Creates a new Shooter object and adds it to the array of shooters.
     */

    private Shooter spawnSmartShooter(int cycles, double xBoundary, double yBoundary) {
        double xPosition = Math.random()*xBoundary - this.getShooters().get(0).getWidth();
        double yPosition = Math.random()*yBoundary - this.getShooters().get(0).getHeight();
        Shooter shooter = new Shooter(cycles, new Point2D(xPosition, yPosition), true);
        shooter.setFireRate(SMART_SHOOTER_FIRE_RATE);
        shooters.add(shooter);
        return shooter;
    }

    private Shooter spawnShooter(int cycles, double xBoundary, double yBoundary) {
        double xPosition = Math.random()*xBoundary - this.getShooters().get(0).getWidth();
        double yPosition = Math.random()*yBoundary - this.getShooters().get(0).getHeight();
        Shooter shooter = new Shooter(cycles, new Point2D(xPosition, yPosition), false);
        shooter.setFireRate(SHOOTER_FIRE_RATE);
        double targetX = Math.random()*xBoundary;
        double targetY = Math.random()* yBoundary;
        shooter.setTarget(new Point2D(targetX, targetY));
        shooters.add(shooter);
        return shooter;
    }

    public Shooter increaseDifficulty(double xBoundary, double yBoundary) {
        switch(nextDifficultyMod) {
            case ADD_SHOOTER:
                nextDifficultyMod = DifficultyModifier.ADD_SMART_SHOOTER;
                return spawnShooter(SHOOTER_PROJECTILE_LIFE, xBoundary, yBoundary);
            case ADD_SMART_SHOOTER:
                nextDifficultyMod = DifficultyModifier.ADD_SHOOTER;
                return spawnSmartShooter(SHOOTER_PROJECTILE_LIFE, xBoundary, yBoundary);
            default:
                return null;
        }
    }

}
