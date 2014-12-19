package core.model.gameplay.items;

import core.model.gameplay.World;

import java.util.List;

public class ArmorLinkedList {
    ArmorNode heavy;
    ArmorNode light;
    ArmorNode robe;
    ArmorNode dressed;

    List<ItemRecord> existedItems;

    public ArmorLinkedList(List<ItemRecord> existedItems) {
        this.existedItems = existedItems;
        heavy = new ArmorNode();
        light = new ArmorNode();
        robe = new ArmorNode();

        heavy.next = light;
        light.next = robe;
        robe.next = heavy;

        setValues();
    }

    public ItemRecord getNext() {
        dressed = getNodeFromArmor(World.getInstance().getHero().getInventory().getDressedWeapon());
        setValues();
        return dressed.next().value;
    }

    private ArmorNode getNodeFromArmor(ItemRecord weapon) {
        if (weapon.getItem().getClass() == Sword.class) {
            return heavy;
        } else if (weapon.getItem().getClass() == Bow.class) {
            return light;
        } else if (weapon.getItem().getClass() == Staff.class) {
            return robe;
        }

        return null;
    }

    private void setValues() {
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

    private class ArmorNode {
        ItemRecord value;
        ArmorNode next;
        ArmorNode curr;

        public ArmorNode() {
            curr = this;
        }

        ArmorNode next() {
            curr = curr.next;
            if (curr.value != null) {
                return curr;
            }
            ArmorNode curr = next();
            return curr;
        }
    }

}
