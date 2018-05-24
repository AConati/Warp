/**
 * Arena.java
 * Ari Conati & Grant Lee
 * 22 May 2018
 *
 * The area that all action takes place
 */

package sprites;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class Arena extends Application {
    private ArrayList<Sprite> spriteList;


    final private double SCENE_WIDTH = 500;
    final private double SCENE_HEIGHT = 400;
    final private double FRAMES_PER_SECOND = 20.0;

    @Override
    public void start(Stage primaryStage) {
        // Since this application will be multi-threaded, we want to make
        // sure to terminate all the threads when the user closes the single window.
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

        // Set up the Stage.
        Group root = new Group();
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        this.spriteList = new ArrayList<Sprite>();

        //Add Sprites Here...

    }
}
