package core.model.gameplay;

import core.model.gameplay.inventory.Inventory;
import org.newdawn.slick.geom.Circle;

import core.ResourceManager;
import org.newdawn.slick.geom.Vector2f;

/**
 * Contains fields to define the hero state
 * */
public class Hero extends GameObjectMoving {

    private Inventory inventory;
    private Loot selectedLoot;
    private int pickLootCounter;
    private final int pickLootTime = 300;
    private int dropLootCounter;
    private final int dropLootTime = 500;

/*
    public Hero(final double x, final double y, final double direction, final double relativeDirection, final double maximumSpeed,
                final double currentSpeed, final GameObjectState currentState) {
        super(x, y, direction, relativeDirection, maximumSpeed, currentSpeed, currentState);
    }
*/
    public Hero(final double x, final double y, final double direction, final double maximumSpeed) {
        super(x, y, direction, maximumSpeed);
        setMask(new Circle(0, 0, ResourceManager.getInstance().getMaskRadius("hero")));
        inventory = new Inventory();
        selectedLoot = null;
        pickLootCounter = 0;
        dropLootCounter = 0;
    }

    public void walk(final double direction) {
        if (getCurrentState() != GameObjectState.PICK_ITEM && getCurrentState() != GameObjectState.DROP_ITEM) {
            setCurrentState(GameObjectState.WALK);
            setCurrentSpeed(getMaximumSpeed() / 2);
            setRelativeDirection(direction);
        }
    }

    public void run(final double direction) {
        if (getCurrentState() != GameObjectState.PICK_ITEM && getCurrentState() != GameObjectState.DROP_ITEM) {
            setCurrentState(GameObjectState.RUN);
            setCurrentSpeed(getMaximumSpeed());
            setRelativeDirection(direction);
        }
    }

    public void stand() {
        if (getCurrentState() != GameObjectState.PICK_ITEM && getCurrentState() != GameObjectState.DROP_ITEM) {
            setCurrentState(GameObjectState.STAND);
            setCurrentSpeed(0);
        }
    }

    public void rotate(final double angleOffset) {
        if (getCurrentState() != GameObjectState.PICK_ITEM && getCurrentState() != GameObjectState.DROP_ITEM) {
            setDirection(getDirection() + angleOffset);
        }
    }

    public void startDropItem() {
        if (inventory.getItemRecords().size() > 0 && getCurrentState() != GameObjectState.PICK_ITEM &&
                getCurrentState() != GameObjectState.DROP_ITEM) {
            setCurrentState(GameObjectState.DROP_ITEM);
            setCurrentSpeed(0);
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
            setCurrentSpeed(0);
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

    @Override
    public void update(final int delta) {
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