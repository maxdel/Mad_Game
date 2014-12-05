package core.model.gameplay.items;

import core.resource_manager.ResourceManager;

import java.util.List;

public class ItemDB {

    private static ItemDB instance;

    private List<Item> items;

    private ItemDB() {
        items = ResourceManager.getInstance().getItems();
    }

    public static ItemDB getInstance() {
        if (instance == null) {
            instance = new ItemDB();
        }
        return instance;
    }

    public Item getItem(String name) {
        for (Item item : items) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }
}