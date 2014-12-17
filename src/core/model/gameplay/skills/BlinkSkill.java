package core.model.gameplay.skills;

import core.MathAdv;
import core.model.gameplay.CollisionManager;
import core.model.gameplay.gameobjects.Unit;
import core.model.gameplay.items.ItemInstanceKind;

public class BlinkSkill extends Skill {

    private double distance;

    public BlinkSkill(String name, String description, int castTime, int postApplyTime, int cooldownTime,
                      ItemInstanceKind requiredItem, double requiredHP, double requiredMP, double distance, SkillInstanceKind kind) {
        super(name, description, castTime, postApplyTime, cooldownTime, requiredItem, requiredHP, requiredMP, kind);
        this.distance = distance;
    }

    @Override
    protected void apply(Unit owner) {
        double resultDirection = owner.getDirection() + owner.getRelativeDirection();
        if (CollisionManager.getInstance().isPlaceFreeAdv(owner,
                owner.getX() + MathAdv.lengthDirX(resultDirection, distance),
                owner.getY() + MathAdv.lengthDirY(resultDirection, distance))) {
            owner.changeX(MathAdv.lengthDirX(resultDirection, distance));
            owner.changeY(MathAdv.lengthDirY(resultDirection, distance));
        }
    }

    public double getDistance() {
        return distance;
    }

}