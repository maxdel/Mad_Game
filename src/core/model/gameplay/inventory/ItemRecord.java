package core.model.gameplay.inventory;

public class ItemRecord {

    private Item item;
    private int number;

    protected ItemRecord(String name, int number) {
        item = ItemDB.getInstance().getItem(name);
        this.number = number;
    }

    public String getName() {
        return item.getName();
    }

    public String getDescription() {
        return item.getDescription();
    }

    public int getNumber() {
        return number;
    }

    public Item getItem() {
        return item;
    }

    protected void setNumber(int number) {
        this.number = number;
    }

}