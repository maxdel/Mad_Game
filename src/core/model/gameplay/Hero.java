package core.model.gameplay;

/**
 * Contains fields to define the hero state
 * */
public class Hero extends GameObjectMoving {

    public Hero(final double x, final double y, final double direction, final double relativeDirection, final double maximumSpeed,
                final double currentSpeed, final GameObjectState currentState) {
        super(x, y, direction, relativeDirection, maximumSpeed, currentSpeed, currentState);
    }

    public Hero(final double x, final double y, final double direction, final double maximumSpeed) {
        super(x, y, direction, maximumSpeed);
    }

    @Override
    public void collidedWith(GameObject other) {
        /*
        * TODO: it's debug code
        * */
        if (other instanceof Wall) {
            //System.out.println("Collision HERO - WALL!");

        } else if (other instanceof Enemy) {
            //System.out.println("Collision HERO - ENEMY");

        }

    }

}