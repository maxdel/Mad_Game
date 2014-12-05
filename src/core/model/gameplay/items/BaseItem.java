package core.model.gameplay.items;

import java.util.Map;

public abstract class BaseItem {
    String name;
    private String description;
    String type;
    boolean equipped;
    private Map<String, Integer> values;



    public BaseItem(String name, String description, String type) {
        this.name = name;
        this.description = description;
        this.type = type;
        equipped = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BaseItem{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", equipped=" + equipped +
                '}';
    }
}
