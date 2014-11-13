package core.model.gameplay;

import org.newdawn.slick.geom.Circle;

public class Enemy extends GameObjectMoving {

    public Enemy(final double x, final double y, final double direction, final double maximumSpeed) {
        super(x, y, direction, maximumSpeed);
        setMask(new Circle(0, 0, 42));
    }


    @Override
    public void update(int delta) {
        followTarget(World.getInstance(false).getHero().getX(), World.getInstance(false).getHero().getY(), delta);
        super.update(delta);
    }

    public void followTarget(final double x, final double y, final int delta) {
        double direction = Math.atan2(y - getY(), x - getX());
        double speed = getMaximumSpeed();
        setDirection(direction);
        if (CollisionManager.getInstance().isPlaceFree(this, getX() +
                lengthDirX(direction, speed * delta),getY() + lengthDirY(direction, speed * delta))) {
            run(speed);
        } else {
            stand();
        }
    }

    public void run(double speed) {
        setCurrentState(GameObjectState.RUN);
        setCurrentSpeed(speed);
    }

    public void stand() {
        setCurrentState(GameObjectState.STAND);
        setCurrentSpeed(0);
    }




}