/**
 * Shooter.java
 * Ari Conati & Grant Lee
 * 22 May 2018
 * 
 * The class that emits the projectile object.
 */

package warp;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class Shooter extends Sprite {

    private List<Projectile> projectiles; //list of shooter's active projectiles
    private int cycleLifeForProjectiles; //the number of frames the shooter's projectiles last
    private int fireRate = 0; //shooter fires a projectile every fireRate - 1 calls to shootIfReady function
    private int fireCount; //keeps track of whether the shooter is ready to fire or not
    private boolean isSmart; //if !isSmart, can only fire in the direction of default target
    private Point2D defaultTarget = new Point2D(0,0);

    public Shooter(int cycles, Point2D position, boolean isSmart) {
        this.projectiles = new ArrayList<Projectile>();
        this.cycleLifeForProjectiles = cycles;

        if(isSmart) {
            this.setImageView(makeImage("src/res/turretSmart.png"));
        }
        else {
            this.setImageView(makeImage("src/res/turretDumb.png"));
            }

        this.setOffsets(0,0);
        this.setViewport(33, 32);
        this.makeAnimation(4,400);
        this.getAnimation().play();
        this.getChildren().add(this.getImageView());
        this.setPosition(position.getX(), position.getY());
        this.setVelocity(0,0);
        this.isSmart = isSmart;
    }

    /**
     * Shoots a projectile at the designated target. If fireCount is not 0 or less,
     * a projectile is not fired. The fireCount is decremented at the start of any call to
     * this method. If fireCount is 0 at the start of a call to this method, it will be reset
     * to this.fireRate.
     *
     * @param velocity The speed at which the projectile travels.
     * @param target The location at which the projectile is aimed.
     *
     * @Return The projectile object shot. null if no projectile fired.
     */

    public Projectile shootIfReady( double velocity, Point2D target) {
        this.decrementFireCount();
        if(!isReadyToShoot()) {
            return null;
        }
        if(!isSmart || target == null)
            target = defaultTarget;

        fireCount = fireRate;

        Projectile projectile = new Projectile(this.cycleLifeForProjectiles, this.getPosition());
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

    /**
     * @Return true if the shooter will fire on the next call to
     * shootIfReady function
     */

    public boolean isReadyToShoot(){
        return fireCount == 0;
    }

    /**
     * Decrements the shooter's fireCount. If the fireCount is 0, it sets
     * it to the shooter's fire rate.
     */

    private void decrementFireCount() {
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

    public void setTarget(Point2D target) {
        this.defaultTarget = target;
    }

    public int getFireRate() {
        return this.fireRate;
    }

}