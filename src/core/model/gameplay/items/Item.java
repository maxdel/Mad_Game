package core.model.gameplay.items;

import core.model.gameplay.units.GameObjectMoving;

import java.util.Map;

public class Item {

    private String name;
    private String description;
    private ItemOperation itemOperation;

    private Map<String, Integer> values;

    public Item(String name, String description, Map<String, Integer> values) {
        this.name = name;
        this.description = description;
        this.values = values;
        setItemOperation(ItemOperation.EMPTY);
    }

    public void setBonuses(GameObjectMoving target) {
        // pass
    }

    public void unsetBonuses(GameObjectMoving target) {
        // pass
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    public int getParameter(String parameter) {
        return values.get(parameter);
    }

    public Map<String, Integer> getValues() {
        return values;
    }

    public ItemOperation getItemOperation() {
        return itemOperation;
    }

    public void setItemOperation(ItemOperation itemOperation) {
        this.itemOperation = itemOperation;
    }
}