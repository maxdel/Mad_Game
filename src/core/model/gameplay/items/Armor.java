package core.model.gameplay.items;

import core.model.gameplay.units.Unit;

import java.util.Map;

public class Armor extends Item {

    public Armor(String name, String description, Map<String, Integer> values) {
        super(name, description, values);
        setItemOperation(ItemOperation.DRESS);
    }

    @Override
    public void setBonuses(Unit target) {
        target.getAttribute().increasePArmor(getParameter("pArmor"));
        target.getAttribute().increaseMArmor(getParameter("mArmor"));
    }

    @Override
    public void unsetBonuses(Unit target) {
        target.getAttribute().decreasePArmor(getParameter("pArmor"));
        target.getAttribute().decreaseMArmor(getParameter("mArmor"));

    }

}