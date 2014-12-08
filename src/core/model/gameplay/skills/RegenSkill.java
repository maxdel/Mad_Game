package core.model.gameplay.skills;

import core.model.gameplay.gameobjects.Unit;

public class RegenSkill extends Skill {

    private int HPdelta;

    public RegenSkill(Unit owner, String name, String description, int castTime, int postCastTime,
                      int cooldownTime, String requiredItem,
                      double requiredHP, double requiredMP, int HPdelta) {
        super(owner, name, description, castTime, postCastTime, cooldownTime, requiredItem, requiredHP, requiredMP);
        this.HPdelta = HPdelta;
    }

    @Override
    public void apply() {
        owner.getAttribute().getHP().heal(HPdelta);
    }

}