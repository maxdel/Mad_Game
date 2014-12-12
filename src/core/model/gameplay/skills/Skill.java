package core.model.gameplay.skills;

import core.model.Timer;
import core.model.gameplay.items.Item;
import core.model.gameplay.items.ItemDB;
import core.model.gameplay.gameobjects.Unit;

import java.security.acl.Owner;

public abstract class Skill {

    protected SkillKind kind;

    protected String name;
    protected String description;
    protected int castTime;

    protected int cooldownTime;
    protected Timer timerCooldown;

    protected int preApplyTime;

    protected Item requiredItem;

    protected double requiredHP;
    protected double requiredMP;

    public Skill(String name, String description, int castTime, int postApplyTime,
                 int cooldownTime, String requiredItem, double requiredHP, double requiredMP, SkillKind kind) {
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
     * Just description of actions, that this skill can do
     */
    protected abstract void apply(Unit owner);

    /**
     * Type of function, that Owner will call
     */
    @FunctionalInterface
    public interface ISkillAction {
        void realized(Unit owner);
    }

    /**
     * Returns actions, that owner will apply using this skill
     * @return apply function
     */
    public ISkillAction getToApplyAction() {
        return this::apply;
    }


    /**
     * Returns actions, that owner will apply using this skill
     * @return apply function
     */
    public ISkillAction getStealSkillCostAction() {
        return this::stealSkillCost;
    }

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
    public void stealSkillCost(Unit owner) {
        owner.getAttribute().getMP().damage(requiredMP);
        owner.getAttribute().getHP().damage(requiredHP);
    }

    /**
     * Changes primary cooldown time of unit on @param deltaCooldownTime value
     * @param deltaCooldownTime is passed time in milliseconds
     */
    public void changeCooldownTime(int deltaCooldownTime) {
        cooldownTime += deltaCooldownTime;
    }

    /* Getters and setters region */
    public int getCastTime() {
        return castTime;
    }

    public void setCastTime(int castTime) {
        this.castTime = castTime;
    }

    public Item getRequiredItem() {
        return requiredItem;
    }

    public double getRequiredHP() {
        return requiredHP;
    }

    public double getRequiredMP() {
        return requiredMP;
    }

    public int getPreApplyTime() {
        return preApplyTime;
    }

    public SkillKind getKind() {
        return kind;
    }

    public Timer getTimerCooldown() {
        return timerCooldown;
    }
}