package core.model.gameplay.skills;

import core.model.gameplay.gameobjects.Unit;

public class BuffSkill extends Skill {

    private int workTime;
    private int currentWorkTime;
    private String skillToBuff;
    private int castTimeDelta;
    private int cooldownTimeDelta;

    public BuffSkill(Unit owner, String name, String description, int castTime, int postCastTime,
                     int cooldownTime, String requiredItem, double requiredHP, double requiredMP,
                     int workTime, String skillToBuff, int castTimeDelta, int cooldownTimeDelta) {
        super(owner, name, description, castTime, postCastTime, cooldownTime, requiredItem, requiredHP, requiredMP);
        this.workTime = workTime;
        this.currentWorkTime = 0;
        this.skillToBuff = skillToBuff;
        this.castTimeDelta = castTimeDelta;
        this.cooldownTimeDelta = cooldownTimeDelta;
    }


    private void updateWorkTime(int delta) {
        if (currentWorkTime > 0) {
            currentWorkTime -= delta;
            if (currentWorkTime <= 0) {
                currentWorkTime = 0;
                dispel();
            }
        }
    }

    @Override
    public void update(int delta) {
        super.update(delta);
        updateWorkTime(delta);
    }


    @Override
    public void apply() {
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