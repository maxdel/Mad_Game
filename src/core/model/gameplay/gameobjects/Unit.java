package core.model.gameplay.gameobjects;

import java.util.List;

import org.newdawn.slick.geom.Vector2f;

import core.MathAdv;
import core.model.gameplay.items.LootRecord;
import core.resourcemanager.ResourceManager;
import core.model.Timer;
import core.model.gameplay.*;
import core.model.gameplay.items.Inventory;
import core.model.gameplay.items.ItemRecord;
import core.model.gameplay.items.Loot;
import core.model.gameplay.skills.Skill;

public abstract class Unit extends GameObjectSolid {

    private double relativeDirection;
    private Attribute attribute;
    private GameObjectState currentState;

    private List<LootRecord> lootRecordList;
    private Inventory inventory;

    private Skill.ISkillAction<Unit> skillApplyAction;
    private Skill.ISkillAction<Unit> stealSkillCostAction;

    private ItemRecord itemToUse;
    private ItemRecord itemToDrop;
    private Loot itemToPick;

    private Timer useItemTimer;
    private Timer dropItemTimer;
    private Timer pickItemTimer;

    private final int USE_ITEM_TIME = 300;
    private final int DROP_ITEM_TIME = 300;
    private final int PICK_ITEM_TIME = 300;
    private final int LOOT_RANGE = 50;
    private final int LOOT_PICK_RADIUS = 30;

    private List<Skill> skillList;
    private Skill castingSkill;
    private Timer preApplyCastingTimer;
    private Timer castingTimer;

    public Unit(double x, double y, double direction, GameObjectSolidType type) {
        super(x, y, direction, type);

        this.attribute = ResourceManager.getInstance().getUnitInfo(type).getAttribute();
        this.currentState = GameObjectState.STAND;

        this.lootRecordList = ResourceManager.getInstance().getUnitInfo(type).getLootRecordList();
        this.inventory = ResourceManager.getInstance().getUnitInfo(type).getInventory(this);
        this.skillList = ResourceManager.getInstance().getUnitInfo(type).getSkilLList();

        this.useItemTimer = new Timer();
        this.dropItemTimer = new Timer();
        this.pickItemTimer = new Timer();

        this.preApplyCastingTimer = new Timer();
        this.castingTimer = new Timer();
    }

    /**
     * Updates unit according to passed time
     * @param delta is passed time in milliseconds
     */
    @Override
    public void update(int delta) {
        /* Update actions with current item */
        if (useItemTimer.update(delta)) {
            useItem();
        }
        if (dropItemTimer.update(delta)) {
            dropItem();
        }
        if (pickItemTimer.update(delta)) {
            pickItem();
        }
        updateItemToPick();
        // ---------------------------------

        /* Update actions with current skill */
        if (preApplyCastingTimer.update(delta)) {
            skillApplyAction.realized(this);
        }

        if (castingTimer.update(delta)) {
            onCastingFinish();
        }
        // ---------------------------------

        updateSkills(delta);

        updatePosition(delta);

        if (attribute.hpAreEnded()) {
            onDelete();
        }
    }

    /**
     * Stops unit
     */
    public void stand() {
        if (currentState == GameObjectState.MOVE) {
            currentState = GameObjectState.STAND;
            attribute.setCurrentSpeed(0);
        }
    }

    /**
     * Moves unit at its maximum speed with 0 relativeDirection
     */
    public void move() {
        move(0);
    }

    /**
     * Moves unit at its maximum speed using @param relativeDirection
     * @param relativeDirection angle in radians
     */
    public void move(double relativeDirection) {
        if (currentState == GameObjectState.STAND || currentState == GameObjectState.MOVE) {
            currentState = GameObjectState.MOVE;
            this.relativeDirection = relativeDirection;
            attribute.setCurrentSpeed(attribute.getMaximumSpeed());
        }
    }

