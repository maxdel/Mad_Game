package core.model.gameplay.items;

import core.model.gameplay.gameobjects.Unit;

import java.util.Map;

public class Reagent extends Item {

    public Reagent(ItemInstanceKind instanceKind, String description, Map<String, Integer> values) {
        super(instanceKind, description, values);
        setItemOperation(ItemOperation.SPEND);
    }

    @Override
    public void setBonuses(Unit target) {
        target.getAttribute().getHP().heal(getParameter("heal"));
        target.getAttribute().getMP().heal(getParameter("mana"));
    }


}
