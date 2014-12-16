package core.model.gameplay.items;

import core.model.gameplay.gameobjects.Unit;

import java.util.Map;

public class Weapon extends Item implements IBonusGiver {

    public Weapon(ItemInstanceKind instanceKind, String description, Map<String, Integer> values) {
        super(instanceKind, description, values);
        setItemOperation(ItemOperation.DRESS);
    }

    /**
     * Increases target's physical and magic attack
     * @param target is an unit, which attributes will change
     */
    @Override
    public void setBonuses(Unit target) {
        target.getAttribute().changePAttack(getParameter("pAttack"));
        target.getAttribute().changeMAttack(getParameter("mAttack"));
    }

    /**
     * Decreases target's physical and magic attack
     * @param target is an unit, which attributes will change
     */
    @Override
    public void unsetBonuses(Unit target) {
        target.getAttribute().changePAttack(getParameter("pAttack"));
        target.getAttribute().changeMAttack(getParameter("mAttack"));
    }

}