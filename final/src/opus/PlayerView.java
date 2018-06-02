package opus;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class PlayerView extends Group {

    public final double FRAME_HEIGHT = 600;
    public final double FRAME_WIDTH = 800;
    final private double FRAMES_PER_SECOND = 20.0;
    private Image missleImage;
    private ArrayList<Sprite> spriteList;


    public PlayerView() {
    }

    public void update(Model model) {
        this.getChildren().add(model.getChordStone());
        this.getChildren().add(model.getPlayer());
        this.getChildren().add(model.getPlayer().getTranslocator());
        for (Shooter shooter : model.getShooters()) {
            this.getChildren().add(shooter);
        }
    }


}



