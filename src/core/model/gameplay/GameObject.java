package core.model.gameplay;

import org.newdawn.slick.geom.*;

/**
 * Contains common fields to define a game object
 * */
public abstract class GameObject {

    protected double x;
    protected double y;
    protected double direction;
    protected Shape mask;
    protected int width;
    protected int height;

    public GameObject(final double x, final double y, final double direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public Shape getMask() {
        return mask;
    }

    /*
    * Method clones existing shape (@param shape), and returns new moved (@param x and @param y) shape.
    * The sizes of shape is not changing, only location
    * */
    public Shape getMovedMask(Shape shape, double dx, double dy, double direction) {
        Shape temp;

        if (shape instanceof Circle) {
            temp = new Circle(shape.getCenterX() + (float) dx,
                    shape.getCenterY() + (float) dy,
                    shape.getBoundingCircleRadius());
        } else if (shape instanceof Rectangle) {
            temp = new Rectangle(shape.getX() + (float) dx,
                    shape.getY() + (float) dy,
                    shape.getWidth(), shape.getHeight());
            temp = temp.transform(Transform.createRotateTransform((float)(direction), temp.getCenterX(), temp.getCenterY()));
        } else {
            temp = null;
        }

        return temp;
    }

    /**
     * Check if this entity collides with another.
     *
     * @param other The other entity to check collision against
     * @return True if the entities collide with each other
     *
     * It's inner game world flow method, which have no connect with controller,
     * so it's GameObject entity method
     */
    public boolean collidesWith(GameObject other) {
        Shape me = getMovedMask(this.getMask(), this.x, this.y, this.direction);
        Shape him = getMovedMask(other.getMask(), other.x, other.y, other.direction);

        return me.intersects(him);
    }

    /**
     * Notification that this entity collided (already) (!) with another.
     * Determinate actions, that will be happened, when entities have collided
     *
     * It's inner game world flow method, which have no connection with controller,
     * so it's GameObject entity method
     *
     * @param other The entity with which this entity collided.
     */
    public abstract void collidedWith(GameObject other);

    public void setMaskSize(int width, int height) {
        this.width = width;
        this.height = height;

        formMask();
    }

    /*
    * Forms a mask of obj for collision detection
    */
    abstract protected void formMask();

    @Override
    public String toString() {
        return "GameObject{" +
                "x=" + x +
                ", y=" + y +
                ", direction=" + direction +
                ", mask=" + mask +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

}