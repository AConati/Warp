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
    private int fireRate = 0;
    private int fireCount;

    public Shooter(int cycles, Point2D position) {
        this.projectiles = new ArrayList<Projectile>();
        this.cycleLifeForProjectiles = cycles;
        this.setImageView(makeImage("src/res/turret.png"));
        this.setOffsets(0,0);
        this.setViewport(33, 32);
        this.makeAnimation(4,400);
        this.getAnimation().play();
        this.getChildren().add(this.getImageView());
        this.setPosition(position.getX(), position.getY());
        this.setVelocity(0,0);
    }

    /*
    * Shoots a projectile in the direction that this object is facing.
    *
    * @param power The amount of damage the projectile will deal.
    * @param size The size of the projectile fired.
     */

    public void shoot(Projectile projectile, double velocity, Point2D target) {

        double angle = Math.atan(target.getY()/target.getX());
        double xVel = Math.cos(angle) * velocity;
        double yVel = Math.sin(angle) * velocity;
        projectile.setVelocity(xVel, yVel);
        projectiles.add(projectile);

    }

    public boolean isReadyToShoot(){
        return fireCount == 0;
    }

    public void decrementFireCount() {
        if(fireCount <= 0) {
            fireCount = fireRate;
        } else {
            fireCount--;
        }
    }

    public List<Projectile> getProjectiles() {
        return this.projectiles;
    }

    public void setFireRate(int fireRate) {
        this.fireRate = fireRate;
    }

    public int getFireRate() {
        return this.fireRate;
    }

}