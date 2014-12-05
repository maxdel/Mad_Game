package core.model.gameplay.skills;

import core.model.gameplay.World;
import core.model.gameplay.items.Item;
import core.model.gameplay.items.ItemDB;
import core.model.gameplay.skills.bullets.Arrow;
import core.model.gameplay.skills.bullets.Fireball;
import core.model.gameplay.units.GameObjectMoving;

public class BulletSkill extends Skill {

    private double bulletSpeed;
    private double pAttack;
    private double mAttack;

    public BulletSkill(GameObjectMoving owner, String name, String description, int castTime, int cooldownTime, String requiredItem,
                       double requiredHP, double requiredMP, double bulletSpeed, double pAttack, double mAttack) {
        super(owner, name, description, castTime, cooldownTime, requiredItem, requiredHP, requiredMP);
        this.bulletSpeed = bulletSpeed;
        this.pAttack = pAttack;
        this.mAttack = mAttack;
    }


    @Override
    public boolean startCast() {
        if (canStartCast(true)) {
            if ((requiredItem == ItemDB.getInstance().getItem("Bow")
                    && owner.getInventory().isItemExists(ItemDB.getInstance().getItem("Arrow")))
                    || requiredItem == ItemDB.getInstance().getItem("Staff")) {
                decreasePointsCost(requiredMP, requiredHP);
                runCast();
                runCD();
                return true;
            }
        }

        return false;
    }

    @Override
    public void cast() {
        if (requiredItem == ItemDB.getInstance().getItem("Bow")) {
            World.getInstance().getToAddList().add(new Arrow(owner, owner.getX(), owner.getY(), owner.getDirection(),
                    bulletSpeed, pAttack, mAttack));
            owner.getInventory().deleteItem("Arrow", 1);
        } else if (requiredItem == ItemDB.getInstance().getItem("Staff")) {
            World.getInstance().getToAddList().add(new Fireball(owner, owner.getX(), owner.getY(), owner.getDirection(),
                    bulletSpeed, pAttack, mAttack));
        }
    }
}