package core.model.gameplay.items;

import core.model.gameplay.gameobjects.Loot;

public class LootRecord {

    private Item item;
    private double probability;

    public LootRecord(Item item, double probability) {
        this.item = item;
        this.probability = probability;
    }

    public Loot generateLoot(double x, double y) {
        if (Math.random() < probability) {
            return new Loot(x, y, item);
        } else {
            return null;
        }
    }

}