package core.model.gameplay.inventory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Inventory {

    private List<ItemRecord> itemRecords;

    public Inventory() {
        itemRecords = new ArrayList<ItemRecord>();

        addItem("Sword");
        addItem("Apple");
        addItem("Silver arrow");
        addItem("Silver arrow");
        addItem("Silver arrow");
        addItem("Silver arrow");
        deleteItem("Silver arrow");
        deleteItem("Apple");
    }

    public List<ItemRecord> getItemRecords() {
        return itemRecords;
    }

    protected void addItem(String name) {
        addItem(name, 1);
    }

    public void addItem(String name, int number) {
        Item item = ItemDB.getInstance().getItem(name);
        if (item != null && number > 0) {
            for (ItemRecord itemRecord : itemRecords) {
                if (itemRecord.getName().equals(name)) {
                    itemRecord.setNumber(itemRecord.getNumber() + number);
                    return;
                }
            }
            itemRecords.add(new ItemRecord(name, number));
        }
    }

    protected void deleteItem(String name) {
        deleteItem(name, 1);
    }

    protected void deleteItem(String name, int number) {
        Item item = ItemDB.getInstance().getItem(name);
        if (item != null && number > 0) {
            for (Iterator<ItemRecord> it = itemRecords.iterator(); it.hasNext();) {
                ItemRecord itemRecord = it.next();
                if (itemRecord.getName().equals(name)) {
                    if (itemRecord.getNumber() - number <= 0) {
                        it.remove();
                    } else {
                        itemRecord.setNumber(itemRecord.getNumber() - number);
                    }
                    break;
                }
            }
        }
    }

}