package core.model.gameplay.skills;

import core.model.gameplay.gameobjects.Unit;

public class Regen extends Skill {

    public enum Kinds implements SkillKinds {
        HEAL
    }

    private int HPdelta;

    public Regen(Unit owner, String name, String description, int castTime, int postCastTime,
                 int cooldownTime, String requiredItem,
                 double requiredHP, double requiredMP, int HPdelta,
                 SkillKinds regenKind) {
        super(owner, name, description, castTime, postCastTime, cooldownTime, requiredItem, requiredHP, requiredMP, regenKind);
        this.HPdelta = HPdelta;
    }

    /**
     * Increases the owner's health
     */
    @Override
    public void apply() {
        owner.getAttribute().getHP().heal(HPdelta);
    }

}
