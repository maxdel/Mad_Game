package core.model.gameplay.skills;

import core.MathAdv;
import core.model.gameplay.World;
import core.model.gameplay.gameobjects.GameObjInstanceKind;
import core.model.gameplay.gameobjects.Unit;
import core.model.gameplay.items.*;

public class BulletShot extends Skill {

    private double bulletSpeed;
    private double pAttack;
    private double mAttack;
    private double distance;

    public BulletShot(String name, String description, int castTime, int postCastTime, int cooldownTime,
                      ItemInstanceKind requiredItemKind, double requiredHP, double requiredMP, double bulletSpeed,
                      double pAttack, double mAttack, double distance, SkillInstanceKind kind) {
        super(name, description, castTime, postCastTime, cooldownTime, requiredItemKind, requiredHP, requiredMP, kind);
        this.bulletSpeed = bulletSpeed;
        this.pAttack = pAttack;
        this.mAttack = mAttack;
        this.distance = distance;
    }

    /**
     * Detect the type of bullet to create and creates this bullet.
     * After this the bullet lives her life
     */
    @Override
    protected void apply(Unit owner) {
        switch (kind) {
            case BOW_SHOT:
                World.getInstance().getGameObjectToAddList().add(new core.model.gameplay.gameobjects.Bullet(owner, owner.getX(), owner.getY(), owner.getDirection(),
                        bulletSpeed, pAttack, mAttack, distance, GameObjInstanceKind.ARROW));
                owner.getInventory().deleteItem(ItemInstanceKind.ARROW, 1);
                break;
            case FIREBALL:
                World.getInstance().getGameObjectToAddList().add(new core.model.gameplay.gameobjects.Bullet(owner, owner.getX(), owner.getY(), owner.getDirection(),
                        bulletSpeed, pAttack, mAttack, distance, GameObjInstanceKind.FIREBALL));
                break;
            case THORN:
            case WEAK_THORN:
            case STRONG_THORN:
                int numberOfThorns = 4;
                for (int i = 0; i < numberOfThorns; ++i) {
                    double currentDirection = owner.getDirection() + 2 * Math.PI / numberOfThorns * i;
                    double lengthDirX = MathAdv.lengthDirX(currentDirection, owner.getMask().getBoundingCircleRadius());
                    double lengthDirY = MathAdv.lengthDirY(currentDirection, owner.getMask().getBoundingCircleRadius());
                    World.getInstance().getGameObjectToAddList().add(new core.model.gameplay.gameobjects.Bullet(owner,
                            owner.getX() + lengthDirX, owner.getY() + lengthDirY, currentDirection,
                            bulletSpeed, pAttack, mAttack, distance, GameObjInstanceKind.THORN));
                }
                break;
            case THROW_STONE:
                World.getInstance().getGameObjectToAddList().add(new core.model.gameplay.gameobjects.Bullet(owner, owner.getX(), owner.getY(), owner.getDirection(),
                        bulletSpeed, pAttack, mAttack, distance, GameObjInstanceKind.STONE));
                break;
        }
    }

    /* Getters and setters region */
    public double getBulletSpeed() {
        return bulletSpeed;
    }

}
