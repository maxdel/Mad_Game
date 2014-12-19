package core.model.gameplay.items;

import core.model.gameplay.World;
import core.model.gameplay.gameobjects.Hero;

import java.util.List;

public class WeaponLinkedList {
    WeaponNode sword;
    WeaponNode bow;
    WeaponNode staff;
    WeaponNode dressed;

    List<ItemRecord> existedItems;

    public WeaponLinkedList(List<ItemRecord> existedItems) {
        this.existedItems = existedItems;
        sword = new WeaponNode();
        bow = new WeaponNode();
        staff = new WeaponNode();

        sword.next = bow;
        bow.next = staff;
        staff.next = sword;

        setValues();
    }

    public ItemRecord getNext() {
        dressed = getNodeFromWeapon(Hero.getInstance().getInventory().getDressedWeapon());
        setValues();
        return dressed.next().value;
    }

    private WeaponNode getNodeFromWeapon(ItemRecord weapon) {
        if (weapon.getItem().getClass() == Sword.class) {
            return sword;
        } else if (weapon.getItem().getClass() == Bow.class) {
            return bow;
        } else if (weapon.getItem().getClass() == Staff.class) {
            return staff;
        }

        return null;
    }

    private void setValues() {
        for (ItemRecord existedItem : existedItems) {
            if (existedItem.getItem().getClass() == Sword.class) {
                sword.value = existedItem;
            } else if (existedItem.getItem().getClass() == Bow.class) {
                bow.value = existedItem;
            } else if (existedItem.getItem().getClass() == Staff.class) {
                staff.value = existedItem;
            }
        }


    }

    private class WeaponNode {
        ItemRecord value;
        WeaponNode next;
        WeaponNode curr;

        public WeaponNode() {
            curr = this;
        }

        WeaponNode next() {
            curr = curr.next;
            if (curr.value != null) {
                return curr;
            }
            WeaponNode curr = next();
            return curr;
        }
    }


}
