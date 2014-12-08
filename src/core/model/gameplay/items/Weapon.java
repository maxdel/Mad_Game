package core.model.gameplay.items;

import core.model.gameplay.units.Unit;

import java.util.Map;

public class Weapon extends Item {

    public Weapon(String name, String description, Map<String, Integer> values) {
        super(name, description, values);
        setItemOperation(ItemOperation.DRESS);
    }

    @Override
    public void setBonuses(Unit target) {
        target.getAttribute().increasePAttack(getParameter("pAttack"));
        target.getAttribute().increaseMAttack(getParameter("mAttack"));

    }

    @Override
    public void unsetBonuses(Unit target) {
        target.getAttribute().decreasePAttack(getParameter("pAttack"));
        target.getAttribute().decreaseMAttack(getParameter("mAttack"));
    }

}
