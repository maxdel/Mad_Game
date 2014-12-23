package core.model.gameplay.gameobjects;

import java.util.List;

import core.model.gameplay.items.*;
import core.model.gameplay.skills.SkillInstanceKind;

import core.MathAdv;
import core.resourcemanager.ResourceManager;
import core.model.Timer;
import core.model.gameplay.*;
import core.model.gameplay.skills.Skill;

public abstract class Unit extends GameObjectSolid {

    private double relativeDirection;
    private Attribute attribute;
    protected UnitState currentState;

    private List<LootRecord> lootRecordList;
    protected Inventory inventory;

    private Skill.ISkillAction skillApplyAction;
    private Skill.ISkillAction stealSkillCostAction;

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

    public Unit(double x, double y, double direction, GameObjInstanceKind type) {
        super(x, y, direction, type);

        this.mask = ResourceManager.getInstance().getUnitInfo(type).getMask();

        this.attribute = ResourceManager.getInstance().getUnitInfo(type).getAttribute();
        this.currentState = UnitState.STAND;

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

        if (!attribute.isAlive()) {
            onDelete();
        }
    }

    /**
     * Stops unit
     */
    public void stand() {
        if (currentState == UnitState.MOVE) {
            currentState = UnitState.STAND;
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
        if (currentState == UnitState.STAND || currentState == UnitState.MOVE) {
            currentState = UnitState.MOVE;
            this.relativeDirection = relativeDirection;
            attribute.setCurrentSpeed(attribute.getMaximumSpeed());
        }
    }

    /**
     * Changing direction of unit on @param directionDelta value
     * @param directionDelta angle in radians
     */
    public void rotate(double directionDelta) {
        if (currentState == UnitState.STAND || currentState == UnitState.MOVE) {
            changeDirection(directionDelta);
        }
    }

    /**
     * Tries to start use item
     */
    public void startUseItem() {
        startUseItem(inventory.getSelectedRecord());
    }

    /**
     * Tries to start use item
     */
    public void startUseItem(ItemRecord itemRecord) {
        if (currentState == UnitState.STAND && inventory.getSelectedRecord() != null) {
            currentState = UnitState.ITEM;
            itemToUse = itemRecord;
            useItemTimer.activate(USE_ITEM_TIME);
        }
    }

    /**
     * Uses item
     */
    private void useItem() {
        inventory.useItem(itemToUse);
        itemToUse = null;
        currentState = UnitState.STAND;
    }

    /**
     * Uses item without changing state
     */
    public void fastUseItem(ItemRecord itemRecord) {
        if (currentState == UnitState.STAND || currentState == UnitState.MOVE) {
            inventory.useItem(itemRecord);
        }
    }

    /**
     * Tries to start drop action
     */
    public void startDropItem() {
        if (currentState == UnitState.STAND && inventory.getSelectedRecord() != null) {
            currentState = UnitState.ITEM;
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
        World.getInstance().getGameObjectToAddList().add(loot);
        inventory.deleteItem(inventory.getSelectedRecord().getItem().getInstanceKind());
        currentState = UnitState.STAND;
    }

    /**
     * Updating current Loot itemToPick object
     */
    private void updateItemToPick() {
        if (currentState != UnitState.ITEM) {
            double lookPointX = getX() + MathAdv.lengthDirX(getDirection(), LOOT_RANGE);
            double lookPointY = getY() + MathAdv.lengthDirY(getDirection(), LOOT_RANGE);
            double lookRadius = LOOT_PICK_RADIUS;
            itemToPick = null;
            for (GameObject gameObject : World.getInstance().getGameObjectList()) {
                if (gameObject.getClass() == Loot.class) {
                    Loot loot = (Loot) gameObject;
                    double distanceToLoot = MathAdv.getDistance(lookPointX, lookPointY, loot.getX(), loot.getY());
                    if (distanceToLoot < lookRadius) {
                        lookRadius = distanceToLoot;
                        itemToPick = loot;
                    }
                }
            }
        }
    }

    /**
     * Tries to start pick item
     */
    public void startPickItem() {
        if (currentState == UnitState.STAND && itemToPick != null) {
            currentState = UnitState.ITEM;
            pickItemTimer.activate(PICK_ITEM_TIME);
        }
    }

    /**
     * Tries to pick item and add it to inventory
     * It's possible that someone else picked it during unit 'casting' pick action
     */
    private void pickItem() {
        if (itemToPick != null) {
            inventory.addItem(itemToPick.getItem().getInstanceKind(), itemToPick.getNumber());
            World.getInstance().getGameObjectToDeleteList().add(itemToPick);
            itemToPick = null;
        }
        currentState = UnitState.STAND;
    }

    /**
     * Tries to start casting skill @param skillKind
     * @param skillInstanceKind is a concrete skill
     */
    public void startCastSkill(SkillInstanceKind skillInstanceKind) {
        Skill skillToCast = getSkillByKind(skillInstanceKind);
        if (isReadyToCast() && skillToCast != null && canStartCast(skillToCast) ) {
            stand();
            currentState = UnitState.SKILL;

            castingSkill = skillToCast;
            skillApplyAction = castingSkill.getToApplyAction();
            stealSkillCostAction = castingSkill.getStealSkillCostAction();

            preApplyCastingTimer.activate(castingSkill.getPreApplyTime());
            castingTimer.activate(castingSkill.getCastTime());

            stealSkillCostAction.realized(this);
        }
    }

    public boolean isReadyToCast() {
        return currentState == UnitState.STAND || currentState == UnitState.MOVE;
    }

    public Skill getSkillByKind(SkillInstanceKind skillInstanceKind) {
        for (Skill skill : skillList) {
            if (skill.getKind() == skillInstanceKind) {
                return skill;
            }
        }
        return null;
    }

    /**
     * Finishes casting skill and bring unit to STAND state
     */
    private void onCastingFinish() {
        castingSkill.activateCooldownTimer();
        castingSkill = null;
        currentState = UnitState.STAND;
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
        World.getInstance().getGameObjectToDeleteList().add(this);
        for (LootRecord lootRecord : lootRecordList) {
            Loot loot = lootRecord.generateLoot(getX() - 10 + Math.random() * 20, getY() - 10 + Math.random() * 20);
            if (loot != null) {
                World.getInstance().getGameObjectToAddList().add(loot);
            }
        }
    }


    /**
     * Returns true if the skill can casting with current owner conditions
     *
     * @param skillToCast is a skill, that probably will cast
     * @return true if an owner can start casting skill
     */
    public boolean canStartCast(Skill skillToCast) {
        if (skillToCast == null) {
            throw new NullPointerException("skillToCast in method canStartCast of " + getType());
        }

        if (skillToCast.getRequiredItem() != null &&
                !inventory.isItemClassDressed(skillToCast.getRequiredItem().getClass())) {
            return false;
        }

        if (skillToCast.getTimerCooldown().isActive()) {
            return false;
        }

        if (skillToCast.getRequiredItem() != null && skillToCast.getRequiredItem().getClass() == Bow.class) {
            if (!inventory.isItemClassExists(ArrowItem.class)) {
                return false;
            }
        }

        if (attribute.getCurrentMP() < skillToCast.getRequiredMP() ||
                attribute.getCurrentHP() < skillToCast.getRequiredHP()) {
            return false;
        }

        return true;
    }

    /* Getters and setters region */
    @Override
    public void setDirection(double direction) {
        if (currentState == UnitState.STAND || currentState == UnitState.MOVE) {
            super.setDirection(direction);
        }
    }

    public void setRelativeDirection(double relativeDirection) {
        if (currentState == UnitState.STAND || currentState == UnitState.MOVE) {
            this.relativeDirection = relativeDirection;
        }
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public double getRelativeDirection() {
        return relativeDirection;
    }

    public UnitState getCurrentState() {
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

    public int getCurrentSkillCastingTime() {
        return castingTimer.getValue();
    }

}