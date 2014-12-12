package core.model.gameplay.skills;

import core.model.Timer;
import core.model.gameplay.World;
import core.model.gameplay.gameobjects.GameObjectSolidType;
import core.model.gameplay.gameobjects.Unit;
import core.model.gameplay.items.ItemDB;

public class BulletShot extends Skill {

    private double bulletSpeed;
    private double pAttack;
    private double mAttack;
    private Timer cooldownTimer = new Timer();

    public BulletShot(Unit owner, String name, String description, int castTime, int postCastTime, int cooldownTime, String requiredItem,
                      double requiredHP, double requiredMP, double bulletSpeed, double pAttack, double mAttack) {
        super(owner, name, description, castTime, postCastTime, cooldownTime, requiredItem, requiredHP, requiredMP);
        this.bulletSpeed = bulletSpeed;
        this.pAttack = pAttack;
        this.mAttack = mAttack;
    }

    // @Override
    public boolean startCast() {
        if (canStartCast(true)) {
            if ((requiredItem == ItemDB.getInstance().getItem("Bow")
                    && owner.getInventory().isItemExists(ItemDB.getInstance().getItem("Arrow")))
                    || requiredItem == ItemDB.getInstance().getItem("Staff")) {
                decreasePointsCost();
                // runCast();
                cooldownTimer.activate(castTime);

                return true;
            }
        }

        return false;
    }

    @Override
    public void apply() {
        if (requiredItem == ItemDB.getInstance().getItem("Bow")) {
            World.getInstance().getToAddList().add(new core.model.gameplay.gameobjects.Bullet(owner, owner.getX(), owner.getY(), owner.getDirection(),
                    bulletSpeed, pAttack, mAttack, GameObjectSolidType.ARROW));
            owner.getInventory().deleteItem("Arrow", 1);
        } else if (requiredItem == ItemDB.getInstance().getItem("Staff")) {
            World.getInstance().getToAddList().add(new core.model.gameplay.gameobjects.Bullet(owner, owner.getX(), owner.getY(), owner.getDirection(),
                    bulletSpeed, pAttack, mAttack, GameObjectSolidType.FIREBALL));
        }
    }

    public double getBulletSpeed() {
        return bulletSpeed;
    }

}
