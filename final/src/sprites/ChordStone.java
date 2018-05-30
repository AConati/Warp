/**
 * ChordStone.java
 * Ari Conati & Grant Lee
 * 22 May 201
 *
 * The class that represents the chord stone.
 * The chordstone is the main objective of the game and
 * will be moving from place to place to prevent player from
 * obtaining the stone.
 */

package sprites;

import javafx.fxml.FXML;
import javafx.scene.shape.Circle;

public class ChordStone extends Circle {
    @FXML private double velocityX;
    @FXML private double velocityY;
    private boolean isActive;

    public ChordStone() {
        isActive = false;
    }

    /*
     * Moves the ChordStone
     */
    public void step() {
        this.setCenterX(this.getCenterX() + this.velocityX);
        this.setCenterY(this.getCenterY() + this.velocityY);
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public boolean getStatus() { return this.isActive; }
}