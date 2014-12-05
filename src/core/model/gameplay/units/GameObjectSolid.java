package core.model.gameplay.units;

import org.newdawn.slick.geom.Shape;

/**
 * Contains common fields to define a game object
 * */
public abstract class GameObjectSolid extends GameObject {

    private Shape mask;

    public GameObjectSolid(double x, double y, double direction, Shape mask) {
        super(x, y, direction);
        this.mask = mask;
    }

    public GameObjectSolid(double x, double y, double direction) {
        super(x, y, direction);
    }

    public Shape getMask() {
        return mask;
    }

    public void setMask(Shape mask) {
        this.mask = mask;
    }

    public abstract void update(int delta);
}