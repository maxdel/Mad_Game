package core.model.gameplay.items;

import core.model.gameplay.gameobjects.Unit;

import java.util.Map;

abstract public class Item {

    protected ItemInstanceKind instanceKind;

    private String name;
    private String description;
    private ItemOperation itemOperation;

    private Map<String, Integer> values;

    public Item(ItemInstanceKind instanceKind, String description, Map<String, Integer> values) {
        this.name = name;
        this.description = description;
        this.values = values;
        setItemOperation(ItemOperation.EMPTY);

        this.instanceKind = instanceKind;
    }

    /* Getters and setters region */
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

    public ItemInstanceKind getInstanceKind() {
        return instanceKind;
    }

   /*public String getName() {
        return name;
    }*/
}