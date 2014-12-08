package core.model.gameplay.units;

import core.resourcemanager.ResourceManager;
import org.newdawn.slick.geom.Circle;

/**
 * Contains fields to define the wall state
 * */
public class Tree extends Obstacle {

    public Tree(double x, double y, double direction) {
        super(x, y, direction);
        setMask(ResourceManager.getInstance().getMask("tree"));
    }

    @Override
    public void update(int delta) {

    }

}