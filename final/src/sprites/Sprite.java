/**
 *
 * The sprite superclass for JavaFX sprites.
 *
 */

 package sprites;

 import javafx.geometry.Point2D;
 import javafx.scene.Group;

 public abstract class Sprite extends Group {
    private Point2D size;
    private String name;
    private Point2D velocity;

    public Sprite() {}

    public final String getName() {
        return this.name;
    }

    public final void setName(String newName) {
        this.name = newName;
    }

    public final Point2D getPosition() {
        Point2D position = new Point2D(this.getLayoutX(), this.getLayoutY());
        return postiion;
    }

    public final void setPostiion(double x, double y) {
        this.setLayoutX(x);
        this.setLayoutY(y);
    }

    public final Point2D getVelocity() {
        return this.velocity;
    }

    public final void setVelocity(double vx, double vy) {
        this.velocity = new Point2D(vx, vy);
    }

    public void setSize(double width, double height) {
        this.size = new Point2D(width, height);
    }

    public void step() {
        Point2D position = this.getPosition();
        this.setPosition(position.getX() + this.velocity.getX(), position.getY() + this.velocity.getY());
    }
}

