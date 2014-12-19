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

    private ItemRecord dressedWeapon;
    private ItemInstanceKind dressedArmorKind;

    public Inventory(Unit owner) {
        this.owner = owner;
        existedItems = new ArrayList<>();
        dressedItems = new ArrayList<>();
        selectedRecord = null;

        addItem(ItemInstanceKind.ARROW);
        addItem(ItemInstanceKind.BOW);
    }

    /**
     * Adds Item Record with one wanted item
     * @param instanceKind is an concrete Item we want to add to inventory
     * @return Item Record with one instance of wanted item
     */
    public ItemRecord addItem(ItemInstanceKind instanceKind) {
        return addItem(instanceKind, 1);
    }

    /**
     * Add to inventory Item Record with @param number of wanted items
     * @param instanceKind is an concrete Item we want to add to inventory
     * @param number is a number of items we want add
     * @return Item Record with @param number of wanted items
     */
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

    /**
     * Deletes one item from inventory
     * @param instanceKind is an concrete Item we want to delete from inventory
     */
    public void deleteItem(ItemInstanceKind instanceKind) {
        deleteItem(instanceKind, 1);
    }

    /**
     * Deletes @param number items from inventory
     * @param instanceKind is an concrete Item we want to delete from inventory
     * @param number is an number of items we want to delete
     */
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

    /**
     * Does given operation with item:
     * - DRESS if item is dressable;
     * - SPEND if item is applicable;
     * @param itemRecord  is an ItemRecord, from we want to use
     * @return success of operation
     */
    public boolean useItem(ItemRecord itemRecord) {
        Item itm = itemRecord.getItem();
        if (itemRecord.getItem().getItemOperation() == ItemOperation.DRESS) {
            dressItem(itemRecord);
            return true;
        } else if (itm.getItemOperation() == ItemOperation.SPEND) {
            if (implementsInterface(itemRecord.getItem(), IBonusGiver.class)) {
                ((IBonusGiver) itm).setBonuses(owner);
            }
            deleteItem(itm.getInstanceKind(), 1);
            return true;
        } else if (itm.getItemOperation() == ItemOperation.EMPTY) {
            // pass
        }
        return false;
    }

    /**
     * Finds item in dressed item list, thar we want undress
     * @param itemToDress is an ItemRecord we want to undress
     * @return ItemRecord from dressed items, that we will dress
     */
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

    /**
     * Dresses @param itemToDress
     * @param itemToDress is an ItemRecord we want to dress
     */
    public boolean dressItem(ItemRecord itemToDress) {
        if (itemToDress == null) {
            return false;
        }

        ItemRecord itemToUndress = findItemToUndress(itemToDress);

        undressItem(itemToUndress);

        if (itemToDress == itemToUndress) {
            return true;
        }

        if (itemToUndress != null && implementsInterface(itemToUndress, IBonusGiver.class)) {
            ((IBonusGiver) itemToUndress).setBonuses(owner);
        }

        if (itemToDress.getItem() instanceof Weapon) {
            dressedWeapon = itemToDress;
        }

        if (itemToDress.getItem() instanceof Armor) {
            dressedArmorKind =  itemToDress.getItem().getInstanceKind();
        }

        dressedItems.add(itemToDress);
        itemToDress.setMarked(true);

        return true;
    }

    /**
     * Undresses @param itemToUndress
     * @param itemToUndress is an ItemRecord we want to undress
     */
    private void undressItem(ItemRecord itemToUndress) {

        if (itemToUndress != null) {

            if (implementsInterface(itemToUndress, IBonusGiver.class)) {
                ((IBonusGiver) itemToUndress).unsetBonuses(owner);
            }

            dressedItems.remove(itemToUndress);
            itemToUndress.setMarked(false);
        }
    }

    /**
     * Dress any of existing items which kind in @param kindList
     * @param kindList suitable to dress kinds of items
     */
    public void dressIfNotDressed(List<ItemInstanceKind> kindList) {
        for (ItemRecord itemRecord : existedItems) {
            for (ItemInstanceKind itemKind : kindList) {
                if (itemRecord.getItem().getInstanceKind() == itemKind) {
                    if (!isItemDressed(itemRecord.getItem())) {
                        useItem(itemRecord);
                    } else {
                        break;
                    }
                }
            }
        }
    }

    /* Getters and setters region */
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

    public List<ItemRecord> getExistedItems() {
        return existedItems;
    }

    public ItemRecord getSelectedRecord() {
        return selectedRecord;
    }

    public void setSelectedRecord(int index) {
        selectedRecord = existedItems.get(index);
    }

    public void setSelectedRecord(ItemRecord itemRecord) {
        selectedRecord = itemRecord;
    }

    public ItemInstanceKind getDressedArmorKind() {
        return dressedArmorKind;
    }

    public ItemRecord getDressedWeapon() {
        return dressedWeapon;
    }

    private static boolean implementsInterface(Object object, Class interf){
        for (Class c : object.getClass().getInterfaces()) {
            if (c.equals(interf)) {
                return true;
            }
        }
        return false;
    }

}