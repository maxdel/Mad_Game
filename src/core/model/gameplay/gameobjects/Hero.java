package core.model.gameplay.gameobjects;

import core.MathAdv;
import core.model.gameplay.World;
import core.model.gameplay.items.ArmorLinkedList;
import core.model.gameplay.items.ItemRecord;
import core.model.gameplay.items.WeaponLinkedList;

/**
 * Represents hero
 * */
public class Hero extends Unit {

    private static Hero instance;

    private NPC selectedNPC;
    private WeaponLinkedList weaponLinkedList;
    private ArmorLinkedList armorLinkedList;

    private Hero(double x, double y, double direction) {


        super(x, y, direction, GameObjInstanceKind.HERO);

        inventory.dressItem(inventory.getExistedItems().get(2));

        weaponLinkedList = new WeaponLinkedList(inventory.getExistedItems());
        armorLinkedList = new ArmorLinkedList(inventory.getExistedItems());
    }

    public static Hero getInstance(boolean reset) {
        if (instance == null || reset) {
            instance = new Hero(0, 0, 0);
        }
        return instance;
    }

    public static Hero getInstance() {
        return getInstance(false);
    }

    public void talkToNpc() {
        if ((currentState == GameObjectState.STAND || currentState == GameObjectState.MOVE ||
                currentState == GameObjectState.DIALOG) && selectedNPC != null) {
            stand();
            setDirection(MathAdv.getAngle(getX(), getY(), selectedNPC.getX(), selectedNPC.getY()));
            selectedNPC.apply();
            if (selectedNPC.isActive()) {
                currentState = GameObjectState.DIALOG;
            } else {
                currentState = GameObjectState.STAND;
            }
        }
    }

    @Override
    public void update(int delta) {
        updateSelectedNpc();

        super.update(delta);
    }

    private void updateSelectedNpc() {
        int searchPointDistance = (int) getMask().getBoundingCircleRadius() / 2 + 15;
        int searchRadius = 50;
        for (GameObject gameObject : World.getInstance().getGameObjectList()) {
            if (gameObject.getClass() == NPC.class) {
                if (MathAdv.getDistance(getX() + MathAdv.lengthDirX(getDirection(), searchPointDistance),
                        getY() + MathAdv.lengthDirY(getDirection(), searchPointDistance),
                        gameObject.getX(), gameObject.getY()) < searchRadius) {
                    selectedNPC = (NPC) gameObject;
                    return;
                }
            }
        }
        selectedNPC = null;
    }

    // Getters

    public NPC getSelectedNPC() {
        return selectedNPC;
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