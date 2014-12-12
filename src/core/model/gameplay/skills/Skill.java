package core.model.gameplay.skills;

import core.model.Timer;
import core.model.gameplay.items.Item;
import core.model.gameplay.items.ItemDB;
import core.model.gameplay.gameobjects.Unit;


public abstract class Skill {

    protected Unit owner;

    protected String name;
    protected String description;
    protected int castTime;


    protected int cooldownTime;
    protected Timer timerCooldown = new Timer();

    protected int preApplyTime;

    protected Item requiredItem;

    protected double requiredHP;
    protected double requiredMP;

    public Skill(Unit owner, String name, String description, int castTime, int postApplyTime, int cooldownTime, String requiredItem,
                 double requiredHP, double requiredMP) {
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.castTime = castTime;
        this.cooldownTime = cooldownTime;
        this.requiredItem = ItemDB.getInstance().getItem(requiredItem);
        this.requiredHP = requiredHP;
        this.requiredMP = requiredMP;
        this.preApplyTime = castTime - postApplyTime;
    }

    public void update(int delta) {
        timerCooldown.update(delta);
    }

    public void activateCooldownTimer() {
        timerCooldown.activate(cooldownTime);
    }

    public void decreasePointsCost() {
        owner.getAttribute().getMP().damage(requiredMP);
        owner.getAttribute().getHP().damage(requiredHP);
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
                || timerCooldown.isActive()) {
            return false;
        }

        return true;
    }
    public abstract void apply();

    public String getName() {
        return name;
    }

    public int getCastTime() {
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

    public void changeCooldownTime(int deltaCooldownTime) {
        cooldownTime += deltaCooldownTime;
    }
}