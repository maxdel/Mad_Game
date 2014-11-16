package core.model.gameplay.inventory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Inventory {

    private List<ItemRecord> itemRecords;
    private ItemRecord selectedRecord;

    public Inventory() {
        itemRecords = new ArrayList<ItemRecord>();
        selectedRecord = null;

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

    public ItemRecord getSelectedRecord() {
        return selectedRecord;
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
            if (itemRecords.size() == 1) {
                selectedRecord = itemRecords.get(0);
            }
        }
    }

    protected void deleteItem(String name) {
        deleteItem(name, 1);
    }

    public void deleteItem(String name, int number) {
        Item item = ItemDB.getInstance().getItem(name);
        if (item != null && number > 0) {
            int i = -1;
            for (Iterator<ItemRecord> it = itemRecords.iterator(); it.hasNext();) {
                i++;
                ItemRecord itemRecord = it.next();
                if (itemRecord.getName().equals(name)) {
                    if (itemRecord.getNumber() - number <= 0) {
                        it.remove();
                        if (selectedRecord == itemRecord) {
                            if (itemRecords.size() == 0) {
                                selectedRecord = null;
                            } else {
                                if (i <= itemRecords.size() - 1) {
                                    selectedRecord = itemRecords.get(i);
                                } else {
                                    selectedRecord = itemRecords.get(i - 1);
                                }
                            }
                        }
                    } else {
                        itemRecord.setNumber(itemRecord.getNumber() - number);
                    }
                    break;
                }
            }
        }
    }

    public void setSelectedRecord(int index) {
        selectedRecord = itemRecords.get(index);
    }

}