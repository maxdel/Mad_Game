package core.model.gameplay.items;

import core.model.gameplay.gameobjects.Unit;

import java.util.Map;

public class Reagent extends Item implements IBonusGiver {

    public Reagent(ItemInstanceKind instanceKind, String description, Map<String, Integer> values) {
        super(instanceKind, description, values);
        setItemOperation(ItemOperation.SPEND);
    }

    /**
     * Increases target's health and magic points
     * @param target is an unit, which attributes will change
     */
    @Override
    public void setBonuses(Unit target) {
        target.getAttribute().getHP().heal(getParameter("heal"));
        target.getAttribute().getMP().heal(getParameter("mana"));
    }

    /**
     * Decrease isn't need
     */
    @Override
    public void unsetBonuses(Unit target) {
        // pass
    }
}
