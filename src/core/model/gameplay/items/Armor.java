package core.model.gameplay.items;

import core.model.gameplay.units.GameObjectMoving;

import java.util.List;
import java.util.Map;

public class Armor extends Item {

    public Armor(String name, String description, String type, Map<String, Integer> values) {
        super(name, description, type, values);
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
