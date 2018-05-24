/**
 * ChordStone.java
 * Ari Conati & Grant Lee
 * 22 May 201
 *
 * The class that represents the chord stone.
 *
 * The chord stone is one of the main objectives of the game.
 * The stone shall move randomly and it will also originate from a random spot
 */

package sprites;

import javafx.fxml.FXML;
import javafx.scene.shape.Circle;

public class ChordStone extends Circle {
    @FXML private double velocityX;
    @FXML private double velocityY;

    public ChordStone() {

    }

    /*
     * Moves the stone by x or y velocity
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

}

