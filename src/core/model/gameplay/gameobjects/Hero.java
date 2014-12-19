package core.model.gameplay.gameobjects;

import core.MathAdv;
import core.model.gameplay.World;
import core.model.gameplay.items.SpinnerArmor;
import core.model.gameplay.items.ItemRecord;
import core.model.gameplay.items.SpinnerWeapon;

/**
 * Represents hero
 * */
public class Hero extends Unit {

    private static Hero instance;

    private NPC selectedNPC;
    private SpinnerWeapon spinnerWeapon;
    private SpinnerArmor spinnerAromor;

    private Hero(double x, double y, double direction) {


        super(x, y, direction, GameObjInstanceKind.HERO);

        inventory.dressItem(inventory.getExistedItems().get(2));

        spinnerWeapon = new SpinnerWeapon(inventory.getExistedItems());
        spinnerAromor = new SpinnerArmor(inventory.getExistedItems());

        inventory.dressItem(inventory.getExistedItems().get(0));
        inventory.dressItem(inventory.getExistedItems().get(5));
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
        if ((currentState == UnitState.STAND || currentState == UnitState.MOVE ||
                currentState == UnitState.DIALOG) && selectedNPC != null) {
            stand();
            setDirection(MathAdv.getAngle(getX(), getY(), selectedNPC.getX(), selectedNPC.getY()));
            selectedNPC.apply();
            if (selectedNPC.isActive()) {
                currentState = UnitState.DIALOG;
            } else {
                currentState = UnitState.STAND;
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
        ItemRecord itemToDress = spinnerWeapon.getNext();
        if (inventory.getDressedWeapon().getItem().getClass() == itemToDress.getItem().getClass()) {
            return;
        }

        inventory.setSelectedRecord(itemToDress);
        fastUseItem();
    }

    public void changeArmor() {
        ItemRecord itemToDress = spinnerAromor.getNext();
        if (inventory.getDressedArmor().getItem().getInstanceKind() == itemToDress.getItem().getInstanceKind()) {
            return;
        }

        inventory.setSelectedRecord(itemToDress);
        startUseItem();
    }
}