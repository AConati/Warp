/**
 * Projectile.java
 * Ari Conati & Grant Lee
 * 22 May 2018
 *
 * The class that represents the objects that the player must evade.
 */

package warp;

import javafx.geometry.Point2D;
import javafx.scene.media.AudioClip;

public class Projectile extends Sprite {

    //counter to keep track of how long projectile stays active
    private int cyclesUntilDisappear;
    private AudioClip audioclip;


    public Projectile( int cycles, Point2D position) {
        cyclesUntilDisappear = cycles;

        this.setImageView(makeImage("src/res/bullet.png"));
        this.setOffsets(0,0);
        this.setViewport(13, 13);
        this.makeAnimation(1,1);
        this.getChildren().add(this.getImageView());
        this.setPosition(position.getX(), position.getY());

        audioclip = new AudioClip(getClass().getResource("/res/Bump.wav").toString());

    }

    public void decrementCycles() {
        cyclesUntilDisappear--;
    }

    public int getCyclesUntilDisappear() {
        return this.cyclesUntilDisappear;
    }

    public void playAudioClip() {
        this.audioclip.play();
    }

}