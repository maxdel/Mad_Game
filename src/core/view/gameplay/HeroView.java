package core.view.gameplay;

import core.model.gameplay.items.Bow;
import core.model.gameplay.skills.Skill;
import org.newdawn.slick.*;

import core.resourcemanager.ResourceManager;
import core.model.gameplay.gameobjects.GameObjectSolid;
import core.model.gameplay.gameobjects.Hero;
import core.model.gameplay.gameobjects.GameObjectState;

/**
 * Provides functions for the definition of actors, as well as actor
 * operations, such as `receive`, `react`, `reply`, etc.
 */
public class HeroView extends GameObjectView {

    private GameObjectState previousState;
    private Animation animationWalk;
    private Animation animationWalkSword;
    private Animation animationStrongSwordAttack;
    private Animation animationSwordAttack;
    private Animation animationWalkBow;
    private Animation animationBowShot;

    public HeroView(GameObjectSolid hero, ResourceManager resourceManager) throws SlickException {
        super(hero, resourceManager);
        animation = ResourceManager.getInstance().getAnimation("hero_walk");
        animationWalkSword = ResourceManager.getInstance().getAnimation("hero_walk_sword");
        animationWalkBow = ResourceManager.getInstance().getAnimation("hero_walk_bow");
        animationSwordAttack = ResourceManager.getInstance().getAnimation("hero_skill_sword_attack");
        animationStrongSwordAttack = ResourceManager.getInstance().getAnimation("hero_skill_strong_sword_attack");
        animationBowShot = ResourceManager.getInstance().getAnimation("hero_skill_bow_shot");
        animationWalk = animation;
    }

    @Override
    public void render(Graphics g, double viewX, double viewY, float viewDegreeAngle,
                       double viewCenterX, double viewCenterY, Hero hero1) throws SlickException {
        Hero hero = getHero();
        if (hero.getCurrentState() != previousState) {
            switch (hero.getCurrentState()) {
                case MOVE:
                    //TODO: bad code
                    if (hero.getInventory().getDressedWeaponType().equals("Sword")) {
                        animation = animationWalkSword;
                        animation.setSpeed((float) (hero.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("hero_walk_sword")));
                    } else if (hero.getInventory().getDressedWeaponType().equals("Bow")) {
                        animation = animationWalkBow;
                        animation.setSpeed((float) (hero.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("hero_walk_bow")));
                    } else {
                        animation = animationWalk;
                        animation.setSpeed((float) (hero.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("hero_walk")));
                    }
                    animation.start();
                    break;
                case STAND:
                    if (hero.getInventory().getDressedWeaponType().equals("Sword")) {
                        animation = animationWalkSword;
                    } else if (hero.getInventory().getDressedWeaponType().equals("Bow")) {
                        animation = animationWalkBow;
                    } else {
                    animation = animationWalk;
                    }
                    animation.stop();
                    animation.setCurrentFrame(0);
                    break;
                case ITEM:
                    if (hero.getInventory().getDressedWeaponType().equals("Sword")) {
                        animation = animationWalkSword;
                    } else if (hero.getInventory().getDressedWeaponType().equals("Bow")) {
                        animation = animationWalkBow;
                    } else {
                        animation = animationWalk;
                    }
                    animation.start();
                    animation.setSpeed((float) (hero.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("hero_walk")));
                    break;
                case SKILL:
                    Music music = null;
                    Skill castingSkill = hero.getCastingSkill();
                    switch (castingSkill.getKind()) {
                        case SWORD_ATTACK:
                            music = new Music("res/sounds/Swoosh01.wav");
                            music.play();
                            animation = animationSwordAttack;
                            animation.restart();
                            hero.getCastingSkill().getCastTime();
                            for (int i = 0; i < animation.getFrameCount(); ++i) {
                                animation.setDuration(i, hero.getCastingSkill().getCastTime() / animation.getFrameCount());
                            }
                            break;
                        case STAFF_ATTACK:
                            break;
                        case STRONG_SWORD_ATTACK:
                            music = new Music("res/sounds/Swoosh01.wav");
                            music.play();
                            animation = animationStrongSwordAttack;
                            animation.restart();
                            hero.getCastingSkill().getCastTime();
                            for (int i = 0; i < animation.getFrameCount(); ++i) {
                                animation.setDuration(i, hero.getCastingSkill().getCastTime() / animation.getFrameCount());
                            }
                            break;
                        case FIREBALL:
                            music = new Music("res/sounds/fireball.wav");
                            music.play();
                            animation = animationBowShot;
                            animation.restart();
                            hero.getCastingSkill().getCastTime();
                            for (int i = 0; i < animation.getFrameCount(); ++i) {
                                animation.setDuration(i, hero.getCastingSkill().getCastTime() / animation.getFrameCount());
                            }
                            break;
                        case BOW_SHOT:
                            music = new Music("res/sounds/Bowshot.wav");
                            music.play();
                            animation = animationBowShot;
                            animation.restart();
                            hero.getCastingSkill().getCastTime();
                            for (int i = 0; i < animation.getFrameCount(); ++i) {
                                animation.setDuration(i, hero.getCastingSkill().getCastTime() / animation.getFrameCount());
                            }
                            break;
                    }
                    animation.start();
                }
        }

        previousState = hero.getCurrentState();

        rotate(g, viewX, viewY, viewDegreeAngle, viewCenterX, viewCenterY, true);
        draw(viewX, viewY);
        // draw mask
        //drawMask(g, viewX, viewY);

        rotate(g, viewX, viewY, viewDegreeAngle, viewCenterX, viewCenterY, false);

        if (hero.getInventory().isItemDressed(Bow.class)) {
            Image aim = ResourceManager.getInstance().getImage("Bow_aim");
            aim.draw((float)viewCenterX - aim.getWidth() / 2, (float)viewCenterY - 250);
        }
        // For debug
        drawHealthbar(g, 90, 100, 120, 6, hero.getAttribute().getHP().getCurrent(),
                hero.getAttribute().getHP().getMaximum(), Color.red);
        drawHealthbar(g, 90, 120, 120, 6, hero.getAttribute().getMP().getCurrent(),
                hero.getAttribute().getMP().getMaximum(), Color.blue);
        /*g.drawString("(" + String.valueOf((int) hero.getX()) + ";" + String.valueOf((int) hero.getY())
                        + ") dir=" + String.valueOf((int) (hero.getDirection() / Math.PI * 180) % 360
                        + " curSpd=" + String.valueOf(hero.getAttribute().getCurrentSpeed())),
                (float) (hero.getX() - viewX),
                (float) (hero.getY() - viewY));*/
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
        /*g.drawString("Kills to next skill: " + (hero.level * 3 - hero.kills), 10, 30);
        g.drawString("Current level: " + hero.level , 10, 50);
        hero.level = hero.kills / 3 + 1;*/
    }

    private Hero getHero() {
        return (Hero) gameObjectSolid;
    }

}