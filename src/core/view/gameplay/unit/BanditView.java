package core.view.gameplay.unit;

import core.MathAdv;
import core.model.gameplay.World;
import core.model.gameplay.gameobjects.*;
import core.model.gameplay.skills.AreaDamage;
import core.model.gameplay.skills.SkillInstanceKind;
import core.view.gameplay.Camera;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.resourcemanager.ResourceManager;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

import java.io.IOException;

public class BanditView extends UnitView {

    private Animation animationWalkSword;
    private GameObjectState previousState;

    public BanditView(GameObject bandit) throws SlickException {
        super(bandit);
        animation = ResourceManager.getInstance().getAnimation("bandit");
        animationWalkSword = ResourceManager.getInstance().getAnimation("bandit_walk_sword");
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
                    animation.setCurrentFrame(0);
                    animation.stop();
                    break;
                case ITEM:
                    break;
                case SKILL:
                    break;
            }
        }
        previousState = bandit.getCurrentState();

        super.render(g, camera);
    }

}