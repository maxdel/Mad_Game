package core.model.gameplay.gameobjects;

import core.model.gameplay.World;
import core.resourcemanager.ResourceManager;

public class Obstacle extends GameObjectSolid {

    private double hp;
    private int lifeTime;
    private boolean isDestructible;

    public Obstacle(double x, double y, double direction, GameObjInstanceKind type, double hp, int lifeTime) {
        super(x, y, direction, type);
        this.mask = ResourceManager.getInstance().getObstacleInfo(type).getMask();
        this.hp = hp;
        this.lifeTime = lifeTime;
        this.isDestructible = true;
    }

    public Obstacle(double x, double y, double direction, GameObjInstanceKind type) {
        this(x, y, direction, type, 0, 0);
        this.isDestructible = false;
    }

    @Override
    public void update(int delta) {
        if (isDestructible) {
            if (hp <= 0) {
                World.getInstance().getGameObjectToDeleteList().add(this);
            } else {
                lifeTime -= delta;
                if (lifeTime <= 0) {
                    hp = 0;
                }
            }
        }
    }

    public boolean isDestructible() {
        return isDestructible;
    }

    public void changeHP(double hpDelta) {
        this.hp += hpDelta;
    }

}
