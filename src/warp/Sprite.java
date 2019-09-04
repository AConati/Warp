/**
 * Sprite.java
 * Ari Conati & Grant Lee
 * 22 May 2018
 *
 * The class that represents the classes which have sprites. All of these classes
 * extend this class.
 */

 package warp;

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
    private Animation animation;
    private double offSetX;
    private double offSetY;


    public Sprite() {}

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

    public double getOffSetX() {
        return this.offSetX;
    }

    public double getOffSetY() {
        return this.offSetY;
    }

    public void setPosition(double x, double y) {
        this.setLayoutX(x);
        this.setLayoutY(y);
    }

    public double getCenterX() {
        return this.getPosition().getX() + this.getWidth()/2;
    }

    public double getCenterY() {
        return this.getPosition().getY() + this.getHeight()/2;
    }

    public Point2D getCenter() {
        return new Point2D(this.getCenterX(), this.getCenterY());
    }

    public double getXOuter() {
        return this.getPosition().getX() + this.getWidth();
    }

    public double getYOuter() {
        return this.getPosition().getY() + this.getHeight();
    }

    public final Point2D getVelocity() {
        return this.velocity;
    }

    public final void setVelocity(double vx, double vy) {
        this.velocity = new Point2D(vx, vy);
    }

    /**
     * Creates the image that will be shown
     * @param file representing the image
     * @return Imageview which can be added to the group object
     */
    public ImageView makeImage(String file) {

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


        return imageView;
    }

    public void setOffsets(double offsetX, double offsetY) {
        this.offSetY = offsetY;
        this.offSetX = offsetX;
    }

    public void setViewport(double width, double height) {
        this.setSize(width, height);
        this.imageView.setImage(imageView.getImage());
        this.imageView.setViewport(new Rectangle2D(this.getOffSetX(), this.getOffSetY(), width, height));

    }

    /**
     * Method that adds flips through the desired section of the sprite sheet
     * @param columns the number of columns the sprite sheet contains
     * @param speed the speed at which the images are flipped through
     */
    public void makeAnimation(int columns, int speed) {
        this.animation = new SpriteAnimation(imageView, Duration.millis(speed), columns, columns, this.getOffSetX(), this.getOffSetY(), this.getWidth(), this.getHeight());
        animation.setCycleCount(Animation.INDEFINITE);
    }

    public Animation getAnimation() {
        return animation;
    }

    public void step() {
        Point2D position = this.getPosition();
        this.setPosition(position.getX() + this.velocity.getX(), position.getY() + this.velocity.getY());
    }
}

