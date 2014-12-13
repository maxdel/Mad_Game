package core.model.gameplay.gameobjects;

import core.resourcemanager.ResourceManager;

public class Obstacle extends GameObjectSolid {

    public Obstacle(double x, double y, double direction, GameObjInstanceKind type) {
        super(x, y, direction, type);
        this.mask = ResourceManager.getInstance().getObstacleInfo(type).getMask();
    }

    @Override
    public void update(int delta) {

    }

}
