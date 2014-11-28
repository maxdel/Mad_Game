package core.model.gameplay;

import core.model.gameplay.inventory.Inventory;
import core.model.gameplay.inventory.ItemRecord;
import org.newdawn.slick.geom.Circle;

import core.ResourceManager;
import org.newdawn.slick.geom.Vector2f;

/**
 * Represents hero
 * */
public class Hero extends GameObjectMoving {

    private Inventory inventory;
    private Loot selectedLoot;
    private ItemRecord usingItem;
    
    private int pickLootCounter;
    private final int pickLootTime = 300;
    private int dropLootCounter;
    private final int dropLootTime = 500;
    private int useItemCounter;
    private final int useItemTime = 400;

    public Hero(double x, double y, double maximumSpeed) {
        super(x, y,  maximumSpeed);
        setMask(new Circle(0, 0, ResourceManager.getInstance().getMaskRadius("hero")));
        inventory = new Inventory(this);
        selectedLoot = null;
        usingItem = null;
        pickLootCounter = 0;
        dropLootCounter = 0;
        useItemCounter = 0;
    }

    public void walk(double direction) {
        if (getCurrentState() != GameObjectState.PICK_ITEM && getCurrentState() != GameObjectState.DROP_ITEM) {
            setCurrentState(GameObjectState.WALK);
            getAttribute().setCurrentSpeed(getAttribute().getMaximumSpeed() / 2);
            setRelativeDirection(direction);
        }
    }

    public void run(double direction) {
        if (getCurrentState() != GameObjectState.PICK_ITEM && getCurrentState() != GameObjectState.DROP_ITEM) {
            setCurrentState(GameObjectState.RUN);
            getAttribute().setCurrentSpeed(getAttribute().getMaximumSpeed());
            setRelativeDirection(direction);
        }
    }

    public void stand() {
        if (getCurrentState() != GameObjectState.PICK_ITEM && getCurrentState() != GameObjectState.DROP_ITEM) {
            setCurrentState(GameObjectState.STAND);
            getAttribute().setCurrentSpeed(0);
        }
    }

    public void rotate(double angleOffset) {
        if (getCurrentState() != GameObjectState.PICK_ITEM && getCurrentState() != GameObjectState.DROP_ITEM) {
            setDirection(getDirection() + angleOffset);
        }
    }

    public void startDropItem() {
        if (inventory.getSelectedRecord() != null && getCurrentState() != GameObjectState.PICK_ITEM &&
                getCurrentState() != GameObjectState.DROP_ITEM) {
            setCurrentState(GameObjectState.DROP_ITEM);
            getAttribute().setCurrentSpeed(0);
            dropLootCounter = dropLootTime;
        }
    }

    private void dropItem() {
        Loot loot = new Loot(getX() + lengthDirX(getDirection(), 50), getY() + lengthDirY(getDirection(), 50),
                getDirection(), inventory.getSelectedRecord().getItem(), 1);
        World.getInstance().getLootList().add(loot);
        inventory.deleteItem(inventory.getSelectedRecord().getName(), 1);
        setCurrentState(GameObjectState.STAND);
    }

    public void startPickItem() {
        if (selectedLoot != null && getCurrentState() != GameObjectState.DROP_ITEM &&
                getCurrentState() != GameObjectState.PICK_ITEM) {
            setCurrentState(GameObjectState.PICK_ITEM);
            getAttribute().setCurrentSpeed(0);
            pickLootCounter = pickLootTime;
        }
    }

    private void pickItem() {
        if (selectedLoot != null) {
            inventory.addItem(selectedLoot.getItem().getName(), selectedLoot.getNumber());
            World.getInstance().getLootList().remove(selectedLoot);
            selectedLoot = null;
            setCurrentState(GameObjectState.STAND);
        }
    }

    private void updateCurrentLoot() {
        if (getCurrentState() != GameObjectState.PICK_ITEM) {
            double lookPointX = getX() + lengthDirX(getDirection(), 50);
            double lookPointY = getY() + lengthDirY(getDirection(), 50);
            double currentLength = 30;
            for (Loot loot : World.getInstance().getLootList()) {
                Vector2f vector = new Vector2f((float) (loot.getX() - lookPointX), (float) (loot.getY() - lookPointY));
                if (vector.length() < 30 && vector.length() < currentLength) {
                    currentLength = vector.length();
                    selectedLoot = loot;
                }
            }
            if (currentLength == 30) {
                selectedLoot = null;
            }
        }
    }

    public void startUseItem() {
        if (inventory.getSelectedRecord() != null && getCurrentState() != GameObjectState.PICK_ITEM &&
                getCurrentState() != GameObjectState.DROP_ITEM &&
                getCurrentState() != GameObjectState.USE_ITEM) {
            setCurrentState(GameObjectState.USE_ITEM);
            getAttribute().setCurrentSpeed(0);
            usingItem = inventory.getSelectedRecord();
            useItemCounter = useItemTime;
        }
    }

    private void useItem(){
        if (inventory.useItem(usingItem)) {
            setCurrentState(GameObjectState.STAND);
        }
        usingItem = null;
    }

    @Override
    public void update(int delta) {
        if (dropLootCounter > 0) {
            dropLootCounter -= delta;
            if (dropLootCounter <= 0) {
                dropLootCounter = 0;
                dropItem();
            }
        }

        if (pickLootCounter > 0) {
            pickLootCounter -= delta;
            if (pickLootCounter <= 0) {
                pickLootCounter = 0;
                pickItem();
            }
        }

        if (useItemCounter > 0) {
            useItemCounter -= delta;
            if (useItemCounter <= 0) {
                useItemCounter = 0;
                useItem();
            }
        }

        updateCurrentLoot();

        super.update(delta);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Loot getSelectedLoot() {
        return selectedLoot;
    }

}