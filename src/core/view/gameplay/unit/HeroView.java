package core.view.gameplay.unit;

import core.model.gameplay.gameobjects.*;
import core.model.gameplay.items.Bow;
import core.model.gameplay.items.Item;
import core.model.gameplay.items.Staff;
import core.model.gameplay.items.Sword;
import core.view.gameplay.Camera;
import org.newdawn.slick.*;

import core.model.gameplay.skills.Skill;
import core.resourcemanager.ResourceManager;

public class HeroView extends UnitView {

    private UnitState previousState;
    private Animation animationWalk;
    private Animation animationWalkSword;
    private Animation animationWalkStaff;
    private Animation animationStrongSwordAttack;
    private Animation animationSwordAttack;
    private Animation animationWalkBow;
    private Animation animationBowShot;
    private Animation animationFireball;
    private Item previousWeapon;

    public HeroView(GameObject hero) {
        super(hero);
        animation = ResourceManager.getInstance().getAnimation("hero_walk");
        animationWalkSword = ResourceManager.getInstance().getAnimation("hero_walk_sword");
        animationWalkBow = ResourceManager.getInstance().getAnimation("hero_walk_bow");
        animationWalkStaff = ResourceManager.getInstance().getAnimation("hero_walk_staff");
        animationSwordAttack = ResourceManager.getInstance().getAnimation("hero_skill_sword_attack");
        animationStrongSwordAttack = ResourceManager.getInstance().getAnimation("hero_skill_strong_sword_attack");
        animationBowShot = ResourceManager.getInstance().getAnimation("hero_skill_bow_shot");
        animationFireball = ResourceManager.getInstance().getAnimation("hero_skill_fireball");
        animationWalk = animation;
    }

    @Override
    public void render(Graphics g, Camera camera) throws SlickException {
        Hero hero = (Hero) gameObject;

        if (hero.getCurrentState() != previousState) {
            switch (hero.getCurrentState()) {
                case MOVE:
                    //TODO: bad code
                    if (hero.getInventory().getDressedWeapon().getItem().getClass() == Sword.class) {
                        animation = animationWalkSword;
                        animation.setSpeed((float) (hero.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("hero_walk_sword")));
                    } else if (hero.getInventory().getDressedWeapon().getItem().getClass() == Bow.class) {
                        animation = animationWalkBow;
                        animation.setSpeed((float) (hero.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("hero_walk_bow")));
                    } else if (hero.getInventory().getDressedWeapon().getItem().getClass() == Staff.class) {
                        animation = animationWalkStaff;
                        animation.setSpeed((float) (hero.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("hero_walk_staff")));
                    } else {
                        animation = animationWalk;
                        animation.setSpeed((float) (hero.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("hero_walk")));
                    }
                    animation.start();
                    break;
                case STAND:
                    if (hero.getInventory().getDressedWeapon().getItem().getClass() == Sword.class) {
                        animation = animationWalkSword;
                    } else if (hero.getInventory().getDressedWeapon().getItem().getClass() == Bow.class) {
                        animation = animationWalkBow;
                    } else if (hero.getInventory().getDressedWeapon().getItem().getClass() == Staff.class) {
                        animation = animationWalkStaff;
                    } else {
                        animation = animationWalk;
                    }
                    animation.stop();
                    animation.setCurrentFrame(0);
                    break;
                case ITEM:
                    if (hero.getInventory().getDressedWeapon().getItem().getClass() == Sword.class) {
                        animation = animationWalkSword;
                    } else if (hero.getInventory().getDressedWeapon().getItem().getClass() == Bow.class) {
                        animation = animationWalkBow;
                    } else {
                        animation = animationWalk;
                    }
                    animation.start();
                    animation.setSpeed((float) (hero.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("hero_walk")));
                    break;
                case SKILL:
                    Skill castingSkill = hero.getCastingSkill();
                    switch (castingSkill.getKind()) {
                        case SWORD_ATTACK:
                            ResourceManager.getInstance().getSound("sword_attack").play();
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
                            ResourceManager.getInstance().getSound("sword_attack").play();
                            animation = animationStrongSwordAttack;
                            animation.restart();
                            hero.getCastingSkill().getCastTime();
                            for (int i = 0; i < animation.getFrameCount(); ++i) {
                                animation.setDuration(i, hero.getCastingSkill().getCastTime() / animation.getFrameCount());
                            }
                            break;
                        case FIREBALL:
                            ResourceManager.getInstance().getSound("fireball").play();
                            animation = animationFireball;
                            animation.restart();
                            hero.getCastingSkill().getCastTime();
                            for (int i = 0; i < animation.getFrameCount(); ++i) {
                                animation.setDuration(i, hero.getCastingSkill().getCastTime() / animation.getFrameCount());
                            }
                            break;
                        case BOW_SHOT:
                            ResourceManager.getInstance().getSound("bow_shot").play();
                            animation = animationBowShot;
                            animation.restart();
                            hero.getCastingSkill().getCastTime();
                            for (int i = 0; i < animation.getFrameCount(); ++i) {
                                animation.setDuration(i, hero.getCastingSkill().getCastTime() / animation.getFrameCount());
                            }
                            break;
                    }
                    animation.start();
                    break;
                case DIALOG:
                    if (hero.getInventory().getDressedWeapon().getItem().getClass() == Sword.class) {
                        if (hero.getInventory().getDressedWeapon().getItem().getClass() == Sword.class) {
                            animation = animationWalkSword;
                        } else if (hero.getInventory().getDressedWeapon().getItem().getClass() == Bow.class) {
                            animation = animationWalkBow;
                        } else {
                            animation = animationWalk;
                        }
                        animation.stop();
                        animation.setCurrentFrame(0);
                    }
                    break;
            }
        } else if (Hero.getInstance().getInventory().getDressedWeapon().getItem() != previousWeapon) {
            switch (hero.getCurrentState()) {
                case MOVE:
                    //TODO: bad code
                    int currentFrame = animation.getFrame();
                    if (hero.getInventory().getDressedWeapon().getItem().getClass() == Sword.class) {
                        animation = animationWalkSword;
                        animation.setSpeed((float) (hero.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("hero_walk_sword")));
                    } else if (hero.getInventory().getDressedWeapon().getItem().getClass() == Bow.class) {
                        animation = animationWalkBow;
                        animation.setSpeed((float) (hero.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("hero_walk_bow")));
                    } else if (hero.getInventory().getDressedWeapon().getItem().getClass() == Staff.class) {
                        animation = animationWalkStaff;
                        animation.setSpeed((float) (hero.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("hero_walk_staff")));
                    } else {
                        animation = animationWalk;
                        animation.setSpeed((float) (hero.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("hero_walk")));
                    }
                    animation.restart();
                    animation.setCurrentFrame(currentFrame);
                    animation.start();
                    break;
                case STAND:
                    if (hero.getInventory().getDressedWeapon().getItem().getClass() == Sword.class) {
                        animation = animationWalkSword;
                    } else if (hero.getInventory().getDressedWeapon().getItem().getClass() == Bow.class) {
                        animation = animationWalkBow;
                    } else if (hero.getInventory().getDressedWeapon().getItem().getClass() == Staff.class) {
                        animation = animationWalkStaff;
                    } else {
                        animation = animationWalk;
                    }
                    animation.stop();
                    animation.setCurrentFrame(0);
                    break;
            }
            previousWeapon = Hero.getInstance().getInventory().getDressedWeapon().getItem();
        }
        if (animation.getFrame() != 0) {
            System.out.println();
        }
        previousState = hero.getCurrentState();

        super.render(g, camera);
    }

}