package core.model.gameplay.skills;

import core.model.gameplay.World;
import core.model.gameplay.gameobjects.Unit;
import core.model.gameplay.gameobjects.GameObjectSolid;

import org.newdawn.slick.geom.Vector2f;

public class AreaSkill extends Skill {

    private double pAttack;
    private double mAttack;

    private double radius;
    private double angle;

    public AreaSkill(Unit owner, String name, String description, int castTime, int postCastTime,
                     int cooldownTime, String requiredItem, double requiredHP, double requiredMP,
                     double pAttack, double mAttack, double radius, double angle) {
        super(owner, name, description, castTime, postCastTime, cooldownTime, requiredItem, requiredHP, requiredMP);
        this.pAttack = pAttack;
        this.mAttack = mAttack;
        this.radius = radius;
        this.angle = angle;
    }

    @Override
    public void apply() {
        for (GameObjectSolid gameObjectSolid : World.getInstance().getGameObjectSolids()) {
            if (gameObjectSolid instanceof Unit && gameObjectSolid != owner) {
                Unit target = (Unit) gameObjectSolid;

                Vector2f v1 = new Vector2f((float)lengthDirX(owner.getDirection(), angle),
                        (float)lengthDirY(owner.getDirection(), angle));
                Vector2f v2 = new Vector2f((float)(target.getX() - owner.getX()), (float)(target.getY() - owner.getY()));
                double angleBetweenObjects = Math.acos(v1.dot(v2) / (v1.length() * v2.length()));

                if (v2.length() - target.getMask().getBoundingCircleRadius() <= radius &&
                        Math.abs(angleBetweenObjects) < angle / 2) {
                    if (pAttack > 0) {
                        applyPhysDamage(target);
                    }
                    if (mAttack > 0) {
                        applyMagicDamage(target);
                    }
                }
            }
        }
    }

    private void applyMagicDamage(Unit target) {
        double mDamage = mAttack + owner.getAttribute().getMAttack() - target.getAttribute().getMArmor();
        if (mDamage <= 1) {
            mDamage = 1;
        }
        target.getAttribute().getHP().damage(mDamage);
    }

    private void applyPhysDamage(Unit target) {
        double pDamage = pAttack + owner.getAttribute().getPAttack() - target.getAttribute().getPArmor();
        if (pDamage <= 1) {
            pDamage = 1;
        }
        target.getAttribute().getHP().damage(pDamage);
    }

    protected double lengthDirX(double direction, double length) {
        return Math.cos(direction) * length;
    }

    protected double lengthDirY(double direction, double length) {
        return Math.sin(direction) * length;
    }

   // public int getCurrentCastTime() {
     //   return currentCastTime;
    //}

//    public void setCurrentCastTime(int currentCastTime) {
  //      this.currentCastTime = currentCastTime;
    //}

    public double getRadius() {
        return radius;
    }

}
