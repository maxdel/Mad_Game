package core.model.gameplay.items;

import core.model.gameplay.gameobjects.Unit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Inventory {

    private Unit owner;
    private List<ItemRecord> existedItems;
    private List<ItemRecord> dressedItems;
    private ItemRecord selectedRecord;

    public Inventory(Unit owner) {
        this.owner = owner;
        existedItems = new ArrayList<>();
        dressedItems = new ArrayList<>();
        selectedRecord = null;

        addItem(ItemInstanceKind.ARROW);
        addItem(ItemInstanceKind.BOW);
    }

    /**
     *
     * @return
     */
    public List<ItemRecord> getExistedItems() {
        return existedItems;
    }

    public ItemRecord getSelectedRecord() {
        return selectedRecord;
    }

    public ItemRecord addItem(ItemInstanceKind instanceKind) {
        return addItem(instanceKind, 1);
    }

    public ItemRecord addItem(ItemInstanceKind instanceKind, int number) {
        Item item = ItemDB.getInstance().getItem(instanceKind);
        if (item != null && number > 0) {
            for (ItemRecord itemRecord : existedItems) {
                if (itemRecord.getItem().instanceKind == instanceKind) {
                    itemRecord.setNumber(itemRecord.getNumber() + number);
                    return itemRecord;
                }
            }
            ItemRecord itemRecord = new ItemRecord(instanceKind, number);
            existedItems.add(itemRecord);
            if (existedItems.size() == 1) {
                selectedRecord = existedItems.get(0);
            }
            return itemRecord;
        }
        return null;
    }

    public void deleteItem(ItemInstanceKind instanceKind) {
        deleteItem(instanceKind, 1);
    }

    public void deleteItem(ItemInstanceKind instanceKind, int number) {
        Item item = ItemDB.getInstance().getItem(instanceKind);
        if (item != null && number > 0) {
            int i = -1;
            for (Iterator<ItemRecord> it = existedItems.iterator(); it.hasNext();) {
                i++;
                ItemRecord itemRecord = it.next();
                if (itemRecord.getItem().instanceKind == instanceKind) {
                    if (itemRecord.getNumber() - number <= 0) {
                        undressItem(itemRecord);
                        it.remove();
                        if (selectedRecord == itemRecord) {
                            if (existedItems.size() == 0) {
                                selectedRecord = null;
                            } else {
                                if (i < existedItems.size()) {
                                    selectedRecord = existedItems.get(i);
                                } else {
                                    selectedRecord = existedItems.get(i - 1);
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
        selectedRecord = existedItems.get(index);
    }


    public boolean useItem(ItemRecord itemRecord) {
        if (itemRecord.getItem().getItemOperation() == ItemOperation.DRESS) {
            dressItem(itemRecord);
            return true;
        } else if (itemRecord.getItem().getItemOperation() == ItemOperation.SPEND) {
            itemRecord.getItem().setBonuses(owner);
            deleteItem(itemRecord.getItem().getInstanceKind(), 1);
            return true;
        } else if (itemRecord.getItem().getItemOperation() == ItemOperation.EMPTY) {
            // pass
        }
        return false;
    }

    private ItemRecord findItemToUndress(ItemRecord itemToDress) {
        ItemRecord itemToUndress = null;
        for (ItemRecord currDressedItem : dressedItems) {

            if (itemToDress.getItem() instanceof Weapon) {
                if (currDressedItem.getItem() instanceof Weapon) {
                    itemToUndress = currDressedItem;
                    break;
                }
            } else if (itemToDress.getItem() instanceof Armor) {
                if (currDressedItem.getItem() instanceof Armor) {
                    itemToUndress = currDressedItem;
                    break;
                }
            }
        }
        return itemToUndress;
    }

    private void dressItem(ItemRecord itemToDress) {
        ItemRecord itemToUndress = findItemToUndress(itemToDress);

        undressItem(itemToUndress);

        if (itemToDress == itemToUndress) {
            return;
        }

        itemToDress.getItem().setBonuses(owner);
        dressedItems.add(itemToDress);
        itemToDress.setMarked(true);
    }

    private void undressItem(ItemRecord itemToUndress) {
        if (itemToUndress != null) {

            itemToUndress.getItem().unsetBonuses(owner);

            dressedItems.remove(itemToUndress);
            itemToUndress.setMarked(false);
        }
    }

    public String getDressedWeaponType() {
        for (Iterator<ItemRecord> it = dressedItems.iterator(); it.hasNext();) {
            ItemRecord itemRecord = it.next();
            if (itemRecord.getItem() instanceof Sword) {
                return "Sword";
            } else if (itemRecord.getItem() instanceof Bow) {
                return "Bow";
            } else if (itemRecord.getItem() instanceof Staff) {
                return "Stuff";
            }
        }
        return "";
    }

    public boolean isItemDressed(Item item) {
        for (Iterator<ItemRecord> it = dressedItems.iterator(); it.hasNext();) {
            ItemRecord itemRecord = it.next();
            if (itemRecord.getItem() == item) {
                return true;
            }
        }
        return false;
    }

    public boolean isItemClassDressed(Class itemClass) {
        for (Iterator<ItemRecord> it = dressedItems.iterator(); it.hasNext();) {
            ItemRecord itemRecord = it.next();
            if (itemRecord.getItem().getClass() == itemClass) {
                return true;
            }
        }
        return false;
    }


    public boolean isItemExists(Item item) {
        for (Iterator<ItemRecord> it = existedItems.iterator(); it.hasNext();) {
            ItemRecord itemRecord = it.next();
            if (itemRecord.getItem() == item) {
                return true;
            }
        }
        return false;
    }

    public boolean isItemClassExists(Class itemClass) {
        for (Iterator<ItemRecord> it = existedItems.iterator(); it.hasNext();) {
            ItemRecord itemRecord = it.next();
            if (itemRecord.getItem().getClass() == itemClass) {
                return true;
            }
        }
        return false;
    }

}