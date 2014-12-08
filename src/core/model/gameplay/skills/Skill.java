package core.model.gameplay.skills;

import core.model.gameplay.items.Item;
import core.model.gameplay.items.ItemDB;
import core.model.gameplay.units.Unit;

public abstract class Skill {

    protected Unit owner;

    protected String name;
    protected String description;
    protected int castTime;
    protected int currentCastTime;
    protected int cooldownTime;
    protected int currentCooldownTime;

    protected int preApplyTime;
    protected boolean alreadyApplied;

    protected Item requiredItem;

    protected double requiredHP;
    protected double requiredMP;

    public Skill(Unit owner, String name, String description, int castTime, int postApplyTime, int cooldownTime, String requiredItem,
                 double requiredHP, double requiredMP) {
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.castTime = castTime;
        this.currentCastTime = 0;
        this.cooldownTime = cooldownTime;
        this.currentCooldownTime = 0;
        this.requiredItem = ItemDB.getInstance().getItem(requiredItem);
        this.requiredHP = requiredHP;
        this.requiredMP = requiredMP;
        this.preApplyTime = castTime - postApplyTime;
    }

    public boolean isTimeToApply() {
        return currentCastTime <= preApplyTime && alreadyApplied == false;
    }

    public boolean isCastingСontinues() {
        return currentCastTime > 0;
    }

    public void stopCasting() {
        currentCastTime = 0;
    }

    public boolean isCastingFinished() {
        return currentCastTime <= 0;
    }

    public void tickCastingTime(int delta) {
        currentCastTime -= delta;
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

    public abstract void apply();

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

    public void setOwner(Unit owner) {
        this.owner = owner;
    }

    public int getPreApplyTime() {
        return preApplyTime;
    }

    public void setAlreadyApplied(boolean alreadyApplied) {
        this.alreadyApplied = alreadyApplied;
    }

}