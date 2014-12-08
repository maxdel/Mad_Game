package core.model.gameplay.units;

import core.resourcemanager.ResourceManager;
import org.newdawn.slick.geom.Shape;

public  class Obstacle extends GameObjectSolid {

    public Obstacle(double x, double y, double direction, GameObjectSolidType type) {
        super(x, y, direction);
        this.type = type;
        this.mask = ResourceManager.getInstance().getMask(type);
    }

    @Override
    public void update(int delta) {

    }
}
