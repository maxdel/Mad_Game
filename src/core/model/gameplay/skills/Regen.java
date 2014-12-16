package core.model.gameplay.skills;

import core.model.gameplay.gameobjects.Unit;
import core.model.gameplay.items.ItemInstanceKind;

public class Regen extends Skill {

    private int HPdelta;

    public Regen(String name, String description, int castTime, int postCastTime,
                 int cooldownTime, ItemInstanceKind requiredItemKind,
                 double requiredHP, double requiredMP, int HPdelta,
                 SkillInstanceKind regenKind) {
        super(name, description, castTime, postCastTime, cooldownTime, requiredItemKind, requiredHP, requiredMP, regenKind);
        this.HPdelta = HPdelta;
    }

    /**
     * Increases the owner's health
     */
    @Override
    protected void apply(Unit owner) {
        owner.getAttribute().changeHP(HPdelta);
    }

}
