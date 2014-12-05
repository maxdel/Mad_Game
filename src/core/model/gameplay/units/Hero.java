package core.model.gameplay.units;

import core.model.gameplay.*;
import core.model.gameplay.items.ItemDB;
import core.model.gameplay.items.Loot;
import core.model.gameplay.skills.*;
import org.newdawn.slick.geom.Circle;

import core.resource_manager.ResourceManager;
import org.newdawn.slick.geom.Vector2f;

/**
 * Represents hero
 * */
public class Hero extends GameObjectMoving {

    private Loot selectedLoot;
    
    private int pickLootCounter;
    private final int pickLootTime = 300;
    private int dropLootCounter;
    private final int dropLootTime = 500;

    // TODO for fun
    public int level = 1;
    public int kills = 0;

    public Hero(double x, double y, double maximumSpeed) {
        super(x, y,  maximumSpeed);
        setDirection(Math.PI);
        setMask(new Circle(0, 0, ResourceManager.getInstance().getMaskRadius("hero")));
        selectedLoot = null;
        usingItem = null;
        pickLootCounter = 0;
        dropLootCounter = 0;
        useItemCounter = 0;
        skillList.add(new AreaSkill(this, "Sword attack", 200, 1000, ItemDB.getInstance().getItem("Sword"), 0, 0, 30, 0, 70, Math.PI / 3));
        skillList.add(new BulletSkill(this, "Bow shot", 200, 1000, ItemDB.getInstance().getItem("Bow"), 0, 0, 0.5, 15, 0));
        skillList.add(new BulletSkill(this, "Fireball", 200, 1000, ItemDB.getInstance().getItem("Staff"), 0, 10, 0.4, 0, 60));
        skillList.add(new AreaSkill(this, "Strong attack", 400, 2000, ItemDB.getInstance().getItem("Sword"), 0, 10, 50, 5, 100, Math.PI / 2));
        skillList.add(new BuffSkill(this, "Bow speed", 200, 10000, null, 0, 10, 5000, "Bow shot", 150, 950));
        skillList.add(new RegenSkill(this, "Heal", 200, 1000, null, 0, 10, 20));
        skillList.add(new BulletSkill(this, "Fireball", 200, 1000, ItemDB.getInstance().getItem("Staff"), 0, 10, 0.4, 0, 60));
        skillList.add(new AreaSkill(this, "Staff attack", 200, 1000, ItemDB.getInstance().getItem("Staff"), 0, 0, 5, 1, 100, Math.PI / 3));

    }

    public void walk(double direction) {
        if (getCurrentState() == GameObjectState.STAND || getCurrentState() == GameObjectState.RUN ||
                getCurrentState() == GameObjectState.WALK) {
            setCurrentState(GameObjectState.WALK);
            getAttribute().setCurrentSpeed(getAttribute().getMaximumSpeed() / 2);
            setRelativeDirection(direction);
        }
    }

    public void run(double direction) {
        if (getCurrentState() == GameObjectState.STAND || getCurrentState() == GameObjectState.WALK ||
                getCurrentState() == GameObjectState.RUN) {
            setCurrentState(GameObjectState.RUN);
            getAttribute().setCurrentSpeed(getAttribute().getMaximumSpeed());
            setRelativeDirection(direction);
        }
    }

    public void stand() {
        if (getCurrentState() == GameObjectState.RUN || getCurrentState() == GameObjectState.WALK) {
            setCurrentState(GameObjectState.STAND);
            getAttribute().setCurrentSpeed(0);
        }
    }

    public void rotate(double angleOffset) {
        if (getCurrentState() == GameObjectState.STAND || getCurrentState() == GameObjectState.WALK ||
                getCurrentState() == GameObjectState.RUN) {
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

        updateCurrentLoot();

        super.update(delta);
    }

    public Loot getSelectedLoot() {
        return selectedLoot;
    }

}