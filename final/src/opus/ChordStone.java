/**
 * ChordStone.java
 * Ari Conati & Grant Lee
 * 22 May 201
 *
 * The class that represents the chord stone.
 * The chordstone is the main objective of the game and
 * will be moving from place to place to prevent player from
 * obtaining the stone.
 */

package opus;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;


public class ChordStone extends Sprite {
    private AudioClip audioClip;
    private boolean isActive;


    public ChordStone() {
        this.isActive = false;
        this.setImageView(makeImage("src/res/chordStone.png"));
        this.setOffsets(0,50);
        this.setViewport(26,33);
        this.makeAnimation(8,400);
        this.getChildren().add(this.getImageView());
        this.audioClip = new AudioClip(getClass().getResource("/res/ding.wav").toString());
    }

    public boolean getStatus() { return this.isActive; }

    void makeSound() {
        this.audioClip.play();
    }
}