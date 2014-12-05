package core.model.gameplay.items;

import core.model.gameplay.units.GameObjectMoving;

import java.util.Map;

public class Weapon extends Item {

    public Weapon(String name, String description, String type, Map<String, Integer> values) {
        super(name, description, type, values);
        setItemOperation(ItemOperation.DRESS);
    }

    @Override
    public void setBonuses(GameObjectMoving target) {
        target.getAttribute().increasePAttack(getParameter("pAttack"));
        target.getAttribute().increaseMAttack(getParameter("mAttack"));

    }

    @Override
    public void unsetBonuses(GameObjectMoving target) {
        target.getAttribute().decreasePAttack(getParameter("pAttack"));
        target.getAttribute().decreaseMAttack(getParameter("mAttack"));
    }

}
