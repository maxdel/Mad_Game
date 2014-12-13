package core.model.gameplay.items;

import core.resourcemanager.ResourceManager;

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
    public Item getItem(ItemInstanceKind instanceKind) {
        for (Item item : items) {
            if (item.getInstanceKind() == instanceKind) {
                return item;
            }
        }
        return null;
    }

}