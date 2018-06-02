/**
 * Projectile.java
 * Ari Conati & Grant Lee
 * 22 May 2018
 *
 * The class that represents the objects that the player must evade.
 */

package opus;

import javafx.geometry.Point2D;

public class Projectile extends Sprite{

    private double power; // var representing the amount of damage dealt to the player if hit by this projectile
    private int cyclesUntilDisappear;

    public Projectile(double power, int cycles, Point2D position) {
        this.power = power;
        cyclesUntilDisappear = cycles;

        this.setImageView(makeImage("src/res/bullet.png"));
        this.setOffsets(0,0);
        this.setViewport(13, 13);
        this.makeAnimation(1,1);
        this.getChildren().add(this.getImageView());
        this.setPosition(position.getX(), position.getY());
    }


    public double getPower() {
        return this.power;
    }

    public void decrementCycles() {
        cyclesUntilDisappear--;
    }

    public int getCyclesUntilDisappear() {
        return this.cyclesUntilDisappear;
    }

}