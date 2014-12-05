package core.view.gameplay;

import org.newdawn.slick.*;

import core.ResourceManager;
import core.model.gameplay.GameObject;
import core.model.gameplay.Hero;
import core.model.gameplay.GameObjectState;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;

/**
 * Provides functions for the definition of actors, as well as actor
 * operations, such as `receive`, `react`, `reply`, etc.
 */
public class HeroView extends GameObjectView {

    private GameObjectState previousState;
    private Animation animationAttack;
    private Animation animationWalk;
    private Animation animationSwordWalk;
    private Animation animationSwordAttack;

    public HeroView(GameObject hero, ResourceManager resourceManager) throws SlickException {
        super(hero, resourceManager);
        animation = ResourceManager.getInstance().getAnimation("hero");
        animationAttack = ResourceManager.getInstance().getAnimation("heroattack");
        animationSwordWalk = ResourceManager.getInstance().getAnimation("heroswordwalk");
        animationSwordAttack = ResourceManager.getInstance().getAnimation("heroswordattack");
        animationWalk = animation;
    }

    @Override
    public void render(Graphics g, double viewX, double viewY, float viewDegreeAngle,
                       double viewCenterX, double viewCenterY, Hero hero1) throws SlickException {
        Hero hero = getHero();
        if (hero.getCurrentState() != previousState) {
            switch (hero.getCurrentState()) {
                case WALK:
                    if (hero.getInventory().isItemDressed("sword")) {
                        animation = animationSwordWalk;
                    } else {
                        animation = animationWalk;
                    }
                    animation.start();
                    animation.setSpeed((float) (hero.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("hero")));
                    break;
                case STAND:
                    if (hero.getInventory().isItemDressed("sword")) {
                        animation = animationSwordWalk;
                    } else {
                        animation = animationWalk;
                    }
                    animation.stop();
                    animation.setCurrentFrame(0);
                    break;
                case RUN:
                    if (hero.getInventory().isItemDressed("sword")) {
                        animation = animationSwordWalk;
                    } else {
                        animation = animationWalk;
                    }
                    animation.start();
                    animation.setSpeed((float) (hero.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("hero")));
                    break;
                case PICK_ITEM:
                    if (hero.getInventory().isItemDressed("sword")) {
                        animation = animationSwordWalk;
                    } else {
                        animation = animationWalk;
                    }
                    animation.start();
                    animation.setSpeed((float) (hero.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("hero")));
                    break;
                case USE_ITEM:
                    if (hero.getUsingItem().getItem().getName().equals("Apple")) {
                        Music music = new Music("res/AppleBite.wav");
                        music.play();
                    }
                     break;
                case CAST:
                    if (hero.getInventory().isItemDressed("sword")) {
                        Music music = new Music("res/Swoosh01.wav");
                        music.play();
                        animation = animationSwordAttack;
                        animation.restart();
                        hero.getCurrentSkill().getCastTime();
                        for (int i = 0; i < animation.getFrameCount(); ++i) {
                            animation.setDuration(i, hero.getCurrentSkill().getCastTime() / animation.getFrameCount());
                        }
                    } else {
                        animation = animationAttack;
                        animation.setSpeed((float) (ResourceManager.getInstance().getSpeedCoef("heroattack")));
                    }
                    animation.start();
                    break;
            }
        }
        previousState = hero.getCurrentState();

        rotate(g, viewX, viewY, viewDegreeAngle, viewCenterX, viewCenterY, true);
        draw(viewX, viewY);
        // draw mask
        drawMask(g, viewX, viewY);

        rotate(g, viewX, viewY, viewDegreeAngle, viewCenterX, viewCenterY, false);

        // For debug
        drawHealthbar(g, 90, 100, 120, 6, hero.getAttribute().getCurrentHP(),
                hero.getAttribute().getMaximumHP(), Color.red);
        drawHealthbar(g, 90, 120, 120, 6, hero.getAttribute().getCurrentMP(),
                hero.getAttribute().getMaximumMP(), Color.blue);
        /*g.drawString(String.valueOf((int) hero.getAttribute().getPAttack()) + "/" +
                        String.valueOf((int) hero.getAttribute().getMAttack()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 80));
        g.drawString(String.valueOf((int) hero.getAttribute().getPArmor()) + "/" +
                        String.valueOf((int) hero.getAttribute().getMArmor()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 60));
        g.drawString(String.valueOf((int) hero.getAttribute().getCurrentHP()) + "/" +
                        String.valueOf((int) hero.getAttribute().getMaximumHP()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 40));
        g.drawString(String.valueOf((int) hero.getAttribute().getCurrentMP()) + "/" +
                        String.valueOf((int) hero.getAttribute().getMaximumMP()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 20));
        g.drawString("(" + String.valueOf((int) gameObject.getX()) + ";" + String.valueOf((int) gameObject.getY())
                        + ") dir=" + String.valueOf((int) (gameObject.getDirection() / Math.PI * 180) % 360),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY));*/
        // TODO for fun
        g.drawString("Kills to next skill: " + (hero.level * 3 - hero.kills), 10, 30);
        g.drawString("Current level: " + hero.level , 10, 50);
        hero.level = hero.kills / 3 + 1;
    }

    private Hero getHero() {
        return (Hero) gameObject;
    }

}