    /**
     * Changing direction of unit on @param directionDelta value
     * @param directionDelta angle in radians
     */
    public void rotate(double directionDelta) {
        if (currentState == GameObjectState.STAND || currentState == GameObjectState.MOVE) {
            changeDirection(directionDelta);
        }
    }

    /**
     * Tries to start use item
     */
    public void startUseItem() {
        if (currentState == GameObjectState.STAND && inventory.getSelectedRecord() != null) {
            currentState = GameObjectState.ITEM;
            itemToUse = inventory.getSelectedRecord();
            useItemTimer.activate(USE_ITEM_TIME);
        }
    }

    /**
     * Uses item
     */
    private void useItem() {
        inventory.useItem(itemToUse);
        itemToUse = null;
        currentState = GameObjectState.STAND;
    }

    /**
     * Tries to start drop action
     */
    public void startDropItem() {
        if (currentState == GameObjectState.STAND && inventory.getSelectedRecord() != null) {
            currentState = GameObjectState.ITEM;
            itemToDrop = inventory.getSelectedRecord();
            dropItemTimer.activate(DROP_ITEM_TIME);
        }
    }

    /**
     * Drops itemToDrop
     */
    private void dropItem() {
        Loot loot = new Loot(getX() + MathAdv.lengthDirX(getDirection(), LOOT_RANGE),
                getY() + MathAdv.lengthDirY(getDirection(), LOOT_RANGE),
                itemToDrop.getItem());
        World.getInstance().getLootList().add(loot);
        inventory.deleteItem(inventory.getSelectedRecord().getName());
        currentState = GameObjectState.STAND;
    }

    /**
     * Updating current Loot itemToPick object
     */
    private void updateItemToPick() {
        if (currentState != GameObjectState.ITEM) {
            double lookPointX = getX() + MathAdv.lengthDirX(getDirection(), LOOT_RANGE);
            double lookPointY = getY() + MathAdv.lengthDirY(getDirection(), LOOT_RANGE);
            double lookRadius = LOOT_PICK_RADIUS;
            itemToPick = null;
            for (Loot loot : World.getInstance().getLootList()) {
                Vector2f distanceToLoot =
                        new Vector2f((float) (loot.getX() - lookPointX), (float) (loot.getY() - lookPointY));
                if (distanceToLoot.length() < lookRadius) {
                    lookRadius = distanceToLoot.length();
                    itemToPick = loot;
                }
            }
        }
    }

    /**
     * Tries to start pick item
     */
    public void startPickItem() {
        if (currentState == GameObjectState.STAND && itemToPick != null) {
            currentState = GameObjectState.ITEM;
            pickItemTimer.activate(PICK_ITEM_TIME);
        }
    }

    /**
     * Tries to pick item and add it to inventory
     * It's possible that someone else picked it during unit 'casting' pick action
     */
    private void pickItem() {
        if (itemToPick != null) {
            inventory.addItem(itemToPick.getItem().getName(), itemToPick.getNumber());
            World.getInstance().getLootList().remove(itemToPick);
            itemToPick = null;
        }
        currentState = GameObjectState.STAND;
    }

    /**
     * TODO: Change parameter from int to ENUM
     * Tries to start casting skill @param skillIndex
     * @param skillIndex bad param :(
     */
    public void startCastSkill(int skillIndex) {
        Skill skillToCast = skillList.get(skillIndex);
        if (((currentState == GameObjectState.STAND || currentState == GameObjectState.MOVE)) &&
                skillToCast != null && canStartCast(skillToCast, true) ) {
            stand();
            currentState = GameObjectState.SKILL;

            castingSkill = skillToCast;
            skillApplyAction = castingSkill.getToApplyAction();
            stealSkillCostAction = castingSkill.getStealSkillCostAction();

            preApplyCastingTimer.activate(castingSkill.getPreApplyTime());
            castingTimer.activate(castingSkill.getCastTime());

            stealSkillCostAction.realized(this);
          }
    }

