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
    }

    public void walk(final double direction) {
        if (getCurrentState() != GameObjectState.PICK) {
            setCurrentState(GameObjectState.WALK);
            setCurrentSpeed(getMaximumSpeed() / 2);
            setRelativeDirection(direction);
        }
    }

    public void run(final double direction) {
        if (getCurrentState() != GameObjectState.PICK) {
            setCurrentState(GameObjectState.RUN);
            setCurrentSpeed(getMaximumSpeed());
            setRelativeDirection(direction);
        }
    }

    public void stand() {
        if (getCurrentState() != GameObjectState.PICK) {
            setCurrentState(GameObjectState.STAND);
            setCurrentSpeed(0);
        }
    }

    public void rotate(final double angleOffset) {
        if (getCurrentState() != GameObjectState.PICK) {
            setDirection(getDirection() + angleOffset);
        }
    }

    public void startPick() {
        if (selectedLoot != null) {
            setCurrentState(GameObjectState.PICK);
            setCurrentSpeed(0);
            pickLootCounter = pickLootTime;
        }
    }

    private void pick() {
        if (selectedLoot != null) {
            inventory.addItem(selectedLoot.getItem().getName(), selectedLoot.getNumber());
            World.getInstance().getLootList().remove(selectedLoot);
            selectedLoot = null;
            setCurrentState(GameObjectState.STAND);
        }
    }

    private void updateCurrentLoot() {
        if (getCurrentState() != GameObjectState.PICK) {
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
        if (pickLootCounter > 0) {
            pickLootCounter -= delta;
            if (pickLootCounter <= 0) {
                pickLootCounter = 0;
                pick();
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