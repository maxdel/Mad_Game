package core.model.gameplay.inventory;

import core.ResourceManager;

import java.util.List;

public class ItemDB {

    private static ItemDB instance;

    private List<Item> items;

    private ItemDB() {
        items = ResourceManager.getInstance().getItems();
    }

    protected static ItemDB getInstance() {
        if (instance == null) {
            instance = new ItemDB();
        }
        return instance;
    }

    protected Item getItem(String name) {
        for (Item item : items) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

}