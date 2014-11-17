package core.model.gameplay;

import core.ResourceManager;
import org.newdawn.slick.geom.Circle;

public class Enemy extends GameObjectMoving {

    public Enemy(final double x, final double y, final double direction, final double maximumSpeed) {
        super(x, y, direction, maximumSpeed);
        setMask(new Circle(0, 0, ResourceManager.getInstance().getMaskRadius("enemy")));
    }

    public void followTarget(final double x, final double y) {
        double direction = Math.atan2(y - getY(), x - getX());
        setDirection(direction);
        run(0);
    }

    public void run(double direction) {
        setCurrentState(GameObjectState.RUN);
        setCurrentSpeed(getMaximumSpeed());
        setRelativeDirection(direction);
    }

    public void stand() {
        setCurrentState(GameObjectState.STAND);
        setCurrentSpeed(0);
    }
    
    @Override
    public void update(int delta) {
        followTarget(World.getInstance(false).getHero().getX(), World.getInstance(false).getHero().getY());
        super.update(delta);
    }

    @Override
    public void update(int delta) {
        followTarget(World.getInstance(false).getHero().getX(), World.getInstance(false).getHero().getY());
        super.update(delta);
    }

}