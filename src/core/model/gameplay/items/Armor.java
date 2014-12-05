package core.model.gameplay.items;

import core.model.gameplay.units.GameObjectMoving;

import java.util.Map;

public class Armor extends Item {

    public Armor(String name, String description, Map<String, Integer> values) {
        super(name, description, values);
        setItemOperation(ItemOperation.DRESS);
    }

    @Override
    public void setBonuses(GameObjectMoving target) {
        target.getAttribute().increasePArmor(getParameter("pArmor"));
        target.getAttribute().increaseMArmor(getParameter("mArmor"));
    }

    @Override
    public void unsetBonuses(GameObjectMoving target) {
        target.getAttribute().decreasePArmor(getParameter("pArmor"));
        target.getAttribute().decreaseMArmor(getParameter("mArmor"));

    }

}