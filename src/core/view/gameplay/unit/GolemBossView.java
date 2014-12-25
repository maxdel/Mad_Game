package core.view.gameplay.unit;

import core.model.gameplay.gameobjects.Bot;
import core.model.gameplay.gameobjects.GameObject;
import core.model.gameplay.gameobjects.UnitState;
import core.model.gameplay.skills.SkillInstanceKind;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.resourcemanager.ResourceManager;
import core.view.gameplay.Camera;

public class GolemBossView extends UnitView {
    private Animation walk;
    private Animation attack;
    private Animation skill;
    private UnitState previousState;

    public GolemBossView(GameObject golem) {
        super(golem);
        walk = ResourceManager.getInstance().getAnimation("golem_boss_move");
        attack = ResourceManager.getInstance().getAnimation("golem_boss_attack");
        skill = ResourceManager.getInstance().getAnimation("golem_boss_throw");
    }

    @Override
    public void render(Graphics g, Camera camera) {
        Bot golem = (Bot) gameObject;

        if (golem.getCurrentState() != previousState) {
            switch (golem.getCurrentState()) {
                case STAND:
                    animation = walk;
                    animation.stop();
                    animation.setCurrentFrame(0);
                    break;
                case MOVE:
                    animation = walk;
                    animation.setSpeed((float) (golem.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("golem_boss_move")));
                    animation.start();
                    break;
                case SKILL:
                    if (golem.getCastingSkill().getKind() == SkillInstanceKind.STRONG_PUNCH) {
                        animation = attack;
                    } else {
                        animation = skill;
                    }

                    animation.restart();
                    golem.getCastingSkill().getCastTime();
                    for (int i = 0; i < animation.getFrameCount(); ++i) {
                        animation.setDuration(i, golem.getCastingSkill().getCastTime() / animation.getFrameCount());
                    }
                    break;
            }
            previousState = golem.getCurrentState();
        }

        super.render(g, camera);
    }

}
