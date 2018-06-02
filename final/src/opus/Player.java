/**
 * Player.java
 * Ari Conati & Grant Lee
 * 22 May 2018
 *
 * The class that represents the main player
 */

package opus;


import javafx.geometry.Point2D;

public class Player extends Sprite {

    private Translocator translocator;
    private int lifeTotal;
    private String name;

    public enum Direction {
        RIGHT, LEFT, UP, DOWN;
    }

    private Direction direction;

    public Player(String name, int lifeTotal, Point2D position) {
        this.name = name;
        this.lifeTotal = lifeTotal;
        this.setImageView(makeImage("src/res/topdownsmall.png"));
        this.setOffsets(0,11);
        this.setViewport(50,60);
        this.makeAnimation(4,200);
        this.getChildren().add(this.getImageView());
        this.translocator = new Translocator(position);
        this.translocator.setThrown(false);
        this.setPosition(position.getX(), position.getY());
        this.direction = Direction.DOWN;
    }


    public void throwTranslocator(double angle, int power) {
        this.translocator.setPosition(this.getPosition().getX(), this.getPosition().getY());
        this.translocator.setThrown(true);
        double xVel = Math.cos(Math.toRadians(angle)) * power;
        double yVel = Math.sin(Math.toRadians(angle)) * power;
        this.translocator.setVelocity(xVel, yVel);
    }

    /*
     * Teleports the user to the location of the translocator. Has no effect if the translocator has not been thrown.
     */

    public void teleport() {
        this.translocator.setVelocity(0,0);
        this.translocator.setThrown(false);
        this.setPosition(this.translocator.getPosition().getX(), this.translocator.getPosition().getY());
    }
    /*
     * Lowers the player's lifeTotal by the amount specified by the parameter. If this lowers the
     * lifeTotal of the player to 0 or less, it destroys the player (invokes this.destroy()).
     *
     * @param damage The amount of hit points to be subtracted from the player's current life total.
     */
    public void takeDamage(int damage) {
        this.lifeTotal -= damage;
        if(this.lifeTotal <= 0)
            this.destroy();
    }

    /*
     * Destroys the player and removes its sprite and its translocator's sprite from the game board.
     */

    public void destroy() {


    }

    public int getLifeTotal() {
        return this.lifeTotal;
    }

    public Translocator getTranslocator() {
        return this.translocator;
    }

    public void refreshAnimation(int newOffSetX, int newOffsetY) {
        if (newOffSetX != this.getOffSetX() || newOffsetY != this.getOffSetY()) {
            this.getAnimation().pause();
            this.setOffsets(newOffSetX, newOffsetY);
            this.makeAnimation(4,300);
        }
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }


    @Override
    public void setPosition(double x, double y) {
        super.setPosition(x,y);
        if(!(this.translocator.isThrown())) {
            this.translocator.setPosition(x,y);
        }

    }
}



   

