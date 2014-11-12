package core.model.gameplay;

import org.newdawn.slick.geom.MorphShape;
import org.newdawn.slick.geom.Rectangle;

/**
 * Contains fields to define the wall state
 * */
public class Wall extends GameObject {

    public Wall(double x, double y, double direction) {
        super(x, y, direction);
    }


    @Override
    public void collidedWith(GameObject other) {
        /*
        * TODO: it's debug code
        * */

         if (other instanceof Hero) {
            System.out.println("Collision WALL - HERO");
             x -= 70;
             y -= 70;

         } else if (other instanceof Enemy) {
            System.out.println("Collision WALL - ENEMY");
        }

//        ((GameObjectMoving) other).maximumSpeed -= ((GameObjectMoving) other).getMaximumSpeed();

    }

    @Override
    protected void formMask() {
        mask = new Rectangle(-width / 2 , -height / 2, width, height);

    }
}