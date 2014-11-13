package core.model.gameplay;

import org.newdawn.slick.geom.Shape;

/**
 * Contains common fields to define a game object
 * */
public abstract class GameObject {

    private double x;
    private double y;
    private double direction;
    private Shape mask;

    public GameObject(final double x, final double y, final double direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public abstract void collidedWith(Hero hero);
    public abstract void collidedWith(Enemy enemy);
    public abstract void collidedWith(Wall wall);

    public abstract void update(final int delta);

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public Shape getMask() {
        return mask;
    }

    public void setMask(Shape mask) {
        this.mask = mask;
    }

    @Override
    public String toString() {
        return "GameObject{" +
                "x=" + x +
                ", y=" + y +
                ", direction=" + direction +
                ", mask=" + mask +
                '}';
    }

}