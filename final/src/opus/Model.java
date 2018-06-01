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
import java.util.Random;

public class Model {
    private Player player;
    private ArrayList<Shooter> shooters;
    private ChordStone chordStone;

    private boolean gameOver;
    private int score;

    //Initialize instance variables
    public Model() {
        this.initialize();
        this.score = 0;
    }

    private void initialize() {
        Point2D playerPosition = new Point2D(10,10);
        player = new Player("hero", 100, playerPosition);

        player.setVelocity(0,0);

//        Shooter shooter = new Shooter();
//        shooter.setPosition(5,5);
//        shooter.setSize(20,20);
//        shooter.setVelocity(0,1);
//        shooters.add(shooter);

        chordStone = new ChordStone();
        chordStone.setPosition(200,200);
        chordStone.setVelocity(0,0);
    }


    public Player getPlayer() {
        return this.player;
    }

    public ArrayList<Shooter> getShooters() {
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
        this.score++;
        Random random = new Random();
        double newXPosition = Math.random()*xBoundary;
        double newYPosition = Math.random()*yBoundary;
        this.chordStone.setPosition(newXPosition, newYPosition);
        this.chordStone.makeSound();
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

    public void spawnShooter() {

    }

}
