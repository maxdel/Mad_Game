package core.view.gameplay.unit;

import core.model.gameplay.gameobjects.Bot;
import core.model.gameplay.gameobjects.GameObject;
import core.model.gameplay.gameobjects.UnitState;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.resourcemanager.ResourceManager;
import core.view.gameplay.Camera;

public class GolemView extends UnitView {

    private UnitState previousState;
    private Animation walk;
    private Animation attackRight;
    private Animation attackLeft;

    public GolemView(GameObject golem) throws SlickException {
        super(golem);
        walk = ResourceManager.getInstance().getAnimation("golem_walk");
        attackRight = ResourceManager.getInstance().getAnimation("golem_attack_right");
        attackLeft = ResourceManager.getInstance().getAnimation("golem_attack_left");
        animation = walk;
    }

    @Override
    public void render(Graphics g, Camera camera) throws SlickException {
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
                    animation.setSpeed((float) (golem.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("golem_walk")));
                    animation.start();
                    break;
                case SKILL:
                    switch (golem.getCastingSkill().getKind()) {
                        case PUNCH:
                            animation = (Math.random() < 0.5) ? attackLeft : attackRight;
                            animation.restart();
                            golem.getCastingSkill().getCastTime();
                            for (int i = 0; i < animation.getFrameCount(); ++i) {
                                animation.setDuration(i, golem.getCastingSkill().getCastTime() / animation.getFrameCount());
                            }
                            break;
                    }
            }
            previousState = golem.getCurrentState();
        }

        super.render(g, camera);
    }

}
