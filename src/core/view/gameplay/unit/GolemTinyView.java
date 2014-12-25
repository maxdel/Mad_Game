package core.view.gameplay.unit;

import core.model.Timer;
import core.model.gameplay.gameobjects.Bot;
import core.model.gameplay.gameobjects.GameObject;
import core.model.gameplay.gameobjects.UnitState;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.resourcemanager.ResourceManager;
import core.view.gameplay.Camera;

import java.util.stream.IntStream;

public class GolemTinyView extends UnitView {

    private Animation walk;
    private Animation attack;
    private UnitState previousState;

    public GolemTinyView(GameObject golem) {
        super(golem);
        walk = ResourceManager.getInstance().getAnimation("golemtiny_walk");
        attack = ResourceManager.getInstance().getAnimation("golemtiny_attack");
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
                    animation.setSpeed((float) (golem.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("golemtiny_walk")));
                    animation.start();
                    break;
                case SKILL:
                    animation = attack;
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
