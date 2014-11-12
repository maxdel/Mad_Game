package core.model.gameplay;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.MorphShape;

public class GameObjectMoving extends GameObject {
public abstract class GameObjectMoving extends GameObject {

    protected double currentSpeed;
    protected double maximumSpeed;
    protected double relativeDirection;
    protected GameObjectState currentState;

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
    public void collidedWith(GameObject other) {

    }

    @Override
    protected void formMask() {
        /*
        * TODO: decide how to form radius
        * e. g.: radius width > height ? width : height;
        * */

          int radius = (int) ((width + height) / 3.0);
          mask = new Circle(radius, radius, radius);


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
