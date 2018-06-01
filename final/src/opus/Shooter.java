/**
 * Shooter.java
 * Ari Conati & Grant Lee
 * 22 May 2018
 * 
 * The class that emits the projectile object.
 */

package opus;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class Shooter extends Sprite {

    private List<Projectile> projectiles;
    private int cycleLifeForProjectiles;

    public Shooter(int cycles) {
        this.projectiles = new ArrayList<Projectile>();
        this.cycleLifeForProjectiles = cycles;
    }
    /*
     * Sets the height and width of the sprite representing the shooter.
     *
     * @param width The width of the sprite.
     * @param height The height of the sprite
     */

    public void setSize(double width, double height) {

    }

    /*
    * Shoots a projectile in the direction that this object is facing.
    *
    * @param power The amount of damage the projectile will deal.
    * @param size The size of the projectile fired.
     */

    public void shoot(double power, double velocity, Point2D playerLocation) {
        Projectile projectile = new Projectile(power, this.cycleLifeForProjectiles);
        double angle = Math.atan(playerLocation.getY()/playerLocation.getX());
        double xVel = Math.cos(angle) * velocity;
        double yVel = Math.sin(angle) * velocity;
        projectile.setVelocity(xVel, yVel);
        projectiles.add(projectile);

    }

}