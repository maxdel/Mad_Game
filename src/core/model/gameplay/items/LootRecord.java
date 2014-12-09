package core.model.gameplay.items;

public class LootRecord {

    private Item item;
    private double probablility;

    public LootRecord(Item item, double probablility) {
        this.item = item;
        this.probablility = probablility;
    }

    public Loot generateLoot(double x, double y) {
        if (Math.random() < probablility) {
            return new Loot(x, y, item);
        } else {
            return null;
        }
    }

}