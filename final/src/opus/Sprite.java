/**
 * Sprite.java
 * Ari Conati & Grant Lee
 * 22 May 2018
 *
 * The class that represents the sprites.
 */

 package opus;

 import javafx.animation.Animation;
 import javafx.embed.swing.SwingFXUtils;
 import javafx.geometry.Point2D;
 import javafx.geometry.Rectangle2D;
 import javafx.scene.Group;
 import javafx.scene.image.Image;
 import javafx.scene.image.ImageView;
 import javafx.util.Duration;

 import javax.imageio.ImageIO;
 import java.awt.image.BufferedImage;
 import java.io.File;
 import java.io.IOException;

public abstract class Sprite extends Group {
    private Point2D size;
    private String name;
    private Point2D velocity;
    private ImageView imageView;


    public Sprite() {}

    public final String getName() {
        return this.name;
    }

    public final void setName(String newName) {
        this.name = newName;
    }

    public ImageView getImageView() {
        return this.imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public final Point2D getPosition() {
        Point2D position = new Point2D(this.getLayoutX(), this.getLayoutY());
        return position;
    }

    public void setSize(double width, double height) {
        this.size = new Point2D(width, height);
    }

    public double getWidth() {
        return this.size.getX();
    }

    public double getHeight() {
        return this.size.getY();
    }

    public final void setPosition(double x, double y) {
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.imageView.setY(y);
        this.imageView.setX(x);
    }

    public final Point2D getVelocity() {
        return this.velocity;
    }

    public final void setVelocity(double vx, double vy) {
        this.velocity = new Point2D(vx, vy);
    }

    public ImageView makeImage(String file, double offsetX, double offsetY, double width, double height, int columns, int speed) {
        this.setSize(width, height);
        File pic = new File(file);
        BufferedImage buffImage = null;

        {
            try {
                buffImage = ImageIO.read(pic);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Image IMAGE = SwingFXUtils.toFXImage(buffImage, null);
        ImageView imageView = new ImageView(IMAGE);
        imageView.setViewport(new Rectangle2D(offsetX,offsetY,width,height));

        Animation animation = new SpriteAnimation(imageView, Duration.millis(speed), columns, columns, offsetX, offsetY, width, height);
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();

        return imageView;
    }


    public void step() {
        Point2D position = this.getPosition();
        this.setPosition(position.getX() + this.velocity.getX(), position.getY() + this.velocity.getY());
    }
}

