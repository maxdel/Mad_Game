package core.model.gameplay.items;

import core.model.gameplay.units.GameObjectMoving;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Inventory {

    private GameObjectMoving owner;
    private List<ItemRecord> itemRecords;
    private List<ItemRecord> dressedItems;
    private ItemRecord selectedRecord;

    public Inventory(GameObjectMoving owner) {
        this.owner = owner;
        itemRecords = new ArrayList<ItemRecord>();
        dressedItems = new ArrayList<ItemRecord>();
        selectedRecord = null;

        addItem("Sword");
        addItem("Strong sword");
        addItem("Bow");
        addItem("Strong bow");
        addItem("Staff");
        addItem("Strong staff");
        addItem("Apple");
        addItem("Apple");
        addItem("Arrow");
        //addItem("Arrow", 100);
        addItem("Arrow");
        deleteItem("Apple");
        addItem("Healing flask", 5);
        addItem("Mana flask", 5);
        addItem("Light armor");
        addItem("Heavy armor");
        addItem("Robe of magic");
    }

    public List<ItemRecord> getItemRecords() {
        return itemRecords;
    }

    public ItemRecord getSelectedRecord() {
        return selectedRecord;
    }

    public ItemRecord addItem(String name) {
        return addItem(name, 1);
    }

    public ItemRecord addItem(String name, int number) {
        Item item = ItemDB.getInstance().getItem(name);
        if (item != null && number > 0) {
            for (ItemRecord itemRecord : itemRecords) {
                if (itemRecord.getName().equals(name)) {
                    itemRecord.setNumber(itemRecord.getNumber() + number);
                    return itemRecord;
                }
            }
            ItemRecord itemRecord = new ItemRecord(name, number);
            itemRecords.add(itemRecord);
            if (itemRecords.size() == 1) {
                selectedRecord = itemRecords.get(0);
            }
            return itemRecord;
        }
        return null;
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
                        undressItem(itemRecord);
                        it.remove();
                        if (selectedRecord == itemRecord) {
                            if (itemRecords.size() == 0) {
                                selectedRecord = null;
                            } else {
                                if (i < itemRecords.size()) {
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


    public boolean useItem(ItemRecord itemRecord) {
        if (itemRecord.getType().equals("healing")) {
            owner.getAttribute().getHP().heal(itemRecord.getParameter("heal"));
            deleteItem(itemRecord.getName(), 1);
            return true;
        } else if (itemRecord.getType().equals("mana")) {
            owner.getAttribute().getMP().heal(itemRecord.getParameter("mana"));
            deleteItem(itemRecord.getName(), 1);
            return true;
        } else if (itemRecord.getType().equals("sword") ||
                itemRecord.getType().equals("bow") ||
                itemRecord.getType().equals("staff")) {
            dressItem(itemRecord);
            return true;
        } else if (itemRecord.getType().equals("armor")) {
            dressItem(itemRecord);
            return true;
        } else {
            return false;
        }
    }

    private void dressItem(ItemRecord itemToDress) {
        ItemRecord itemToUndress = null;
        for (Iterator<ItemRecord> it = dressedItems.iterator(); it.hasNext();) {
            ItemRecord itemRecord = it.next();
            // TODO: do item common class

            if (itemToDress.getType().equals("sword") ||
                    itemToDress.getType().equals("bow") ||
                    itemToDress.getType().equals("staff")) {
                if (itemRecord.getType().equals("sword") ||
                        itemRecord.getType().equals("bow") ||
                        itemRecord.getType().equals("staff")) {
                    itemToUndress = itemRecord;
                    break;
                }
            } else {
                if (itemRecord.getType().equals(itemToDress.getType())) {
                    itemToUndress = itemRecord;
                    break;
                }
            }
        }
        if (itemToDress.getType().equals("sword") ||
                itemToDress.getType().equals("bow") ||
                itemToDress.getType().equals("staff")) {
            undressItem(itemToUndress); // TODO: undress one time

            if (itemToDress != itemToUndress) {
                owner.getAttribute().setPAttack(owner.getAttribute().getPAttack() + itemToDress.getParameter("pAttack"));
                owner.getAttribute().setMAttack(owner.getAttribute().getMAttack() + itemToDress.getParameter("mAttack"));
                dressedItems.add(itemToDress);
                itemToDress.setMarked(true);
            }
        } else if (itemToDress.getType().equals("armor")) {
            undressItem(itemToUndress);

            if (itemToDress != itemToUndress) {
                owner.getAttribute().setPArmor(owner.getAttribute().getPArmor() + itemToDress.getParameter("pArmor"));
                owner.getAttribute().setMArmor(owner.getAttribute().getMArmor() + itemToDress.getParameter("mArmor"));
                dressedItems.add(itemToDress);
                itemToDress.setMarked(true);
            }
        }
    }

    private void undressItem(ItemRecord itemToUndress) {
        if (itemToUndress != null) {
            if (itemToUndress.getType().equals("sword") ||
                    itemToUndress.getType().equals("bow") ||
                    itemToUndress.getType().equals("staff")) {
                owner.getAttribute().setPAttack(owner.getAttribute().getPAttack() - itemToUndress.getParameter("pAttack"));
                owner.getAttribute().setMAttack(owner.getAttribute().getMAttack() - itemToUndress.getParameter("mAttack"));
            } else if (itemToUndress.getType().equals("armor")) {
                owner.getAttribute().setPArmor(owner.getAttribute().getPArmor() - itemToUndress.getParameter("pArmor"));
                owner.getAttribute().setMArmor(owner.getAttribute().getMArmor() - itemToUndress.getParameter("mArmor"));
            }
            dressedItems.remove(itemToUndress);
            itemToUndress.setMarked(false);
        }
    }

    public boolean isItemDressed(String itemType) {
        for (Iterator<ItemRecord> it = dressedItems.iterator(); it.hasNext();) {
            ItemRecord itemRecord = it.next();
            if (itemRecord.getType().equals(itemType)) {
                return true;
            }
        }
        return false;
    }

    public boolean isItemExists(String itemName) {
        for (Iterator<ItemRecord> it = itemRecords.iterator(); it.hasNext();) {
            ItemRecord itemRecord = it.next();
            if (itemRecord.getName().equals(itemName)) {
                return true;
            }
        }
        return false;
    }

}