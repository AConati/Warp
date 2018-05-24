/**
 * Projectile.java
 * Ari Conati & Grant Lee
 * 22 May 2018
 *
 * The class that represents the objects that the player must evade.
 */

package sprites;

import java.awt.geom.Point2D;

public class Projectile {

    private int power; // var representing the amount of damage dealt to the player if hit by this projectile

    public Projectile(int power, Point2D size) {
        this.power = power;
    }


    /**
     * Sets the height and width of the sprite representing the projectile.
     *
     * @param width The width of the sprite.
     * @param height The height of the sprite
     */

    public void setSize(double width, double height) {

    }

    public int getPower() {
        return 0;
    }

}