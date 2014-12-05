package core.model.gameplay.items;

import java.util.Map;

public class Item {

    private String name;
    private String description;
    private String type;

    private Map<String, Integer> values;

    public Item(String name, String description, String type, Map<String, Integer> values) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.values = values;
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

}