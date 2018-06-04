/**
 * Model.java
 * Ari Conati & Grant Lee
 * 22 May 2018
 *
 * The class that represents the model/the underlying game logic.
 */

package warp;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class Model {
    public final int SMART_SHOOTER_FIRE_RATE = 60; //the rate at which the projectiles are fired from smart turret
    public final int SHOOTER_FIRE_RATE = 30; //the rate at which teh projectiles are fired from normal turret
    public final int SHOOTER_PROJECTILE_LIFE = 25; //how many steps a projectile will take before disappearing
    public final int SHOOTER_BULLET_VELOCITY = 15; //the speed at which the projectiles move
    public final int TRANSLOCATOR_DECELERATION = 3; //how quickly the translocator stops

    private Player player;
    private List<Shooter> shooters = new ArrayList<Shooter>();
    private MagicStone magicStone;

    public enum DifficultyModifier {
        ADD_SHOOTER, ADD_SMART_SHOOTER;
    }

    private int score;
    private DifficultyModifier nextDifficultyMod = DifficultyModifier.ADD_SHOOTER;

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

        magicStone = new MagicStone();
        magicStone.setPosition(Math.random()*(xBoundary - this.getMagicStone().getWidth()), Math.random()*(yBoundary - this.getMagicStone().getHeight()));
        magicStone.setVelocity(0,0);
    }


    public Player getPlayer() {
        return this.player;
    }

    public List<Shooter> getShooters() {
        return this.shooters;
    }

    public MagicStone getMagicStone() {
        return this.magicStone;
    }

    public boolean isGameOver() {
        return this.player.getLifeTotal() <= 0;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Sets the chord stone's location to a new location and increments the player's score by 1.
     * @param xBoundary the maximum x boundary the stone can spawn within
     * @param yBoundary the maximum y boundary the stone can spawn within
     */
    public void spawnChordStone(double xBoundary, double yBoundary) {
        double newXPosition = Math.random()*xBoundary;
        double newYPosition = Math.random()*yBoundary;
        this.magicStone.setPosition(newXPosition, newYPosition);
        this.magicStone.makeSound();
        this.score++;
    }

    /**
     * The method for Spawning each type of shooter
     * A smart shooter targets the position of the player but has a low rate of fire
     * A normal shooter targets a random position but has a higher rate of fire
     */
    private Shooter spawnSmartShooter(double xBoundary, double yBoundary) {
        double xPosition = Math.random()*xBoundary - this.getShooters().get(0).getWidth();
        double yPosition = Math.random()*yBoundary - this.getShooters().get(0).getHeight();
        Shooter shooter = new Shooter(SHOOTER_PROJECTILE_LIFE, new Point2D(xPosition, yPosition), true);
        shooter.setFireRate(SMART_SHOOTER_FIRE_RATE);
        shooters.add(shooter);
        return shooter;
    }

    private Shooter spawnShooter(double xBoundary, double yBoundary) {
        double xPosition = Math.random()*xBoundary - this.getShooters().get(0).getWidth();
        double yPosition = Math.random()*yBoundary - this.getShooters().get(0).getHeight();
        Shooter shooter = new Shooter(SHOOTER_PROJECTILE_LIFE, new Point2D(xPosition, yPosition), false);
        shooter.setFireRate(SHOOTER_FIRE_RATE);
        double targetX = Math.random()*xBoundary;
        double targetY = Math.random()* yBoundary;
        shooter.setTarget(new Point2D(targetX, targetY));
        shooters.add(shooter);
        return shooter;
    }

    /**
     * The method used to determine which type of shooter gets spawned
     */
    public Shooter increaseDifficulty(double xBoundary, double yBoundary) {
        switch(nextDifficultyMod) {
            case ADD_SHOOTER:
                nextDifficultyMod = DifficultyModifier.ADD_SMART_SHOOTER;
                return spawnShooter(xBoundary, yBoundary);
            case ADD_SMART_SHOOTER:
                nextDifficultyMod = DifficultyModifier.ADD_SHOOTER;
                return spawnSmartShooter(xBoundary, yBoundary);
            default:
                return null;
        }
    }

}
