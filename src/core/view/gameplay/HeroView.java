package core.view.gameplay;

import org.newdawn.slick.*;

import core.resource_manager.ResourceManager;
import core.model.gameplay.units.GameObjectSolid;
import core.model.gameplay.units.Hero;
import core.model.gameplay.units.GameObjectState;

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
    private Animation animationBowWalk;
    private Animation animationBowAttack;

    public HeroView(GameObjectSolid hero, ResourceManager resourceManager) throws SlickException {
        super(hero, resourceManager);
        animation = ResourceManager.getInstance().getAnimation("hero");
        animationAttack = ResourceManager.getInstance().getAnimation("heroattack");
        animationSwordWalk = ResourceManager.getInstance().getAnimation("heroswordwalk");
        animationSwordAttack = ResourceManager.getInstance().getAnimation("heroswordattack");
        animationBowWalk = ResourceManager.getInstance().getAnimation("herobowwalk");
        animationBowAttack = ResourceManager.getInstance().getAnimation("herobowattack");
        animationWalk = animation;
    }

    @Override
    public void render(Graphics g, double viewX, double viewY, float viewDegreeAngle,
                       double viewCenterX, double viewCenterY, Hero hero1) throws SlickException {
        Hero hero = getHero();
        if (hero.getCurrentState() != previousState) {
            switch (hero.getCurrentState()) {
                case WALK:
                    //TODO: bad code
                    if (hero.getInventory().getDressedWeaponType().equals("Sword")) {
                        animation = animationSwordWalk;
                        animation.setSpeed((float) (hero.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("heroswordwalk")));
                    } else if (hero.getInventory().getDressedWeaponType().equals("Bow")) {
                        animation = animationBowWalk;
                        animation.setSpeed((float) (hero.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("herobowwalk")));
                    } else {
                        animation = animationWalk;
                        animation.setSpeed((float) (hero.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("hero")));
                    }
                    animation.start();
                    break;
                case STAND:
                    if (hero.getInventory().getDressedWeaponType().equals("Sword")) {
                        animation = animationSwordWalk;
                    } else if (hero.getInventory().getDressedWeaponType().equals("Bow")) {
                        animation = animationBowWalk;
                    } else {
                    animation = animationWalk;
                }
                    animation.stop();
                    animation.setCurrentFrame(0);
                    break;
                case RUN:
                    if (hero.getInventory().getDressedWeaponType().equals("Sword")) {
                        animation = animationSwordWalk;
                    } else if (hero.getInventory().getDressedWeaponType().equals("Bow")) {
                        animation = animationBowWalk;
                    } else {
                    animation = animationWalk;
                }
                    animation.start();
                    animation.setSpeed((float) (hero.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("hero")));
                    break;
                case PICK_ITEM:
                    if (hero.getInventory().getDressedWeaponType().equals("Sword")) {
                        animation = animationSwordWalk;
                    } else if (hero.getInventory().getDressedWeaponType().equals("Bow")) {
                        animation = animationBowWalk;
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
                    if (hero.getInventory().getDressedWeaponType().equals("Sword")) {
                        Music music = new Music("res/Swoosh01.wav");
                        music.play();
                        animation = animationSwordAttack;
                        animation.restart();
                        hero.getCurrentSkill().getCastTime();
                        for (int i = 0; i < animation.getFrameCount(); ++i) {
                            animation.setDuration(i, hero.getCurrentSkill().getCastTime() / animation.getFrameCount());
                        }
                    } else if (hero.getInventory().getDressedWeaponType().equals("Bow")) {
                        Music music = new Music("res/Bowshot.wav");
                        music.play();
                        animation = animationBowAttack;
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
        drawHealthbar(g, 90, 100, 120, 6, hero.getAttribute().getHP().getCurrent(),
                hero.getAttribute().getHP().getMaximum(), Color.red);
        drawHealthbar(g, 90, 120, 120, 6, hero.getAttribute().getMP().getCurrent(),
                hero.getAttribute().getMP().getMaximum(), Color.blue);
        /*g.drawString(String.valueOf((int) hero.getAttribute().getPAttack()) + "/" +
                        String.valueOf((int) hero.getAttribute().getMAttack()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 80));
        g.drawString(String.valueOf((int) hero.getAttribute().getPArmor()) + "/" +
                        String.valueOf((int) hero.getAttribute().getMArmor()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 60));
        g.drawString(String.valueOf((int) hero.getAttribute().getHP().getCurrent()) + "/" +
                        String.valueOf((int) hero.getAttribute().getMaximumHP()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 40));
        g.drawString(String.valueOf((int) hero.getAttribute().getMP().getCurrent()) + "/" +
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
        return (Hero) gameObjectSolid;
    }

}