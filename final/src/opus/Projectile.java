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

    public Projectile(double power, int cycles) {
        this.power = power;
        cyclesUntilDisappear = cycles;
    }


    public double getPower() {
        return this.power;
    }

    public void decrementCycles() {
        cyclesUntilDisappear--;
    }

}