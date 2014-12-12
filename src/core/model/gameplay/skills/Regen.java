package core.model.gameplay.skills;

import core.model.gameplay.gameobjects.Unit;

public class Regen extends Skill {

    private int HPdelta;

    public Regen(String name, String description, int castTime, int postCastTime,
                 int cooldownTime, String requiredItem,
                 double requiredHP, double requiredMP, int HPdelta,
                 SkillKind regenKind) {
        super(name, description, castTime, postCastTime, cooldownTime, requiredItem, requiredHP, requiredMP, regenKind);
        this.HPdelta = HPdelta;
    }

    /**
     * Increases the owner's health
     */
    @Override
    public void apply(Unit owner) {
        owner.getAttribute().getHP().heal(HPdelta);
    }

}
