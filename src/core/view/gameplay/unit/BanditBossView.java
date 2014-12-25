package core.view.gameplay.unit;

import core.model.gameplay.skills.SkillInstanceKind;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

import core.view.gameplay.Camera;
import core.model.gameplay.gameobjects.*;
import core.resourcemanager.ResourceManager;

public class BanditBossView extends UnitView {

    private Animation walk;
    private Animation attack;
    private Animation strongAttack;
    private UnitState previousState;



    public BanditBossView(GameObject banditBoss) {
        super(banditBoss);
        walk = ResourceManager.getInstance().getAnimation("bandit_boss_move");
        attack = ResourceManager.getInstance().getAnimation("bandit_boss_attack");
        strongAttack = ResourceManager.getInstance().getAnimation("bandit_boss_strong_attack");


    }

    @Override
    public void render(Graphics g, Camera camera) {

        Bot boss = (Bot) gameObject;

        if (boss.getCurrentState() != previousState) {
            switch (boss.getCurrentState()) {
                case STAND:
                    animation = walk;
                    animation.stop();
                    animation.setCurrentFrame(0);
                    break;
                case MOVE:
                    animation = walk;
                    animation.setSpeed((float) (boss.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("golem_boss_move")));
                    animation.start();
                    break;
                case SKILL:
                    if (boss.getCastingSkill().getKind() == SkillInstanceKind.BANDITBOSS_SWORD_ATTACK) {
                        animation = attack;
                    } else {
                        animation = strongAttack;
                    }

                    animation.restart();
                    boss.getCastingSkill().getCastTime();
                    for (int i = 0; i < animation.getFrameCount(); ++i) {
                        animation.setDuration(i, boss.getCastingSkill().getCastTime() / animation.getFrameCount());
                    }
                    break;
            }
            previousState = boss.getCurrentState();
        }

        super.render(g, camera);
    }


}

