package core.model.gameplay.skills;

import core.model.Timer;
import core.model.gameplay.items.Item;
import core.model.gameplay.items.ItemDB;
import core.model.gameplay.gameobjects.Unit;
import core.model.gameplay.items.ItemInstanceKind;

public abstract class Skill {

    protected SkillInstanceKind kind;

    protected String name;
    protected String description;
    protected int castTime;

    protected int cooldownTime;
    protected Timer timerCooldown;

    protected Timer timerAterCooldown;

    protected int preApplyTime;

    protected Item requiredItem;

    protected double requiredHP;
    protected double requiredMP;

    public Skill(String name, String description, int castTime, int postApplyTime,
                 int cooldownTime, ItemInstanceKind requiredItemKind, double requiredHP, double requiredMP, SkillInstanceKind kind) {
        this.name = name;
        this.description = description;
        this.castTime = castTime;
        this.cooldownTime = cooldownTime;
        this.requiredItem = ItemDB.getInstance().getItem(requiredItemKind);
        this.requiredHP = requiredHP;
        this.requiredMP = requiredMP;
        this.preApplyTime = castTime - postApplyTime;
        this.kind = kind;

        timerCooldown = new Timer();
        timerAterCooldown = new Timer();

    }

    /**
     * Updates skill according to passed time
     * @param delta passed time in milliseconds
     */
    public void update(int delta) {

        if (timerCooldown.update(delta)) {
            timerAterCooldown.activate(70);
        }

        timerAterCooldown.update(delta);
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
     * Returns action, that owner will apply using this skill
     * @return apply function
     */
    public ISkillAction getToApplyAction() {
        return this::apply;
    }


    /**
     * Returns action, where owner's health or/and magic points will decreased
     * @return apply function
     */
    public ISkillAction getStealSkillCostAction() {
        return this::stealSkillCost;
    }

    /**
     * Activates cooldown timer using skill cooldown time
     */
    public void activateCooldownTimer() {
        timerCooldown.activate(cooldownTime);
    }

    /**
     * Skills have the health and magic points cost, that will still from unit,
     * when unit successfully uses skill
     */
    public void stealSkillCost(Unit owner) {
        owner.getAttribute().changeHP(-requiredHP);
        owner.getAttribute().changeMP(-requiredMP);
    }

    /**
     * Changes primary cooldown time of unit on @param deltaCooldownTime value
     * @param deltaCooldownTime is passed time in milliseconds
     */
    public void changeCooldownTime(int deltaCooldownTime) {
        cooldownTime += deltaCooldownTime;
    }

    /**
     * Changes primary cast time of unit on @param deltaCastTime value
     * @param deltaCastTime is passed time in milliseconds
     */
    public void changeCastTime(int deltaCastTime) {
        castTime += deltaCastTime;
    }

    /* Getters and setters region */
    public int getCastTime() {
        return castTime;
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

    public SkillInstanceKind getKind() {
        return kind;
    }

    public Timer getTimerCooldown() {
        return timerCooldown;
    }

    public Timer getTimerAterCooldown() {
        return timerAterCooldown;
    }

    public int getCooldownTime() {
        return cooldownTime;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


}