    /**
     * Finishes casting skill and bring unit to STAND state
     */
    private void onCastingFinish() {
        castingSkill.activateCooldownTimer();
        castingSkill = null;
        currentState = GameObjectState.STAND;
    }

    /**
     * Updates coordinates according to current speed, directions and free place around unit
     * @param delta milliseconds from last step
     */
    private void updatePosition(int delta) {
        double length, direction, lengthDirX, lengthDirY;
        length = attribute.getCurrentSpeed() * delta;
        direction = getDirection() + getRelativeDirection();

        lengthDirX = MathAdv.lengthDirX(direction, length);
        lengthDirY = MathAdv.lengthDirY(direction, length);

        if (CollisionManager.getInstance().isPlaceFreeAdv(this, getX() + lengthDirX, getY() + lengthDirY)) {
            changeX(lengthDirX);
            changeY(lengthDirY);
        } else {
            double stepAngle = 10 * Math.PI / 180;
            double altDirection = direction;
            for (int i = 0; Math.abs(altDirection - direction) < Math.PI / 2; ++i) {
                altDirection += i * stepAngle * (i % 2 == 0 ? 1 : -1);
                lengthDirX = MathAdv.lengthDirX(altDirection, length * Math.cos(altDirection - direction));
                lengthDirY = MathAdv.lengthDirY(altDirection, length * Math.cos(altDirection - direction));
                if (CollisionManager.getInstance().isPlaceFreeAdv(this, getX() + lengthDirX, getY() + lengthDirY)) {
                    changeX(lengthDirX);
                    changeY(lengthDirY);
                    break;
                }
            }
        }
    }

    /**
     * Updates skills of this unit in skillList
     * @param delta milliseconds from last step
     */
    private void updateSkills(int delta) {
        for (Skill skill : skillList) {
            skill.update(delta);
        }
    }

    /**
     * Actions to do on delete event
     */
    protected void onDelete() {
        World.getInstance().getToDeleteList().add(this);
        for (LootRecord lootRecord : lootRecordList) {
            Loot loot = lootRecord.generateLoot(getX() - 10 + Math.random() * 20, getY() - 10 + Math.random() * 20);
            if (loot != null) {
                World.getInstance().getLootList().add(loot);
            }
        }
    }


    /** MUST BU REFACTORED
     * Returns true if the skill can casting with current owner conditions
     *
     * @param skillToCast is a skill, that probably will cast
     * @param reqItemIsNecessary shows if owner must be wearing some item
     * @return true if an owner can start casting skill
     */
    public boolean canStartCast(Skill skillToCast, boolean reqItemIsNecessary) {
        if (reqItemIsNecessary) {
            if (skillToCast.getRequiredItem() != null &&
                    !inventory.isItemDressed(skillToCast.getRequiredItem().getClass())) {
                return false;
            }
        }

        if (!reqItemIsNecessary) {
            if (!inventory.isItemDressed(skillToCast.getRequiredItem()) &&
                    skillToCast.getRequiredItem() != null) {
                return false;
            }
        }

        if (attribute.getMP().getCurrent() < skillToCast.getRequiredMP()
                || attribute.getHP().getCurrent() < skillToCast.getRequiredHP()
                || skillToCast.getTimerCooldown().isActive()) {
            return false;
        }

        return true;
    }

    /* Getters and setters region */
    @Override
    public void setDirection(double direction) {
        if (currentState == GameObjectState.STAND || currentState == GameObjectState.MOVE) {
            super.setDirection(direction);
        }
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public double getRelativeDirection() {
        return relativeDirection;
    }

    public GameObjectState getCurrentState() {
        return currentState;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public List<Skill> getSkillList() {
        return skillList;
    }

    public Skill getCastingSkill() {
        return castingSkill;
    }

    public Loot getItemToPick() {
        return itemToPick;
    }


}