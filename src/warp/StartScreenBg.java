/**
 * StartScreenBg.java
 *
 * Ari Conati & Grant Lee
 * 22 May 2018
 *
 * The sprite that represents the background image of the intro screen.
 * Image belongs to NASA: https://www.jpl.nasa.gov/news/?search=&news_dates=&news_destinations=StarsGalaxies
 * audioclip belongs to 8-bit Universe: https://www.youtube.com/channel/UCn4HDI02U4f3VEsghRX7dRw
 */

package warp;

import javafx.scene.media.AudioClip;

public class StartScreenBg extends Sprite {

    private AudioClip audioClip;

    public StartScreenBg() {
        this.setImageView(makeImage("src/res/spiral.png"));
        this.getChildren().add(this.getImageView());

        this.audioClip = new AudioClip(getClass().getResource("/res/intro.mp3").toString());
    }

    public void playMusic() {
        if(!audioClip.isPlaying()) {
            this.audioClip.setCycleCount(100);
            this.audioClip.play();
        }
    }

    public void stopMusic() {
        this.audioClip.stop();
    }

}
