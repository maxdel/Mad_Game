package core.model.gameplay;

import org.newdawn.slick.geom.Rectangle;

/**
 * Contains fields to define the wall state
 * */
public class Wall extends GameObject {

    public Wall(double x, double y, double direction) {
        super(x, y, direction);
        setMask(new Rectangle(-32, -32, 64, 64));
    }


}