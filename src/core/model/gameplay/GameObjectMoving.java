package core.model.gameplay;

public class GameObjectMoving extends GameObject {

    private double currentSpeed;
    private double maximumSpeed;
    private double relativeDirection;
    private GameObjectState currentState;

    public GameObjectMoving(final double x, final double y, final double direction, final double relativeDirection,
                            final double maximumSpeed, final double currentSpeed, final GameObjectState currentState) {
        super(x, y, direction);

        this.relativeDirection = relativeDirection;
        this.maximumSpeed = maximumSpeed;
        this.currentSpeed = currentSpeed;
        this.currentState = currentState;
    }

    public GameObjectMoving(final double x, final double y, final double direction,
                            final double maximumSpeed) {
        this(x, y, direction, 0, maximumSpeed, 0, GameObjectState.STAND);
    }

    @Override
    public void collidedWith(Hero hero) {

    }

    @Override
    public void collidedWith(Enemy enemy) {

    }

    @Override
    public void collidedWith(Wall wall) {

    }

    @Override
    public void update(final int delta) {
        double length, lengthDirX, lengthDirY;
        length = getCurrentSpeed() * delta;

        lengthDirX = lengthDirX(getDirection() + getRelativeDirection(), length);
        lengthDirY = lengthDirY(getDirection() + getRelativeDirection(), length);

        setX(getX() + lengthDirX);
        setY(getY() + lengthDirY);
    }

    protected double lengthDirX(double direction, double length) {
        return Math.cos(direction) * length;
    }

    protected double lengthDirY(double direction, double length) {
        return Math.sin(direction) * length;
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

    public GameObjectState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameObjectState currentState) {
        this.currentState = currentState;
    }

}