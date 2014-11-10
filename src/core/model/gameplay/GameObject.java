package core.model.gameplay;

/**
 * Contains common fields to define a game object
 * */
public abstract class GameObject {

    protected double x;
    protected double y;
    protected double direction;
    protected double currentSpeed;
    protected double maximumSpeed;

    public GameObject(final double x, final double y, final double direction, final double maximumSpeed) {
        this(x, y, direction, maximumSpeed, 0);
    }

    public GameObject(final double x, final double y, final double direction, final double maximumSpeed,
                      final double currentSpeed) {
        this.x = x;
        this.y = y;
        this.direction = direction;

        this.maximumSpeed = maximumSpeed;
        this.currentSpeed = currentSpeed;
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

    public double getMaximumSpeed() {
        return maximumSpeed;
    }

    public void setMaximumSpeed(double maximumSpeed) {
        this.maximumSpeed = maximumSpeed;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(double currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

}