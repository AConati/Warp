/**
 * StartScreenView.java
 * Ari Conati & Grant Lee
 * 22 May 2018
 *
 * The view for the starting screen
 * Should be responsible for the following:
 * (1); Set the size and dimensions of the scene and stage
 * (2); Add the components and sprites that will be involved in the screen
 */

package warp;

import javafx.scene.Group;

public class StartScreenView extends Group {

    public final double FRAME_HEIGHT = 600;
    public final double FRAME_WIDTH = 800;

    Selector selector = new Selector();
    StartScreenBg startScreen = new StartScreenBg();

    public StartScreenView() {
    }

    public void initialize() {
        //initialize screen background
        startScreen.setPosition(0,0);
        startScreen.setOffsets(450, 50);
        startScreen.setViewport(FRAME_WIDTH,FRAME_HEIGHT);
        this.getChildren().add(startScreen);

        //initialize selector
        selector.setPosition(250,294);
        this.getChildren().add(selector);
        this.startScreen.playMusic();

    }


}
