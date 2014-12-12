package core.model.gameplay.skills;

import core.model.gameplay.World;
import core.model.gameplay.gameobjects.GameObjectSolidType;
import core.model.gameplay.gameobjects.Unit;
import core.model.gameplay.items.Bow;
import core.model.gameplay.items.Staff;

import java.security.acl.Owner;

public class BulletShot extends Skill {

    private double bulletSpeed;
    private double pAttack;
    private double mAttack;

    public BulletShot(String name, String description, int castTime, int postCastTime, int cooldownTime,
                      String requiredItem, double requiredHP, double requiredMP, double bulletSpeed,
                      double pAttack, double mAttack, SkillKind kind) {
        super(name, description, castTime, postCastTime, cooldownTime, requiredItem, requiredHP, requiredMP, kind);
        this.bulletSpeed = bulletSpeed;
        this.pAttack = pAttack;
        this.mAttack = mAttack;
    }

    /**
     * Detect the type of bullet to create and creates this bullet.
     * After this the bullet lives her life
     */
    @Override
    protected void apply(Unit owner) {
        if (requiredItem.getClass() == Bow.class) {
            World.getInstance().getToAddList().add(new core.model.gameplay.gameobjects.Bullet(owner, owner.getX(), owner.getY(), owner.getDirection(),
                    bulletSpeed, pAttack, mAttack, GameObjectSolidType.ARROW));
            owner.getInventory().deleteItem("Arrow", 1);
        } else if (requiredItem.getClass() == Staff.class) {
            World.getInstance().getToAddList().add(new core.model.gameplay.gameobjects.Bullet(owner, owner.getX(), owner.getY(), owner.getDirection(),
                    bulletSpeed, pAttack, mAttack, GameObjectSolidType.FIREBALL));
        }
    }

    public double getBulletSpeed() {
        return bulletSpeed;
    }

}
