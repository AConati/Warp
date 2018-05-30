package opus;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class PlayerView extends Group {

    final private double FRAMES_PER_SECOND = 20.0;
    private Image missleImage;
    private ArrayList<Sprite> spriteList;

    public PlayerView() {
        this.missleImage = new Image(getClass().getResourceAsStream("/res/error.jpg"));
    }

    public void update(Model model) {
        System.out.println(model.getChordStone().getPosition());
        this.getChildren().add(model.getChordStone().getImageView());



    }


}



