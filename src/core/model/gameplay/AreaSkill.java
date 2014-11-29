package core.model.gameplay;

import org.newdawn.slick.geom.Vector2f;

/**
 * Created by Alex on 29.11.2014.
 */
public class AreaSkill extends Skill {

    private double pAttack;
    private double mAttack;

    private double radius;
    private double angle;

    public AreaSkill(GameObjectMoving owner, String name, int castTime, int cooldownTime, String requiredItem,
                     double requiredHP, double requiredMP, double pAttack, double mAttack, double radius, double angle) {
        super(owner, name, castTime, cooldownTime, requiredItem, requiredHP, requiredMP);
        this.pAttack = pAttack;
        this.mAttack = mAttack;
        this.radius = radius;
        this.angle = angle;
    }

    @Override
    protected boolean startCast() {
        if (owner.getInventory().isItemDressed(requiredItem) &&
                owner.getAttribute().getCurrentMP() >= requiredMP &&
                owner.getAttribute().getCurrentHP() >= requiredHP &&
                currentCooldownTime == 0) {
            currentCastTime = castTime;
            owner.getAttribute().setCurrentMP(owner.getAttribute().getCurrentMP() - requiredMP);
            owner.getAttribute().setCurrentHP(owner.getAttribute().getCurrentHP() - requiredHP);
            currentCooldownTime = cooldownTime;
            return true;
        }
        return false;
    }

    @Override
    protected void cast() {
        for (GameObject gameObject : World.getInstance().getGameObjects()) {
            if (gameObject instanceof GameObjectMoving && gameObject != owner) {
                GameObjectMoving target = (GameObjectMoving) gameObject;

                Vector2f v1 = new Vector2f((float)lengthDirX(owner.getDirection(), angle),
                        (float)lengthDirY(owner.getDirection(), angle));
                Vector2f v2 = new Vector2f((float)(target.getX() - owner.getX()), (float)(target.getY() - owner.getY()));
                double angleBetweenObjects = Math.acos(v1.dot(v2) / (v1.length() * v2.length()));

                if (v2.length() - target.getMask().getBoundingCircleRadius() <= radius &&
                        Math.abs(angleBetweenObjects) < angle / 2) {
                    if (pAttack > 0) {
                        double pDamage = pAttack + owner.getAttribute().getPAttack() - target.getAttribute().getPArmor();
                        if (pDamage <= 1) {
                            pDamage = 1;
                        }
                        target.getAttribute().setCurrentHP(target.getAttribute().getCurrentHP() - pDamage);
                    }
                    if (mAttack > 0) {
                        double mDamage = mAttack + owner.getAttribute().getMAttack() - target.getAttribute().getMArmor();
                        if (mDamage <= 1) {
                            mDamage = 1;
                        }
                        target.getAttribute().setCurrentHP(target.getAttribute().getCurrentHP() - mDamage);
                    }
                }
            }
        }
    }

    protected double lengthDirX(double direction, double length) {
        return Math.cos(direction) * length;
    }

    protected double lengthDirY(double direction, double length) {
        return Math.sin(direction) * length;
    }

    public int getCurrentCastTime() {
        return currentCastTime;
    }

    public void setCurrentCastTime(int currentCastTime) {
        this.currentCastTime = currentCastTime;
    }

    public double getRadius() {
        return radius;
    }

}
