package core.model.gameplay.items;

import core.model.gameplay.gameobjects.GameObject;

public class Loot extends GameObject {

    private Item item;
    private int number;

    public Loot(double x, double y, Item item) {
        this(x, y, Math.random() * 2 * Math.PI, item, 1);
    }

    public Loot(double x, double y, double direction, Item item, int number) {
        super(x, y, direction);
        this.item = item;
        this.number = number;
    }

    public Item getItem() {
        return item;
    }

    public int getNumber() {
        return number;
    }

}