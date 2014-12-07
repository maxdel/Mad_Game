package core.model.gameplay.units;

import core.resource_manager.ResourceManager;
import core.model.gameplay.*;
import core.model.gameplay.items.ItemDB;
import core.model.gameplay.skills.BulletSkill;
import core.model.gameplay.items.Loot;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

public class Vampire extends GameObjectMoving {

    private VampireAI vampireAI;

    public Vampire(double x, double y, double maximumSpeed) {
        super(x, y, maximumSpeed);
        setMask(new Circle(0, 0, ResourceManager.getInstance().getMaskRadius("vampire")));

        skillList.add(ResourceManager.getInstance().getSkill(this, "Sword attack"));
        skillList.add(ResourceManager.getInstance().getSkill(this, "Fireball"));

        inventory.useItem(inventory.addItem("Light armor"));

        getAttribute().resetHpMp(1000, 1000);

        vampireAI = new VampireAI(this);
    }

    public void followTarget(double x, double y) {
        if (getCurrentState() != GameObjectState.CAST) {
            double direction = Math.atan2(y - getY(), x - getX());
            setDirection(direction);
            run(0);
        }
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
        vampireAI.run(delta);
        super.update(delta);
    }

    private double getPredictedDirection(double angleToTarget, double targetSpeed, double targetDirection,
                                         double bulletSpeed) {
        if (targetSpeed > 0) {
            double alphaAngle = (Math.PI - targetDirection) + angleToTarget;
            double neededOffsetAngle = Math.asin(Math.sin(alphaAngle) * targetSpeed / bulletSpeed);
            return angleToTarget + neededOffsetAngle;
        } else {
            return angleToTarget;
        }
    }

    public double getPredictedDirection(int skillIndex) {
        Vector2f v = new Vector2f((float) World.getInstance().getHero().getX() - (float)getX(),
            (float)World.getInstance().getHero().getY() - (float)getY());
        double angleToTarget = v.getTheta() / 180 * Math.PI;
        double targetSpeed = World.getInstance().getHero().getAttribute().getCurrentSpeed();
        double targetDirection = World.getInstance().getHero().getDirection() + World.getInstance().getHero().getRelativeDirection();
        double bulletSpeed = ((BulletSkill) skillList.get(skillIndex)).getBulletSpeed();

        if (targetSpeed > 0) {
            double alphaAngle = (Math.PI - targetDirection) + angleToTarget;
            double neededOffsetAngle = Math.asin(Math.sin(alphaAngle) * targetSpeed / bulletSpeed);
            return angleToTarget + neededOffsetAngle;
        } else {
            return angleToTarget;
        }
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
        if (Math.random() < 0.3) {
            World.getInstance().getLootList().add(new Loot(getX() - 10 + Math.random() * 20,
                    getY() - 10 + Math.random() * 20, Math.random() * 2 * Math.PI, ItemDB.getInstance().getItem("Bow"), 1));
        }
        if (Math.random() < 0.7) {
            World.getInstance().getLootList().add(new Loot(getX() - 10 + Math.random() * 20,
                    getY() - 10 + Math.random() * 20, Math.random() * 2 * Math.PI, ItemDB.getInstance().getItem("Arrow"),
                    (int)(6 + Math.random() * 10)));
        }
        // TODO for fun
        World.getInstance().getHero().kills++;
    }

    public VampireAI getVampireAI() {
        return vampireAI;
    }

}