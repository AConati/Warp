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
        this.setViewport(23, 23);
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

    public Projectile shootIfReady(int power, int cycles, double velocity, Point2D target) {
        if(!isReadyToShoot()) {
            return null;
        }
        fireCount = fireRate;

        Projectile projectile = new Projectile(power, cycles, this.getPosition());
        double xDistanceToTarget = target.getX()-this.getPosition().getX();
        double yDistanceToTarget = target.getY()-this.getPosition().getY();
        Point2D distanceToTarget = new Point2D(Math.abs(xDistanceToTarget), Math.abs(yDistanceToTarget));

        double angle = Math.atan(distanceToTarget.getY()/distanceToTarget.getX());
        double xVel = Math.cos(angle) * velocity;
        double yVel = Math.sin(angle) * velocity;
        if(xDistanceToTarget < 0)
            xVel = -xVel;
        if(yDistanceToTarget < 0)
            yVel = -yVel;
        projectile.setVelocity(xVel, yVel);
        projectiles.add(projectile);
        return projectile;

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