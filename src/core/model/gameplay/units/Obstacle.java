package core.model.gameplay.units;

import core.resourcemanager.ResourceManager;

public  class Obstacle extends GameObjectSolid {

    public Obstacle(double x, double y, double direction, GameObjectSolidType type) {
        super(x, y, direction);
        this.type = type;
    }

    @Override
    public void update(int delta) {

    }
}
