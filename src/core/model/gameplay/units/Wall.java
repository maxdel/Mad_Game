package core.model.gameplay.units;

import core.resourcemanager.ResourceManager;
import org.newdawn.slick.geom.Rectangle;

/**
 * Contains fields to define the wall state
 * */
public class Wall extends Obstacle {

    public Wall(double x, double y, double direction) {
        super(x, y, direction);
        setMask(ResourceManager.getInstance().getMask("wall"));

        /*setMask(new Rectangle(0,
                0,
                ResourceManager.getInstance().getMaskWidth("wall"),
                ResourceManager.getInstance().getMaskHeight("wall")));*/
    }

    @Override
    public void update(int delta) {

    }

}