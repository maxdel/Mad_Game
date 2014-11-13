package core.model;

import org.newdawn.slick.geom.Rectangle;

/**
 * Contains fields to define the wall state
 * */
public class Wall extends GameObject {

    public Wall(double x, double y, double direction) {
        super(x, y, direction);
        setMask(new Rectangle(-32, -32, 64, 64));
    }

    @Override
    public void update(int delta) {
        /* some code like:  wall.method(...); */
    }

    @Override
    public void collidedWith(Hero other) {
        setX(getX() + 30);
        setY(getY() + 30);
    }

    @Override
    public void collidedWith(Enemy other) {
        //
    }

    @Override
    public void collidedWith(Wall wall) {

    }


}