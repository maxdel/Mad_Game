package core.model.gameplay.items;

import core.model.gameplay.gameobjects.Hero;

import java.util.List;

public class SpinnerArmor extends SpinnerThreesome {
    SpinnerNode heavy;
    SpinnerNode light;
    SpinnerNode robe;

    List<ItemRecord> existedItems;

    public SpinnerArmor(List<ItemRecord> existedItems) {
        this.existedItems = existedItems;
        heavy = new SpinnerNode();
        light = new SpinnerNode();
        robe = new SpinnerNode();

        heavy.next = light;
        light.next = robe;
        robe.next = heavy;

        setValues();
    }


    @Override
    protected void setDressed() {
        dressed = getNode(Hero.getInstance().getInventory().getDressedWeapon());
    }

    @Override
    protected SpinnerNode getNode(ItemRecord weapon) {
        if (weapon.getItem().getClass() == Sword.class) {
            return heavy;
        } else if (weapon.getItem().getClass() == Bow.class) {
            return light;
        } else if (weapon.getItem().getClass() == Staff.class) {
            return robe;
        }

        return null;
    }

    @Override
    protected void setValues() {
        for (ItemRecord existedItem : existedItems) {
            if (existedItem.getItem().getInstanceKind() == ItemInstanceKind.HEAVY_ARMOR) {
                heavy.value = existedItem;
            } else if (existedItem.getItem().getInstanceKind() == ItemInstanceKind.LIGHT_ARMOR) {
                light.value = existedItem;
            } else if (existedItem.getItem().getInstanceKind() == ItemInstanceKind.ROBE_OF_MAGIC) {
                robe.value = existedItem;
            }
        }
    }


}