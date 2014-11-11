package core.model.gameplay;

public class GameObjectMoving extends GameObject {

    protected double currentSpeed;
    protected double maximumSpeed;
    protected double relativeDirection;

    public GameObjectMoving(final double x, final double y, final double direction, final double relativeDirection,
                            final double maximumSpeed, final double currentSpeed) {
        super(x, y, direction);

        this.relativeDirection = relativeDirection;
        this.maximumSpeed = maximumSpeed;
        this.currentSpeed = currentSpeed;
    }

    public GameObjectMoving(final double x, final double y, final double direction,
                            final double maximumSpeed) {
        this(x, y, direction, 0, maximumSpeed, 0);
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

    public double getRelativeDirection() {
        return relativeDirection;
    }

    public void setRelativeDirection(double relativeDirection) {
        this.relativeDirection = relativeDirection;
    }

}
