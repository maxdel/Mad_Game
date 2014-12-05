package core.model.gameplay.skills;

import core.model.gameplay.units.GameObjectMoving;

public class RegenSkill extends Skill {

    private int HPdelta;

    public RegenSkill(GameObjectMoving owner, String name, int castTime, int cooldownTime, String requiredItem,
                      double requiredHP, double requiredMP, int HPdelta) {
        super(owner, name, castTime, cooldownTime, requiredItem, requiredHP, requiredMP);
        this.HPdelta = HPdelta;
    }

    @Override
    public boolean startCast() {
        if ((owner.getInventory().isItemDressed(requiredItem) ||
                requiredItem == null) &&
                owner.getAttribute().getMP().getCurrent() >= requiredMP &&
                owner.getAttribute().getHP().getCurrent() >= requiredHP &&
                currentCooldownTime == 0) {
            currentCastTime = castTime;
            owner.getAttribute().getMP().damage((requiredMP));
            owner.getAttribute().getHP().damage(requiredHP);
            currentCooldownTime = cooldownTime;
            return true;
        }
        return false;
    }

    @Override
    public void cast() {
        owner.getAttribute().getHP().heal(HPdelta);
    }

}