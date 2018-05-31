/** 
 * Translocator.java
 * Ari Conati & Grant Lee
 * 22 May 2018
 *
 * The class that represents the object that the player can throw and teleport to.
 */
 
 package opus;


import javafx.geometry.Point2D;

public class Translocator extends Sprite {

    private boolean visible;

    public Translocator(Point2D position) {
       this.makeVisible(false);
       this.setImageView(makeImage("res/chordStone.png", 0,50, 26,33,8,400));
       this.getChildren().add(this.getImageView());
       this.setPosition(position.getX(), position.getY());
       this.setVelocity(0,0);
    }


    /*
     * Sets the height and width of the sprite representing the translocator.
     *
     * @param width The width of the sprite.
     * @param height The height of the sprite
     */
    public void setSize(double width, double height) {

    }

    /*
     * Sets the translocator to hidden or visible. If it has not been thrown,
     * it's sprite is not visible to the user.
     *
     * @param visible If true, sets the translocator object to visible; else, hidden.
     */

    public void makeVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean getVisible() {
        return this.visible;
    }

    public void decelerate(double deceleration) {

    }
}

