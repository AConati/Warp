package sprites;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class PlayerView extends Group {

    final private double SCENE_WIDTH = 500;
    final private double SCENE_HEIGHT = 400;
    final private double FRAMES_PER_SECOND = 20.0;
    private Image missleImage;
    private ArrayList<Sprite> spriteList;

    public int PlayerView() {
        this.missleImage = new Image(getClass().getResourceAsStream("/res/error.jpg"));
    }

    public void update(Model model) {
        if(model.getChordStone().getStatus()) {


    }


}



