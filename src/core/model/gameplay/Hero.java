package core.model.gameplay;

/**
 * Contains fields to define the hero state
 * */
public class Hero extends GameObjectMoving {

    private GameObjectState currentState;

    public Hero(double x, double y, double direction, double maximumSpeed, double currentSpeed, double relativeDirection) {
        super(x, y, direction, maximumSpeed);
        this.currentSpeed = currentSpeed;
        this.relativeDirection = relativeDirection;
    }

    public Hero(final double x, final double y, final double direction, final double maximumSpeed) {
        this(x, y, direction, maximumSpeed, 0, 0);
    }

    public GameObjectState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameObjectState state) {
        this.currentState = state;
    }

}