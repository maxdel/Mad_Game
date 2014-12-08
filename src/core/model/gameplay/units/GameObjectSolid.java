package core.model.gameplay.units;

import core.resourcemanager.ResourceManager;
import org.newdawn.slick.geom.Shape;

/**
 * Contains common fields to define a solid game object
 * */
public abstract class GameObjectSolid extends GameObject {
    protected Shape mask;

    protected GameObjectSolidType type;


    public GameObjectSolid(double x, double y, double direction) {
        super(x, y, direction);
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