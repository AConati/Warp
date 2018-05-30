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
        this.setImageView(setImage("src/res/chordStone.png", 0,50, 26,33,8,400));
        this.audioClip = new AudioClip(getClass().getResource("/res/ding.wav").toString());
        this.getChildren().add(this.getImageView());
    }

    public boolean getStatus() { return this.isActive; }

    void makeSound() {
        this.audioClip.play();
    }
}