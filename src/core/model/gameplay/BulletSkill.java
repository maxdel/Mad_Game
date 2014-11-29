package core.model.gameplay;

import org.newdawn.slick.geom.Vector2f;

public class BulletSkill extends Skill {

    private double bulletSpeed;
    private double pAttack;
    private double mAttack;

    public BulletSkill(GameObjectMoving owner, String name, int castTime, int cooldownTime, String requiredItem,
                       double requiredHP, double requiredMP, double bulletSpeed, double pAttack, double mAttack) {
        super(owner, name, castTime, cooldownTime, requiredItem, requiredHP, requiredMP);
        this.bulletSpeed = bulletSpeed;
        this.pAttack = pAttack;
        this.mAttack = mAttack;
    }

    @Override
    protected boolean startCast() {
        if (owner.getInventory().isItemDressed(requiredItem) &&
                owner.getAttribute().getCurrentMP() >= requiredMP &&
                owner.getAttribute().getCurrentHP() >= requiredHP &&
                currentCooldownTime == 0) {
            if (requiredItem.equals("Bow")) {
                if (owner.getInventory().isItemExists("Arrow")) {
                    currentCastTime = castTime;
                    owner.getAttribute().setCurrentMP(owner.getAttribute().getCurrentMP() - requiredMP);
                    owner.getAttribute().setCurrentHP(owner.getAttribute().getCurrentHP() - requiredHP);
                    currentCooldownTime = cooldownTime;
                    return true;
                }
            } else {
                currentCastTime = castTime;
                owner.getAttribute().setCurrentMP(owner.getAttribute().getCurrentMP() - requiredMP);
                owner.getAttribute().setCurrentHP(owner.getAttribute().getCurrentHP() - requiredHP);
                currentCooldownTime = cooldownTime;
                return true;
            }
        }
        return false;
    }

    @Override
    protected void cast() {
        if (requiredItem.equals("Bow")) {
            if (owner.getInventory().isItemExists("Arrow")) {
                World.getInstance().getToAddList().add(new Bullet(owner, owner.getX(), owner.getY(), owner.getDirection(),
                        bulletSpeed, pAttack, mAttack));
                owner.getInventory().deleteItem("Arrow", 1);
            }
        } else {
            World.getInstance().getToAddList().add(new Bullet(owner, owner.getX(), owner.getY(), owner.getDirection(),
                    bulletSpeed, pAttack, mAttack));
        }
    }

}