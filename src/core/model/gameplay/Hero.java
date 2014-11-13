package core.model.gameplay;

import org.newdawn.slick.geom.Circle;

/**
 * Contains fields to define the hero state
 * */
public class Hero extends GameObjectMoving {

/*
    public Hero(final double x, final double y, final double direction, final double relativeDirection, final double maximumSpeed,
                final double currentSpeed, final GameObjectState currentState) {
        super(x, y, direction, relativeDirection, maximumSpeed, currentSpeed, currentState);
    }
*/
    public Hero(final double x, final double y, final double direction, final double maximumSpeed) {
        super(x, y, direction, maximumSpeed);
        setMask(new Circle(0, 0, 20));
    }


    public void walk(final double direction) {
        setCurrentState(GameObjectState.WALK);
        setCurrentSpeed(getMaximumSpeed() / 2);
        setRelativeDirection(direction);
    }

    public void run(final double direction) {
        setCurrentState(GameObjectState.RUN);
        setCurrentSpeed(getMaximumSpeed());
        setRelativeDirection(direction);
    }

    public void stand() {
        setCurrentState(GameObjectState.STAND);
        setCurrentSpeed(0);
    }

    public void rotate(final double angleOffset) {
        setDirection(getDirection() + angleOffset);
    }

}