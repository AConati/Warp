/**
 * Player.java
 * Ari Conati & Grant Lee
 * 22 May 2018
 *
 * The class that represents the main player
 */

package sprites;

public class Player extends Sprite {

    private Translocator translocator;
    private int lifeTotal;

    public Player(String name, Point2D position) {
        this.name = name;
        this.setPosition(position.getX(), position.getY());
        this.translocator = new Translocator(this.getPosition());
    }

    /*
     * Sets the height and width of the sprite representing the player.
     *
     * @param width The width of the sprite.
     * @param height The height of the sprite
     */

    public void setSize(double width, double height) {
    }

    /*
     * Throws the player's translocator in the direction specified by the parameter. This will be designed
     * to be extremely flexible (throwable in any direction) or extremely inflexible (throwable in 1 or a few directions)
     * based on time constraints.
     *
     * @param angle An integer between 0 and 360 which represents the angle in degrees which represent the angle at which
     * the translocator is thrown.
     *
     */

    public void throwTranslocator(int angle) {
    }

    /*
     * Teleports the user to the location of the translocator. Has no effect if the translocator has not been thrown.
     */

    public void teleport() {
    }
    /*
     * Lowers the player's lifeTotal by the amount specified by the parameter. If this lowers the
     * lifeTotal of the player to 0 or less, it destroys the player (invokes this.destroy()).
     *
     * @param damage The amount of hit points to be subtracted from the player's current life total.
     */
    public void takeDamage(int damage) {

    }

    /*
     * Destroys the player and removes its sprite and its translocator's sprite from the game board.
     */

    public void destroy() {

    }

    public int getLifeTotal() {
        return this.lifeTotal;
    }


}



   

