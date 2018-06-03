package warp;

public class Selector extends Sprite {

    public Selector() {
        this.setImageView(makeImage("src/res/selector.png"));
        this.getChildren().add(this.getImageView());
    }
}
