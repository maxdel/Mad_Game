package core.model.gameplay;

public class RegenSkill extends Skill {

    private int HPdelta;

    public RegenSkill(GameObjectMoving owner, String name, int castTime, int cooldownTime, String requiredItem,
                      double requiredHP, double requiredMP, int HPdelta) {
        super(owner, name, castTime, cooldownTime, requiredItem, requiredHP, requiredMP);
        this.HPdelta = HPdelta;
    }

    @Override
    protected boolean startCast() {
        if ((owner.getInventory().isItemDressed(requiredItem) ||
                requiredItem == null) &&
                owner.getAttribute().getCurrentMP() >= requiredMP &&
                owner.getAttribute().getCurrentHP() >= requiredHP &&
                currentCooldownTime == 0) {
            currentCastTime = castTime;
            owner.getAttribute().setCurrentMP(owner.getAttribute().getCurrentMP() - requiredMP);
            owner.getAttribute().setCurrentHP(owner.getAttribute().getCurrentHP() - requiredHP);
            currentCooldownTime = cooldownTime;
            return true;
        }
        return false;
    }

    @Override
    protected void cast() {
        owner.getAttribute().setCurrentHP(owner.getAttribute().getCurrentHP() + HPdelta);
    }

}