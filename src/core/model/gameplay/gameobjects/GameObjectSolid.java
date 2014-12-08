package core.model.gameplay.gameobjects;

import core.resourcemanager.ResourceManager;
import org.newdawn.slick.geom.Shape;

/**
 * Contains common fields to define a solid game object
 * */
public abstract class GameObjectSolid extends GameObject {
    protected Shape mask;

    protected GameObjectSolidType type;


    public GameObjectSolid(double x, double y, double direction, GameObjectSolidType type) {
        super(x, y, direction);
        this.type = type;
        this.mask = ResourceManager.getInstance().getMask(type);
    }

    public Shape getMask() {
        return mask;
    }

    public void setMask(Shape mask) {
        this.mask = mask;
    }

    public GameObjectSolidType getType() {
        return type;
    }


    public abstract void update(int delta);
}