package core.model.gameplay.items;

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

    /*uses in view*/
    public void setMarked(boolean isMarked) {
        this.isMarked = isMarked;
    }

}