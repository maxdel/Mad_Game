package core.model.gameplay.items;

import core.resource_manager.ResourceManager;

import java.util.List;

public class ItemDB {

    private static ItemDB instance;

    private List<Item> items;

    private ItemDB() {
        items = ResourceManager.getInstance().getItems();
        formItemDB();
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

    private void formItemDB() {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getType().equals("Sword")) {
                items.set(i, new Sword(items.get(i).getName(), items.get(i).getDescription(), items.get(i).getType(), items.get(i).getValues()));

            }
            else if (items.get(i).getType().equals("Bow")) {
                items.set(i, new Bow(items.get(i).getName(), items.get(i).getDescription(), items.get(i).getType(), items.get(i).getValues()));
            }
            else if (items.get(i).getType().equals("Staff")) {
                items.set(i, new Stuff(items.get(i).getName(), items.get(i).getDescription(), items.get(i).getType(), items.get(i).getValues()));
            }
            else if (items.get(i).getType().equals("Reagent")) {
                items.set(i, new Reagent(items.get(i).getName(), items.get(i).getDescription(), items.get(i).getType(), items.get(i).getValues()));
            }
            else if (items.get(i).getType().equals("Armor")) {
                items.set(i, new Armor(items.get(i).getName(), items.get(i).getDescription(), items.get(i).getType(), items.get(i).getValues()));
            }
        }
    }
}