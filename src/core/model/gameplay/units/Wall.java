package core.model.gameplay.units;

import core.resource_manager.ResourceManager;
import org.newdawn.slick.geom.Rectangle;

/**
 * Contains fields to define the wall state
 * */
public class Wall extends GameObjectSolid {

    public Wall(double x, double y, double direction) {
        super(x, y, direction);
        setMask(new Rectangle(- ResourceManager.getInstance().getMaskWidth("wall") / 2,
                - ResourceManager.getInstance().getMaskHeight("wall") / 2,
                ResourceManager.getInstance().getMaskWidth("wall"),
                ResourceManager.getInstance().getMaskHeight("wall")));
    }

    @Override
    public void update(int delta) {

    }

}