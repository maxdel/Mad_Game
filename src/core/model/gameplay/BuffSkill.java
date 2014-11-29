package core.model.gameplay;

public class BuffSkill extends Skill {

    private int workTime;
    private int currentWorkTime;
    private String skillToBuff;
    private int castTimeDelta;
    private int cooldownTimeDelta;

    public BuffSkill(GameObjectMoving owner, String name, int castTime, int cooldownTime, String requiredItem,
                     double requiredHP, double requiredMP, int workTime, String skillToBuff, int castTimeDelta,
                     int cooldownTimeDelta) {
        super(owner, name, castTime, cooldownTime, requiredItem, requiredHP, requiredMP);
        this.workTime = workTime;
        this.currentWorkTime = 0;
        this.skillToBuff = skillToBuff;
        this.castTimeDelta = castTimeDelta;
        this.cooldownTimeDelta = cooldownTimeDelta;
    }

    @Override
    protected void update(int delta) {
        super.update(delta);

        if (currentWorkTime > 0) {
            currentWorkTime -= delta;
            if (currentWorkTime <= 0) {
                currentWorkTime = 0;
                dispel();
            }
        }
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
        for (Skill skill : owner.getSkillList()) {
            if (skill.getName().equals(skillToBuff)) {
                skill.setCastTime(skill.getCastTime() - castTimeDelta);
                skill.setCooldownTime(skill.getCooldownTime() - cooldownTimeDelta);
                currentWorkTime = workTime;
                break;
            }
        }
    }

    private void dispel() {
        for (Skill skill : owner.getSkillList()) {
            if (skill.getName().equals(skillToBuff)) {
                skill.setCastTime(skill.getCastTime() + castTimeDelta);
                skill.setCooldownTime(skill.getCooldownTime() + cooldownTimeDelta);
                break;
            }
        }
    }

}