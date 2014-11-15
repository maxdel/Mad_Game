package core.model.gameplay.inventory;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private List<Item> items;

    public Inventory() {
        items = new ArrayList<Item>();

        items.add(new Item("Sword", "Simple sword"));
        items.add(new Item("Apple", "Green apple"));
        items.add(new Item("Silver arrow", "Arrow vs undead"));
        items.add(new Item("Sword", "Simple sword"));
        items.add(new Item("Apple", "Green apple"));
        items.add(new Item("Silver arrow", "Arrow vs undead"));
        items.add(new Item("Sword", "Simple sword"));
        items.add(new Item("Apple", "Green apple"));
        items.add(new Item("Silver arrow", "Arrow vs undead"));
    }

    public List<Item> getItems() {
        return items;
    }

}