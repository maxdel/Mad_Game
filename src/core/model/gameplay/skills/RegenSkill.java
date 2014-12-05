package core.model.gameplay.skills;

import core.model.gameplay.items.Item;
import core.model.gameplay.units.GameObjectMoving;

public class RegenSkill extends Skill {

    private int HPdelta;

    public RegenSkill(GameObjectMoving owner, String name, int castTime, int cooldownTime, Item requiredItem,
                      double requiredHP, double requiredMP, int HPdelta) {
        super(owner, name, castTime, cooldownTime, requiredItem, requiredHP, requiredMP);
        this.HPdelta = HPdelta;
    }

    @Override
    public void cast() {
        owner.getAttribute().getHP().heal(HPdelta);
    }

}