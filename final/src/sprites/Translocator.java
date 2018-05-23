/** 
 * PlayerO.java
 * Ari Conati & Grant Lee
 * 22 May 2018
 *
 * The class that represents the object that the player can throw and teleport to.
 */
 
 package sprites;

public class Translocator extends Sprite {

    public Translocator(Point2D position) {
       this.setPosition(position.getX(), position.getY());
       this.setVisible(false);
    }

    /*
    * Sets the translocator to hidden or visible. If it has not been thrown,
    * it's sprite is not visible to the user.
    *
    * @param visible If true, sets the translocator object to visible; else, hidden.
     */

    public void setVisible(boolean visible) {
    }

}

