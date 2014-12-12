package core.model.gameplay.skills;

import core.model.Timer;
import core.model.gameplay.items.Item;
import core.model.gameplay.items.ItemDB;
import core.model.gameplay.gameobjects.Unit;

public abstract class Skill {

    protected SkillKind kind;

    protected Unit owner;

    protected String name;
    protected String description;
    protected int castTime;

    protected int cooldownTime;
    protected Timer timerCooldown;

    protected int preApplyTime;

    protected Item requiredItem;

    protected double requiredHP;
    protected double requiredMP;

    public Skill(Unit owner, String name, String description, int castTime, int postApplyTime,
                 int cooldownTime, String requiredItem, double requiredHP, double requiredMP, SkillKind kind) {
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.castTime = castTime;
        this.cooldownTime = cooldownTime;
        this.requiredItem = ItemDB.getInstance().getItem(requiredItem);
        this.requiredHP = requiredHP;
        this.requiredMP = requiredMP;
        this.preApplyTime = castTime - postApplyTime;
        this.kind = kind;

        timerCooldown = new Timer();
    }

    /**
     * Updates skill according to passed time
     * @param delta passed time in milliseconds
     */
    public void update(int delta) {
        timerCooldown.update(delta);
    }

    /**
     * Applies skill to owner
     */
    public abstract void apply();

    /**
     * Activates cooldown timer with skill cooldown time
     */
    public void activateCooldownTimer() {
        timerCooldown.activate(cooldownTime);
    }

    /**
     * Skills have the health and magic points cost, that will still from unit,
     * when unit successfully uses skill
     */
    public void decreasePointsCost() {
        owner.getAttribute().getMP().damage(requiredMP);
        owner.getAttribute().getHP().damage(requiredHP);
    }

    /**
     * Returns true if the skill can casting with current owner conditions
     * @param reqItemIsNecessary shows if owner must be wearing some item
     * @return true if an owner can start this skill
     */
    public boolean canStartCast(boolean reqItemIsNecessary) {
        if (reqItemIsNecessary) {
            if (requiredItem != null && !owner.getInventory().isItemDressed(requiredItem.getClass())) {
                return false;
            }
        }

        if (!reqItemIsNecessary) {
            if (!owner.getInventory().isItemDressed(requiredItem.getClass()) && requiredItem != null) {
                return false;
            }
        }

        if (owner.getAttribute().getMP().getCurrent() < requiredMP
                || owner.getAttribute().getHP().getCurrent() < requiredHP
                || timerCooldown.isActive()) {
            return false;
        }

        return true;
    }

    /**
     * Changes primary cooldown time of unit on @param deltaCooldownTime value
     * @param deltaCooldownTime is passed time in milliseconds
     */
    public void changeCooldownTime(int deltaCooldownTime) {
        cooldownTime += deltaCooldownTime;
    }

    /* Getters and setters region */
  /*  public String getName() {
        return name;
    }
*/    public int getCastTime() {
        return castTime;
    }

    public void setCastTime(int castTime) {
        this.castTime = castTime;
    }

    public void setOwner(Unit owner) {
        this.owner = owner;
    }

    public int getPreApplyTime() {
        return preApplyTime;
    }

    public SkillKind getKind() {
        return kind;
    }
}