package core.model.gameplay.items;

import core.model.gameplay.gameobjects.Unit;

import java.util.Map;

public class Armor extends Item implements IBonusGiver {

    public Armor(ItemInstanceKind instanceKind, String description, Map<String, Integer> values) {
        super(instanceKind, description, values);
        setItemOperation(ItemOperation.DRESS);
    }

    /**
     * Increases target's physical and magic armor
     * @param target is an unit, which attributes will change
     */
    @Override
    public void setBonuses(Unit target) {
        target.getAttribute().increasePArmor(getParameter("pArmor"));
        target.getAttribute().increaseMArmor(getParameter("mArmor"));
    }

    /**
     * Decreases target's physical and magic armor
     * @param target is an unit, which attributes will change
     */
    @Override
    public void unsetBonuses(Unit target) {
        target.getAttribute().decreasePArmor(getParameter("pArmor"));
        target.getAttribute().decreaseMArmor(getParameter("mArmor"));

    }

}