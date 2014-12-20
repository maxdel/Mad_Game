package core.view.gameplay.unit;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.view.gameplay.Camera;
import core.model.gameplay.gameobjects.*;
import core.model.gameplay.skills.Skill;
import core.resourcemanager.ResourceManager;

public class BanditView extends UnitView {

    private UnitState previousState;
    private Animation animationSwordAttack;
    private Animation animationWalkSword;

    public BanditView(GameObject bandit) throws SlickException {
        super(bandit);
        animationWalkSword = ResourceManager.getInstance().getAnimation("bandit_walk_sword");
        animationSwordAttack = ResourceManager.getInstance().getAnimation("bandit_skill_sword_attack");
        animation = animationWalkSword;
    }

    @Override
    public void render(Graphics g, Camera camera) throws SlickException {
        Bot bandit = (Bot) gameObject;

        if (bandit.getCurrentState() != previousState) {
            switch (bandit.getCurrentState()) {
                case MOVE:
                    animation = animationWalkSword;
                    animation.setSpeed((float) (bandit.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("bandit_walk_sword")));
                    animation.start();
                    break;
                case STAND:
                    animation = animationWalkSword;
                    animation.stop();
                    animation.setCurrentFrame(0);
                    break;
                case ITEM:
                    break;
                case SKILL:
                    Skill castingSkill = bandit.getCastingSkill();
                    switch (castingSkill.getKind()) {
                        case SWORD_ATTACK:
                            ResourceManager.getInstance().getSound("sword_attack").play();
                            animation = animationSwordAttack;
                            animation.restart();
                            bandit.getCastingSkill().getCastTime();
                            for (int i = 0; i < animation.getFrameCount(); ++i) {
                                animation.setDuration(i, bandit.getCastingSkill().getCastTime() / animation.getFrameCount());
                            }
                            break;
                    }
                    animation.start();
            }

        }
        previousState = bandit.getCurrentState();

        super.render(g, camera);
    }

}