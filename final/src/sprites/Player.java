/**
 * Player.java
 * Ari Conati & Grant Lee
 * 22 May 2018
 *
 * The class that represents the main player
 */

package sprites;

import javafx.animation.Animation;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends Application {



    File pic = new File("src/res/test.png");
    BufferedImage buffImage;

    {
        try {
            buffImage = ImageIO.read(pic);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Image IMAGE = SwingFXUtils.toFXImage(buffImage, null);



    private static final int COLUMNS  =  4;
    private static final int COUNT    =  4;
    private static final int OFFSET_X =  0;
    private static final int OFFSET_Y =  260;
    private static final int WIDTH    = 200;
    private static final int HEIGHT   = 200;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("The Horse in Motion");

        final ImageView imageView = new ImageView(IMAGE);
        imageView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));

        final Animation animation = new SpriteAnimation(
                imageView,
                Duration.millis(400),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        );
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();

        primaryStage.setScene(new Scene(new Group(imageView), 700, 700));
        primaryStage.show();
    }
}

