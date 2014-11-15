package core.model.gameplay.inventory;

public class Item {

    private String name;
    private String description;

    protected Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}