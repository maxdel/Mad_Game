package core.model.gameplay.inventory;

public class ItemRecord {

    private Item item;
    private int number;
    private boolean isMarked;

    public ItemRecord(String name, int number) {
        item = ItemDB.getInstance().getItem(name);
        this.number = number;
        this.isMarked = false;
    }

    public String getName() {
        return item.getName();
    }

    public String getDescription() {
        return item.getDescription();
    }

    public String getType() {
        return item.getType();
    }

    public int getParameter(String parameter) {
        return item.getParameter(parameter);
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

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean isMarked) {
        this.isMarked = isMarked;
    }

}