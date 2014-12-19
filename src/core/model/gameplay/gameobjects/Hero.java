package core.model.gameplay.gameobjects;

import core.model.gameplay.items.ArmorLinkedList;
import core.model.gameplay.items.ItemRecord;
import core.model.gameplay.items.WeaponLinkedList;

/**
 * Represents hero
 * */
public class Hero extends Unit {

    private WeaponLinkedList weaponLinkedList;
    private ArmorLinkedList armorLinkedList;

    public Hero(double x, double y, double direction) {

        super(x, y, direction, GameObjInstanceKind.HERO);

        inventory.dressItem(inventory.getExistedItems().get(2));

        weaponLinkedList = new WeaponLinkedList(inventory.getExistedItems());
        armorLinkedList = new ArmorLinkedList(inventory.getExistedItems());
    }

    public void changeWeapon() {
        ItemRecord itemToDress = weaponLinkedList.getNext();
        if (inventory.getDressedWeapon().getItem().getClass() == itemToDress.getItem().getClass()) {
            return;
        }

        inventory.setSelectedRecord(itemToDress);
        startUseItem();
        System.out.println("Dressed:" + itemToDress.getItem().getInstanceKind().toString());
    }

    public void changeArmor() {
        ItemRecord itemToDress = armorLinkedList.getNext();
        if (inventory.getDressedArmorKind() == itemToDress.getItem().getInstanceKind()) {
            return;
        }

        inventory.setSelectedRecord(itemToDress);
        startUseItem();
        System.out.println("Dressed:" + itemToDress.getItem().getInstanceKind().toString());
    }
}