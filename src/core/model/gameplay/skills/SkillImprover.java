package core.model.gameplay.skills;

import core.model.Timer;
import core.model.gameplay.gameobjects.Unit;

public class SkillImprover extends Skill{

    public enum Kinds implements SkillKinds {
        WIND_BOW
    }

    private int workTime;
    private Timer timerWorkTime;
    private SkillKinds skillToBuff;
    private int castTimeDelta;
    private int cooldownTimeDelta;

    public SkillImprover(Unit owner, String name, String description, int castTime, int postCastTime,
                         int cooldownTime, String requiredItem, double requiredHP, double requiredMP,
                         int workTime, SkillKinds skillToBuff, int castTimeDelta, int cooldownTimeDelta,
                         SkillKinds kind) {
        super(owner, name, description, castTime, postCastTime, cooldownTime, requiredItem, requiredHP, requiredMP, kind);
        this.workTime = workTime;
        this.skillToBuff = skillToBuff;
        this.castTimeDelta = castTimeDelta;
        this.cooldownTimeDelta = cooldownTimeDelta;

        timerWorkTime = new Timer();
    }

    /**
     * Updates improver skill. It includes updates improver skill working time
     * @param delta passed time in milliseconds
     */
    @Override
    public void update(int delta) {
        super.update(delta);

        if (timerWorkTime.update(delta)) {
            dispel();
        }
    }

    /**
     * Decrease the cast time and cooldown time of the skill
     */
    @Override
    public void apply() {
        for (Skill skill : owner.getSkillList()) {
      /*      if (skill.getClass() == getKind().equals(skillToBuff)) {
                skill.setCastTime(skill.getCastTime() - castTimeDelta);
                skill.changeCooldownTime(-cooldownTimeDelta);
                timerWorkTime.activate(workTime);
                break;
            }
      */  }
    }

    /**
     * Sets the cast time and cooldown time to their primary values
     */
    private void dispel() {
        /*for (Skill skill : owner.getSkillList()) {
            if (skill.getName().equals(skillToBuff)) {
                skill.setCastTime(skill.getCastTime() + castTimeDelta);
                skill.changeCooldownTime(cooldownTimeDelta);
                break;
            }
        }*/
    }


}
