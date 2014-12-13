package core.model.gameplay.gameobjects;

import core.resourcemanager.ResourceManager;
import org.newdawn.slick.geom.Shape;

/**
 * Contains common fields to define a solid game object
 * */
public abstract class GameObjectSolid extends GameObject {

    protected Shape mask;

    public GameObjectSolid(double x, double y, double direction, GameObjectType type) {
        super(x, y, direction, type);
        this.mask = null;
    }

    public Shape getMask() {
        return mask;
    }

}