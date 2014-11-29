package core.model.gameplay;

import org.newdawn.slick.geom.Vector2f;

public abstract class Skill {

    protected GameObjectMoving owner;

    protected String name;
    protected int castTime;
    protected int currentCastTime;
    protected int cooldownTime;
    protected int currentCooldownTime;

    protected String requiredItem;

    protected double requiredHP;
    protected double requiredMP;

    public Skill(GameObjectMoving owner, String name, int castTime, int cooldownTime, String requiredItem,
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

    protected void update(int delta) {
        if (currentCooldownTime > 0) {
            currentCooldownTime -= delta;
            if (currentCooldownTime < 0) {
                currentCooldownTime = 0;
            }
        }
    }

    protected abstract boolean startCast();

    protected abstract void cast();

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