package warp;

import javafx.scene.media.AudioClip;
import javafx.scene.transform.Rotate;

public class gameBg extends Sprite {

    private AudioClip audioClip;

    public gameBg() {
        this.setImageView(makeImage("res/background2.png"));
        this.getChildren().add(this.getImageView());

        this.audioClip = new AudioClip(getClass().getResource("/res/intro.mp3").toString());
    }

    public void step() {
        this.getTransforms().add(new Rotate(2, this.getWidth() / 2.0, this.getHeight()/2.0));
    }

    public void playMusic() {
        this.audioClip.setCycleCount(10);
        this.audioClip.play();
    }

    public void stopMusic() {
        this.audioClip.stop();
    }

}
