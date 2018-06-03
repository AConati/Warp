package warp;

import javafx.scene.Group;

public class GameView extends Group {

    public final double FRAME_HEIGHT = 600;
    public final double FRAME_WIDTH = 800;


    public GameView() {
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



