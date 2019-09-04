/**
 * GameView.java
 * Ari Conati & Grant Lee
 * 22 May 2018
 *
 * This class is responsible for the view of the main game event
 */

package warp;

import javafx.scene.Group;

public class GameView extends Group {

    public final double FRAME_HEIGHT = 600;
    public final double FRAME_WIDTH = 800;

    gameBg gameBg = new gameBg();


    public GameView() {

    }

    public void update(Model model) {
        this.getChildren().add(gameBg);

        this.getChildren().add(model.getMagicStone());

        this.getChildren().add(model.getPlayer());
        this.getChildren().add(model.getPlayer().getTranslocator());

        for (Shooter shooter : model.getShooters()) {
            this.getChildren().add(shooter);
        }
    }


}



