package core.model.gameplay.skills;

import core.model.gameplay.gameobjects.Unit;

public class Regen extends Skill {

    private int HPdelta;

    public Regen(Unit owner, String name, String description, int castTime, int postCastTime,
                 int cooldownTime, String requiredItem,
                 double requiredHP, double requiredMP, int HPdelta) {
        super(owner, name, description, castTime, postCastTime, cooldownTime, requiredItem, requiredHP, requiredMP);
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
