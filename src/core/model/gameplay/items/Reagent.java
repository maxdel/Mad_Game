package core.model.gameplay.items;

import core.model.gameplay.units.GameObjectMoving;

import java.util.Map;

public class Reagent extends Item {

    public Reagent(String name, String description, Map<String, Integer> values) {
        super(name, description, values);
        setItemOperation(ItemOperation.SPEND);
    }

    @Override
    public void setBonuses(GameObjectMoving target) {
        target.getAttribute().getHP().heal(getParameter("heal"));
        target.getAttribute().getMP().heal(getParameter("mana"));
    }


}
