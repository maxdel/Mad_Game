package core.model.gameplay.skills;

import core.model.gameplay.World;
import core.model.gameplay.items.Item;
import core.model.gameplay.items.ItemDB;
import core.model.gameplay.units.GameObjectMoving;
import core.model.gameplay.units.GameObjectSolid;
import org.newdawn.slick.geom.Vector2f;

public class AreaSkill extends Skill {

    private double pAttack;
    private double mAttack;

    private double radius;
    private double angle;

    public AreaSkill(GameObjectMoving owner, String name, String description, int castTime, int cooldownTime, String requiredItem,
                     double requiredHP, double requiredMP, double pAttack, double mAttack, double radius, double angle) {
        super(owner, name, description, castTime, cooldownTime, requiredItem, requiredHP, requiredMP);
        this.pAttack = pAttack;
        this.mAttack = mAttack;
        this.radius = radius;
        this.angle = angle;
    }

    @Override
    public void cast() {
        for (GameObjectSolid gameObjectSolid : World.getInstance().getGameObjectSolids()) {
            if (gameObjectSolid instanceof GameObjectMoving && gameObjectSolid != owner) {
                GameObjectMoving target = (GameObjectMoving) gameObjectSolid;

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
                        target.getAttribute().getHP().damage(pDamage);
                    }
                    if (mAttack > 0) {
                        double mDamage = mAttack + owner.getAttribute().getMAttack() - target.getAttribute().getMArmor();
                        if (mDamage <= 1) {
                            mDamage = 1;
                        }
                        target.getAttribute().getHP().damage(mDamage);
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
