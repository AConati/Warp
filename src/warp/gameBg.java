/**
 * gameBg.java
 * Ari Conati & Grant Lee
 * 22 May 2018
 *
 * Sprite object that presents the background image
 * of the game.
 * Image belongs to Wallpaper Abyss: https://wall.alphacoders.com/big.php?i=885542
 */

package warp;

public class gameBg extends Sprite {

    public gameBg() {
        this.setImageView(makeImage("res/background2.png"));
        this.getChildren().add(this.getImageView());
    }
}
