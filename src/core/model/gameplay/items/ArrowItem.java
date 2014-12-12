package core.model.gameplay.items;

import java.util.Map;

public class ArrowItem extends Item {

    public ArrowItem(String name, String description, Map<String, Integer> values) {
        super(name, description, values);
        setItemOperation(ItemOperation.EMPTY);
    }

}
