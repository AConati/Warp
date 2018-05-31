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

    public Player(String name, int lifeTotal) {
        this.name = name;
        this.lifeTotal = lifeTotal;
//      this.translocator = new Translocator(this.getPosition());
        this.setImageView(makeImage("src/res/topdownsmall.png", 0,0, 50,80,1,400));
        this.getChildren().add(this.getImageView());
    }


    public void throwTranslocator(double angle, int power) {
        if(this.translocator.getPosition().equals(this.getPosition()))
            return;
        this.translocator.setVisible(true);
        double xVel = Math.cos(Math.toRadians(angle)) * power;
        double yVel = Math.sin(Math.toRadians(angle)) * power;
        this.translocator.setVelocity(xVel, yVel);

    }

    /*
     * Teleports the user to the location of the translocator. Has no effect if the translocator has not been thrown.
     */

    public void teleport() {
        this.translocator.setVelocity(0,0);
        this.translocator.setVisible(false);
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

}



   

