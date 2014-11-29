package core.model.gameplay;

import core.ResourceManager;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

public class Enemy extends GameObjectMoving {

    private int timer;
    private double targetX;
    private double targetY;
    private boolean isTargetHero;

    public Enemy(double x, double y, double maximumSpeed) {
        super(x, y, maximumSpeed);
        setMask(new Circle(0, 0, ResourceManager.getInstance().getMaskRadius("enemy")));
        timer = (int) (Math.random() * 1000);

        skillList.add(new AreaSkill(this, "Sword attack", 200, 1000, "Sword", 0, 3, 15, 0, 100, Math.PI / 2));
        inventory.useItem(inventory.addItem("Sword"));

        isTargetHero = false;
    }

    public void followTarget(double x, double y) {
        double direction = Math.atan2(y - getY(), x - getX());
        setDirection(direction);
        run(0);
    }

    public void run(double direction) {
        if (getCurrentState() == GameObjectState.STAND || getCurrentState() == GameObjectState.WALK ||
                getCurrentState() == GameObjectState.RUN) {
            setCurrentState(GameObjectState.RUN);
            getAttribute().setCurrentSpeed(getAttribute().getMaximumSpeed());
            setRelativeDirection(direction);
        }
    }

    public void stand() {
        if (getCurrentState() == GameObjectState.RUN || getCurrentState() == GameObjectState.WALK) {
            setCurrentState(GameObjectState.STAND);
            getAttribute().setCurrentSpeed(0);
        }
    }
    
    @Override
    public void update(int delta) {
        Vector2f v = new Vector2f((float)World.getInstance().getHero().getX() - (float)getX(),
                (float)World.getInstance().getHero().getY() - (float)getY());
        if (v.length() < 300) {
            targetX = World.getInstance().getHero().getX();
            targetY = World.getInstance().getHero().getY();
            isTargetHero = true;
        } else {
            isTargetHero = false;
            if (timer <= 0) {
                double distance = 100 + Math.random() * 50;
                double angle = Math.random() * 2 * Math.PI;
                double tmpX = getX() + lengthDirX(angle, distance);
                double tmpY = getY() + lengthDirY(angle, distance);
                if (CollisionManager.getInstance().isPlaceFreeAdv(this, tmpX, tmpY)) {
                    targetX = tmpX;
                    targetY = tmpY;
                    timer = 1500 + (int) (Math.random() * 1000);
                }
            } else {
                timer -= delta;
            }
        }
        Vector2f v2 = new Vector2f((float)(targetX - getX()), (float)(targetY - getY()));
        if (v2.length() < 3) {
            setX(targetX);
            setY(targetY);
        } else {
            followTarget(targetX, targetY);
            // TODO Fix magic numbers
            if (isTargetHero && v2.length() < 100) {
                startCastSkill(0);
            }
        }
        super.update(delta);
    }

}