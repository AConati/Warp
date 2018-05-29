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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class Player extends Application {



    File pic = new File("src/res/topdownsmall.png");
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
    private static final int WIDTH    = 50;
    private static final int HEIGHT   = 80;

    //Down
    private static final int DownOffSetX =  0;
    private static final int DownOffSetY =  0;

    //Up
    private static final int UpOffSetX =  0;
    private static final int UpOffSetY =  80;

    //Right
    private static final int RightOffSetX =  0;
    private static final int RightOffSetY =  150;

    //Left
    private static final int LeftOffSetX =  0;
    private static final int LeftOffSetY =  230;





    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sonata");

        final ImageView imageView = new ImageView(IMAGE);
        imageView.setViewport(new Rectangle2D(DownOffSetX, DownOffSetY, WIDTH, HEIGHT));

        imageView.setX(100.0);
        imageView.setY(500.0);

        final Animation animation = new SpriteAnimation(
                imageView,
                Duration.millis(500),
                COUNT, COLUMNS,
                DownOffSetX, DownOffSetY,
                WIDTH, HEIGHT
        );
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();

        Shape rect = new Shape() {
            @Override
            public com.sun.javafx.geom.Shape impl_configShape() {
                return null;
            }
        };

        Collection<Node> pics = new ArrayList<>();
        pics.add(imageView);
        pics.add(rect);


        primaryStage.setScene(new Scene(new Group(imageView), 700, 700));
        primaryStage.show();




        System.out.print(imageView.getX() + " , " + imageView.getY());
    }
}

