package core.model.gameplay;

import core.view.ResourceManager;
import org.newdawn.slick.geom.Rectangle;

/**
 * Contains fields to define the wall state
 * */
public class Wall extends GameObject {

    public Wall(double x, double y, double direction) {
        super(x, y, direction);
        setMask(new Rectangle(- ResourceManager.getInstance().getMaskWidth("wall") / 2,
                - ResourceManager.getInstance().getMaskHeight("wall") / 2,
                ResourceManager.getInstance().getMaskWidth("wall"),
                ResourceManager.getInstance().getMaskHeight("wall")));
    }

    @Override
    public void update(int delta) {
        if (CollisionManager.getInstance().isCollidesWith(this, Hero.class, this.getX(), this.getY())) {
            setX(getX() + 30);
            setY(getY() + 30);
        }
    }

    @Override
    public void collidedWith(Hero other) {
        /*setX(getX() + 30);
        setY(getY() + 30);*/
    }

    @Override
    public void collidedWith(Enemy other) {
        //
    }

    @Override
    public void collidedWith(Wall wall) {

    }

}