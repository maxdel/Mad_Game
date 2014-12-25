package core.view.gameplay.unit;

import core.model.gameplay.gameobjects.Bot;
import core.model.gameplay.gameobjects.UnitState;
import core.model.gameplay.skills.Skill;
import core.view.gameplay.Camera;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.model.gameplay.gameobjects.GameObject;
import core.resourcemanager.ResourceManager;

public class SkeletonMageView extends UnitView {

    private UnitState previousState;
    private Animation walk;
    private Animation attack;

    public SkeletonMageView(GameObject skeletonMage) {
        super(skeletonMage);
        walk = ResourceManager.getInstance().getAnimation("skeleton_mage_walk");
        attack = ResourceManager.getInstance().getAnimation("skeleton_mage_attack");
        animation = walk;
    }

    @Override
    public void render(Graphics g, Camera camera) {
        Bot skeletonMage = (Bot) gameObject;

        if (skeletonMage.getCurrentState() != previousState) {
            switch (skeletonMage.getCurrentState()) {
                case MOVE:
                    animation = walk;
                    animation.setSpeed((float) (skeletonMage.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("skeleton_mage_walk")));
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
                    Skill castingSkill = skeletonMage.getCastingSkill();
                    switch (castingSkill.getKind()) {
                        case SKELETON_FIREBALL:
                            ResourceManager.getInstance().getSound("fireball").play();
                            animation = attack;
                            animation.restart();
                            skeletonMage.getCastingSkill().getCastTime();
                            for (int i = 0; i < animation.getFrameCount(); ++i) {
                                animation.setDuration(i, skeletonMage.getCastingSkill().getCastTime() / animation.getFrameCount());
                            }
                            break;
                    }
                    animation.start();
                    break;
            }

        }
        previousState = skeletonMage.getCurrentState();

        super.render(g, camera);
    }

}