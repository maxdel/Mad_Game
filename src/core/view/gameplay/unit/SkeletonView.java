package core.view.gameplay.unit;

import core.model.gameplay.gameobjects.Bot;
import core.model.gameplay.gameobjects.GameObject;
import core.model.gameplay.gameobjects.UnitState;
import core.model.gameplay.skills.Skill;
import core.resourcemanager.ResourceManager;
import core.view.gameplay.Camera;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class SkeletonView extends UnitView {

    private UnitState previousState;
    private Animation walk;
    private Animation attack;

    public SkeletonView(GameObject skeleton) {
        super(skeleton);
        walk = ResourceManager.getInstance().getAnimation("skeleton_walk");
        attack = ResourceManager.getInstance().getAnimation("skeleton_attack");
        animation = walk;
    }

    @Override
    public void render(Graphics g, Camera camera) {
        Bot skeleton = (Bot) gameObject;

        if (skeleton.getCurrentState() != previousState) {
            switch (skeleton.getCurrentState()) {
                case MOVE:
                    animation = walk;
                    animation.setSpeed((float) (skeleton.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("skeleton_walk")));
                    animation.start();
                    break;
                case STAND:
                    animation = walk;
                    animation.stop();
                    animation.setCurrentFrame(0);
                    break;
                case ITEM:
                    break;
                case SKILL:
                    Skill castingSkill = skeleton.getCastingSkill();
                    switch (castingSkill.getKind()) {
                        case SKELETON_SWORD_ATTACK:
                            ResourceManager.getInstance().getSound("sword_attack").play();
                            animation = attack;
                            animation.restart();
                            skeleton.getCastingSkill().getCastTime();
                            for (int i = 0; i < animation.getFrameCount(); ++i) {
                                animation.setDuration(i, skeleton.getCastingSkill().getCastTime() / animation.getFrameCount());
                            }
                            break;
                    }
                    animation.start();
                    break;
            }

        }
        previousState = skeleton.getCurrentState();

        super.render(g, camera);
    }

}