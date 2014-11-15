package core.model.gameplay.inventory;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private List<Item> items;

    public Inventory() {
        items = new ArrayList<Item>();

        items.add(new Item("Sword", "Simple sword"));
        items.add(new Item("Apple", "Green apple"));
        items.add(new Item("Apple", "Green apple"));
        items.add(new Item("Silver arrow", "Rare arrow"));
        items.add(new Item("Silver arrow", "Rare arrow"));
        items.add(new Item("Sword", "Simple sword"));
        items.add(new Item("Sword", "Simple sword"));
        items.add(new Item("Apple", "Green apple"));
        items.add(new Item("Silver arrow", "Rare arrow"));
    }

    public List<Item> getItems() {
        return items;
    }

}