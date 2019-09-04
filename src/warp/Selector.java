/**
 * Selector.java
 * Ari Conati & Grant Lee
 * 22 May 2018
 *
 * Represents the selector sprite used as a pointer for selections
 * Image belongs to Iconfinder: https://www.iconfinder.com/icons/731839/arrow_game_mountain_pixel_art_pixelated_triangle_up_icon#size=256
 */

package warp;

public class Selector extends Sprite {

    public Selector() {
        this.setImageView(makeImage("src/res/selector.png"));
        this.getChildren().add(this.getImageView());
    }
}
