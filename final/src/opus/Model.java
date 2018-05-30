/**
 * Model.java
 * Ari Conati & Grant Lee
 * 22 May 2018
 *
 * The class that represents the model/the underlying game logic.
 */
package opus;

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

        player = new Player("hero", 100);
        player.setPosition(200, 200);
        player.setVelocity(0,0);

//        Shooter shooter = new Shooter();
//        shooter.setPosition(5,5);
//        shooter.setSize(20,20);
//        shooter.setVelocity(0,1);
//        shooters.add(shooter);

        chordStone = new ChordStone();
<<<<<<< HEAD
        chordStone.setPosition(500, 500);
=======
        chordStone.setPosition(100, 800);
>>>>>>> 2b6d653166c199cab524254eabe4ae3f2534a585
        chordStone.setVelocity(1,1);
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

    public void spawnChordStone(int xBoundary, int yBoundary) {
        this.score++;
        Random random = new Random();
        double newXPosition = random.nextInt(xBoundary);
        double newYPosition = random.nextInt(yBoundary);
        this.chordStone.setPosition(newXPosition, newYPosition);
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
