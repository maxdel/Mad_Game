package core.model.gameplay.items;

import core.model.gameplay.gameobjects.Hero;

import java.util.List;

public class SpinnerWeapon extends SpinnerThreesome {
    SpinnerNode sword;
    SpinnerNode bow;
    SpinnerNode staff;

    List<ItemRecord> existedItems;

    public SpinnerWeapon(List<ItemRecord> existedItems) {
        this.existedItems = existedItems;
        sword = new SpinnerNode();
        bow = new SpinnerNode();
        staff = new SpinnerNode();

        setValues();

        sword.next = bow;
        bow.next = staff;
        staff.next = sword;
    }

    @Override
    protected void setDressed() {
        dressed = getNode(Hero.getInstance().getInventory().getDressedWeapon());
    }

    @Override
    protected SpinnerNode getNode(ItemRecord weapon) {
        if (weapon.getItem().getClass() == Sword.class) {
            return sword;
        } else if (weapon.getItem().getClass() == Bow.class) {
            return bow;
        } else if (weapon.getItem().getClass() == Staff.class) {
            return staff;
        }

        return null;
    }

    @Override
    protected void setValues() {
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
}