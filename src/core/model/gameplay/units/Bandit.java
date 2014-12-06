package core.model.gameplay.units;

import core.resource_manager.ResourceManager;
import core.model.gameplay.*;
import core.model.gameplay.items.ItemDB;
import core.model.gameplay.skills.AreaSkill;
import core.model.gameplay.items.Loot;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

public class Bandit extends GameObjectMoving {

    private int timer;
    private double targetX;
    private double targetY;
    private boolean isTargetHero;

    public Bandit(double x, double y, double maximumSpeed) {
        super(x, y, maximumSpeed);
        setMask(new Circle(0, 0, ResourceManager.getInstance().getMaskRadius("bandit")));
        timer = (int) (Math.random() * 1000);

        skillList.add(ResourceManager.getInstance().getSkill(this, "Sword attack"));
        inventory.useItem(inventory.addItem("Sword"));
        inventory.useItem(inventory.addItem("Light armor"));

        getAttribute().resetHpMp(40, 40);

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
        Vector2f v = new Vector2f((float) World.getInstance().getHero().getX() - (float)getX(),
                (float)World.getInstance().getHero().getY() - (float)getY());

        double distanceToHero = v.length();
        double followHeroDistance = 300;
        double attackHeroDistance = 50;

        if (!isTargetHero) {
            if (distanceToHero < attackHeroDistance) {
                stand();
                setDirection(v.getTheta() / 180 * Math.PI);
                startCastSkill(0);
            } else if (distanceToHero < followHeroDistance) {
                targetX = World.getInstance().getHero().getX();
                targetY = World.getInstance().getHero().getY();
                followTarget(targetX, targetY);
            } else {
                if (timer <= 0) {
                    double distance = 30 + Math.random() * 10;
                    double angle = Math.random() * 2 * Math.PI;
                    double tmpX = getX() + lengthDirX(angle, distance);
                    double tmpY = getY() + lengthDirY(angle, distance);
                    if (CollisionManager.getInstance().isPlaceFreeAdv(this, tmpX, tmpY)) {
                        targetX = tmpX;
                        targetY = tmpY;
                        timer = 5000 + (int) (Math.random() * 10000);
                    }
                } else {
                    timer -= delta;
                }
                Vector2f v2 = new Vector2f((float)targetX - (float)getX(),
                        (float)targetY - (float)getY());
                if (v2.length() > 0) {
                    followTarget(targetX, targetY);
                }
            }
        } else {
            if (distanceToHero < attackHeroDistance) {
                stand();
                setDirection(v.getTheta() / 180 * Math.PI);
                startCastSkill(0);
            } else {
                targetX = World.getInstance().getHero().getX();
                targetY = World.getInstance().getHero().getY();
                followTarget(targetX, targetY);
            }
        }

        Vector2f v2 = new Vector2f((float)targetX - (float)getX(),
                (float)targetY - (float)getY());

        if (v2.length() < 3) {
            setX(targetX);
            setY(targetY);
            stand();
        }

        super.update(delta);
    }

    @Override
    protected void onDelete() {
        if (Math.random() < 0.8) {
            World.getInstance().getLootList().add(new Loot(getX() - 10 + Math.random() * 20,
                    getY() - 10 + Math.random() * 20, Math.random() * 2 * Math.PI, ItemDB.getInstance().getItem("Apple"), 1));
        }
        if (Math.random() < 0.6) {
            World.getInstance().getLootList().add(new Loot(getX() - 10 + Math.random() * 20,
                    getY() - 10 + Math.random() * 20, Math.random() * 2 * Math.PI, ItemDB.getInstance().getItem("Healing flask"), 1));
        }
        if (Math.random() < 0.4) {
            World.getInstance().getLootList().add(new Loot(getX() - 10 + Math.random() * 20,
                    getY() - 10 + Math.random() * 20, Math.random() * 2 * Math.PI, ItemDB.getInstance().getItem("Mana flask"), 1));
        }
        if (Math.random() < 0.2) {
            World.getInstance().getLootList().add(new Loot(getX() - 10 + Math.random() * 20,
                    getY() - 10 + Math.random() * 20, Math.random() * 2 * Math.PI, ItemDB.getInstance().getItem("Light armor"), 1));
        }
        if (Math.random() < 0.2) {
            World.getInstance().getLootList().add(new Loot(getX() - 10 + Math.random() * 20,
                    getY() - 10 + Math.random() * 20, Math.random() * 2 * Math.PI, ItemDB.getInstance().getItem("Staff"), 1));
        }
        if (Math.random() < 0.7) {
            World.getInstance().getLootList().add(new Loot(getX() - 10 + Math.random() * 20,
                    getY() - 10 + Math.random() * 20, Math.random() * 2 * Math.PI, ItemDB.getInstance().getItem("Arrow"),
                    (int)(6 + Math.random() * 10)));
        }
        // TODO for fun
        World.getInstance().getHero().kills++;
    }

    public void setTargetHero(boolean isTargetHero) {
        this.isTargetHero = isTargetHero;
    }

}