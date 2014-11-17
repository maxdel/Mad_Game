package core.model.gameplay;

import org.newdawn.slick.geom.Vector2f;

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
    public void update(final int delta) {
        double length, direction, lengthDirX, lengthDirY;
        length = getCurrentSpeed() * delta;
        direction = getDirection() + getRelativeDirection();

        lengthDirX = lengthDirX(direction, length);
        lengthDirY = lengthDirY(direction, length);

        double targetX = getX() + lengthDirX;
        double targetY = getY() + lengthDirY;

        if (CollisionManager.getInstance().isPlaceFree(this, targetX, targetY)) {
            setX(getX() + lengthDirX);
            setY(getY() + lengthDirY);
        } else {
            double stepAngle = 10 * Math.PI / 180;
            double altDirection = direction;
            int i = -1;
            do {
                i++;
                altDirection += i * stepAngle * (i % 2 == 0 ? 1 : -1);
                lengthDirX = lengthDirX(altDirection, length * Math.cos(altDirection - direction));
                lengthDirY = lengthDirY(altDirection, length * Math.cos(altDirection - direction));
                if (CollisionManager.getInstance().isPlaceFree(this, getX() + lengthDirX, getY() + lengthDirY)) {
                    setX(getX() + lengthDirX);
                    setY(getY() + lengthDirY);
                    break;
                }
            } while (Math.abs(altDirection - direction) < Math.PI / 2);
        }
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