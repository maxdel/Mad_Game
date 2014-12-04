package core.model.gameplay.units;

import core.model.gameplay.resource_manager.ResourceManager;
import org.newdawn.slick.geom.Circle;

/**
 * Contains fields to define the wall state
 * */
public class Tree extends GameObjectSolid {

    public Tree(double x, double y, double direction) {
        super(x, y, direction);
        setMask(new Circle(0, 0, ResourceManager.getInstance().getMaskRadius("tree")));
    }

    @Override
    public void update(int delta) {

    }

}