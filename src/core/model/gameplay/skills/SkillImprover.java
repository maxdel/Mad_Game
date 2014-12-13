package core.model.gameplay.skills;

import core.model.Timer;
import core.model.gameplay.gameobjects.Unit;
import core.model.gameplay.items.ItemInstanceKind;

public class SkillImprover extends Skill {

    private int workTime;
    private Timer timerWorkTime;
    private SkillInstanceKind skillToBuffKind;
    private int castTimeDelta;
    private int cooldownTimeDelta;
    private Skill buffingSkill;


    public SkillImprover(String name, String description, int castTime, int postCastTime,
                         int cooldownTime, ItemInstanceKind requiredItemKind, double requiredHP, double requiredMP,
                         int workTime, SkillInstanceKind skillToBuffKind, int castTimeDelta, int cooldownTimeDelta,
                         SkillInstanceKind kind) {
        super(name, description, castTime, postCastTime, cooldownTime, requiredItemKind, requiredHP, requiredMP, kind);
        this.workTime = workTime;
        this.skillToBuffKind = skillToBuffKind;
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
    protected void apply(Unit owner) {
        for (Skill skill : owner.getSkillList()) {
            if (skill.getKind() == skillToBuffKind) {
                buffingSkill = skill;
                buffingSkill.changeCastTime(-castTimeDelta);
                buffingSkill.changeCooldownTime(-cooldownTimeDelta);
                timerWorkTime.activate(workTime);
                break;
            }
        }
    }

    /**
     * Sets the cast time and cooldown time of buffing skill to their primary values
     */
    private void dispel() {
        buffingSkill.changeCastTime(castTimeDelta);
        buffingSkill.changeCooldownTime(cooldownTimeDelta);
    }

}
