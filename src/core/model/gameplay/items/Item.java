package core.model.gameplay.items;

import core.model.gameplay.units.GameObjectMoving;

import java.util.Map;

public class Item {

    private String name;
    private String description;
    private String type;
    private boolean canDress;

    private Map<String, Integer> values;

    public Item(String name, String description, String type, Map<String, Integer> values) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.values = values;
        this.canDress = false;
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

    public String getType() {
        return type;
    }

    public int getParameter(String parameter) {
        return values.get(parameter);
    }

    public Map<String, Integer> getValues() {
        return values;
    }

    public boolean canDress() {
        return canDress;
    }

    public void setCanDress(boolean canDress) {
        this.canDress = canDress;
    }
}