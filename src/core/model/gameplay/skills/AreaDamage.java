package core.model.gameplay.skills;

import core.MathAdv;
import core.model.gameplay.World;
import core.model.gameplay.gameobjects.GameObject;
import core.model.gameplay.gameobjects.GameObjectSolid;
import core.model.gameplay.gameobjects.Unit;
import core.model.gameplay.items.ItemInstanceKind;
import org.newdawn.slick.geom.Vector2f;

public class AreaDamage extends Skill {

    private double pAttack;
    private double mAttack;

    private double radius;
    private double angle;

    public AreaDamage(String name, String description, int castTime, int postCastTime,
                      int cooldownTime, ItemInstanceKind requiredItemKind, double requiredHP, double requiredMP,
                      double pAttack, double mAttack, double radius, double angle, SkillInstanceKind kind) {
        super(name, description, castTime, postCastTime, cooldownTime, requiredItemKind, requiredHP, requiredMP, kind);
        this.pAttack = pAttack;
        this.mAttack = mAttack;
        this.radius = radius;
        this.angle = angle;
    }

    /**
     *  Detect the solid object's, that fall under the area of effect and applies the effect
     */
    @Override
    protected void apply(Unit owner) {
        for (GameObject gameObject : World.getInstance().getGameObjectList()) {
            if (gameObject instanceof Unit && gameObject != owner) {
                Unit target = (Unit) gameObject;

                if (isHitTheTarget(owner, target)) {
                    if (pAttack > 0) {
                        applyPhysDamage(owner, target);
                    }
                    if (mAttack > 0) {
                        applyMagicDamage(owner, target);
                    }
                }
            }
        }
    }

    public boolean isHitTheTarget(Unit owner, GameObjectSolid target) {
            Vector2f v1 = new Vector2f((float) MathAdv.lengthDirX(owner.getDirection(), angle),
            (float)MathAdv.lengthDirY(owner.getDirection(), angle));
        Vector2f v2 = new Vector2f((float)(target.getX() - owner.getX()), (float)(target.getY() - owner.getY()));
        double angleBetweenObjects = Math.acos(v1.dot(v2) / (v1.length() * v2.length()));

        if (v2.length() - target.getMask().getBoundingCircleRadius() <= radius &&
                Math.abs(angleBetweenObjects) < angle / 2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Applies magic damage to the target
     * @param target
     */
    private void applyMagicDamage(Unit owner, Unit target) {
        double mDamage = mAttack + owner.getAttribute().getMAttack() - target.getAttribute().getMArmor();
        if (mDamage <= 1) {
            mDamage = 1;
        }
        target.getAttribute().getHP().damage(mDamage);
    }

    /**
     * Applies physical damage to the target
     * @param target
     */
    private void applyPhysDamage(Unit owner, Unit target) {
        double pDamage = pAttack + owner.getAttribute().getPAttack() - target.getAttribute().getPArmor();
        if (pDamage <= 1) {
            pDamage = 1;
        }
        target.getAttribute().getHP().damage(pDamage);
    }

    public double getRadius() {
        return radius;
    }

}
