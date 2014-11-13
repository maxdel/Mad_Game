package core.model.gameplay;

import org.newdawn.slick.geom.Circle;

public class Enemy extends GameObjectMoving {

    public Enemy(final double x, final double y, final double direction, final double relativeDirection,
                 final double maximumSpeed, final double currentSpeed, final GameObjectState currentState) {
        super(x, y, direction, relativeDirection, maximumSpeed, currentSpeed, currentState);
    }

    public Enemy(final double x, final double y, final double direction, final double maximumSpeed) {
        super(x, y, direction, maximumSpeed);
        setMask(new Circle(0, 0, 42));
    }

}