/**
 * Model.java
 * Ari Conati & Grant Lee
 * 22 May 2018
 *
 * The class that represents the model/the underlying game logic.
 */

public class Model {
    private Player player;
    private Shooter[] shooters;
    private ChordStone chordStone;

    private boolean gameOver;
    private int score;

    //Initialize instance variables
    public Model() {}


    public Player getPlayer() {
        return this.player;
    }

    public Shooter[] getShooters() {
        return this.shooters;
    }

    public ChordStone getChordStone() {
        return this.chordStone;
    }

    public boolean isGameOver() {
        return this.gameOver();
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

    public void spawnChordStone() {
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