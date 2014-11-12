package core.model.gameplay;

public class Enemy extends GameObjectMoving {

    public Enemy(final double x, final double y, final double direction, final double relativeDirection,
                 final double maximumSpeed, final double currentSpeed, final GameObjectState currentState) {
        super(x, y, direction, relativeDirection, maximumSpeed, currentSpeed, currentState);
    }

    public Enemy(final double x, final double y, final double direction, final double maximumSpeed) {
        super(x, y, direction, maximumSpeed);
    }

    @Override
    public void collidedWith(GameObject other) {
        /*
        * TODO: it's debug code
        * */
        if (other instanceof Hero) {

            System.out.println("Collision ENEMY - HERO");
        } else if (other instanceof Wall) {
            System.out.println("Collision ENEMY - WALL");
        } else if (other instanceof Enemy) {
            System.out.println("Collision ENEMY - ENEMY");

        }

        maximumSpeed = -maximumSpeed;
    }

}