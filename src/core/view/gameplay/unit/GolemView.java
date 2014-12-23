package core.view.gameplay.unit;

import core.model.Timer;
import core.model.gameplay.gameobjects.Bot;
import core.model.gameplay.gameobjects.GameObject;
import core.model.gameplay.gameobjects.UnitState;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

import core.resourcemanager.ResourceManager;
import core.view.gameplay.Camera;
import org.newdawn.slick.Sound;

import java.util.stream.IntStream;

public class GolemView extends UnitView {

    private UnitState previousState;
    private Animation walk;
    private Animation attackRight;
    private Animation attackLeft;
    private Sound step;
    private Sound attack;
    private Timer stepTimer;

    public GolemView(GameObject golem) {
        super(golem);
        walk = ResourceManager.getInstance().getAnimation("golem_walk");
        attackRight = ResourceManager.getInstance().getAnimation("golem_attack_right");
        attackLeft = ResourceManager.getInstance().getAnimation("golem_attack_left");
        animation = walk;
        step = ResourceManager.getInstance().getSound("golem_step");
        attack = ResourceManager.getInstance().getSound("golem_attack");
        stepTimer = new Timer();
    }

    @Override
    public void update(int delta) {
        stepTimer.update(delta);
    }

    @Override
    public void render(Graphics g, Camera camera) {
        Bot golem = (Bot) gameObject;

        if (golem.getCurrentState() == UnitState.MOVE && stepTimer.isTime()) {
            step.play();
            stepTimer.activate((int) (1f / golem.getAttribute().getCurrentSpeed() * ResourceManager.getInstance().getSpeedCoef("golem_walk")) * IntStream.of(animation.getDurations()).sum() / 2);
            //camera.shake(100);
        }

        if (golem.getCurrentState() != previousState) {
            switch (golem.getCurrentState()) {
                case STAND:
                    animation = walk;
                    animation.stop();
                    animation.setCurrentFrame(0);
                    break;
                case MOVE:
                    stepTimer.activate(IntStream.of(animation.getDurations()).sum() / 2);
                    animation = walk;
                    animation.setSpeed((float) (golem.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("golem_walk")));
                    animation.start();
                    break;
                case SKILL:
                    switch (golem.getCastingSkill().getKind()) {
                        case PUNCH:
                            attack.play();
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
