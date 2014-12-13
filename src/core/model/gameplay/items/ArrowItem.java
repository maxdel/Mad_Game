package core.model.gameplay.items;

import java.util.Map;

public class ArrowItem extends Item {

    public ArrowItem( ItemInstanceKind instanceKind, String description, Map<String, Integer> values) {
        super(instanceKind, description, values);
        setItemOperation(ItemOperation.EMPTY);
    }

}
