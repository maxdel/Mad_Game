package core.model.gameplay.skills;

import core.model.gameplay.items.Item;
import core.model.gameplay.units.GameObjectMoving;

public abstract class Skill {

    protected GameObjectMoving owner;

    protected String name;
    protected int castTime;
    protected int currentCastTime;
    protected int cooldownTime;
    protected int currentCooldownTime;

    protected Item requiredItem;
    protected Class requiredItemType;

    protected double requiredHP;
    protected double requiredMP;

    public Skill(GameObjectMoving owner, String name, int castTime, int cooldownTime, Item requiredItem,
                 double requiredHP, double requiredMP) {
        this.owner = owner;
        this.name = name;
        this.castTime = castTime;
        this.currentCastTime = 0;
        this.cooldownTime = cooldownTime;
        this.currentCooldownTime = 0;
        this.requiredItem = requiredItem;
        this.requiredHP = requiredHP;
        this.requiredMP = requiredMP;
    }

    public void runCast() {
        currentCastTime = castTime;
    }

    public void runCD() {
        currentCooldownTime = cooldownTime;
    }

    public void decreasePointsCost(double mp, double hp) {
        owner.getAttribute().getMP().damage(requiredMP);
        owner.getAttribute().getHP().damage(requiredHP);
    }

    public void updateCD(int delta) {
        if (currentCooldownTime > 0) {
            currentCooldownTime -= delta;
            if (currentCooldownTime < 0) {
                currentCooldownTime = 0;
            }
        }
    }
    public void update(int delta) {
        updateCD(delta);
    }

    public boolean canStartCast(boolean reqItemIsNecessary) {
        if (reqItemIsNecessary) {
            if (!owner.getInventory().isItemDressed(requiredItem.getClass())) {
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
                || currentCooldownTime > 0) {
            return false;
        }

        return true;
    }

    public boolean startCast() {
        if (canStartCast(true)) {
            decreasePointsCost(requiredMP, requiredHP);
            runCast();
            runCD();
            return true;
        }
        return false;
    }

    public abstract void cast();

    public int getCurrentCastTime() {
        return currentCastTime;
    }

    public void setCurrentCastTime(int currentCastTime) {
        this.currentCastTime = currentCastTime;
    }

    public String getName() {
        return name;
    }

    public int getCastTime() {
        return castTime;
    }

    public void setCastTime(int castTime) {
        this.castTime = castTime;
    }

    public int getCooldownTime() {
        return cooldownTime;
    }

    public void setCooldownTime(int cooldownTime) {
        this.cooldownTime = cooldownTime;
    }

}