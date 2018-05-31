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

    private boolean thrown;

    public Translocator(Point2D position) {
       this.setThrown(false);
       this.setImageView(makeImage("src/res/chordStone.png"));
       this.setImageView(makeImage("src/res/chordStone.png"));
       this.setOffsets(0,50);
       this.setViewport(26,33);
       this.makeAnimation(8,400);
       this.getChildren().add(this.getImageView());
       this.setPosition(position.getX(), position.getY());
       this.setVelocity(0,0);
    }


    public void setThrown(boolean thrown) {
        this.thrown = thrown;
    }

    public boolean isThrown() {
        return this.thrown;
    }

    public void decelerate(double deceleration) {

    }
}

