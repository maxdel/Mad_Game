package core.model.gameplay;

import core.model.gameplay.inventory.Item;

public class Loot {

    private double x;
    private double y;
    private double direction;
    private Item item;
    private int number;

    public Loot(double x, double y, double direction, Item item, int number) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.item = item;
        this.number = number;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDirection() {
        return direction;
    }

    public Item getItem() {
        return item;
    }

    public int getNumber() {
        return number;
    }

}