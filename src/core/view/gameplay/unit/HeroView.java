package core.view.gameplay.unit;

import core.model.gameplay.gameobjects.*;
import core.view.gameplay.Camera;
import core.view.gameplay.ParticleEffect;
import org.newdawn.slick.*;

import core.model.gameplay.skills.Skill;
import core.resourcemanager.ResourceManager;
import org.newdawn.slick.particles.ParticleIO;

import java.io.IOException;

public class HeroView extends UnitView {

    private GameObjectState previousState;
    private Animation animationWalk;
    private Animation animationWalkSword;
    private Animation animationStrongSwordAttack;
    private Animation animationSwordAttack;
    private Animation animationWalkBow;
    private Animation animationBowShot;

    public HeroView(GameObject hero) throws SlickException {
        super(hero);
        animation = ResourceManager.getInstance().getAnimation("hero_walk");
        animationWalkSword = ResourceManager.getInstance().getAnimation("hero_walk_sword");
        animationWalkBow = ResourceManager.getInstance().getAnimation("hero_walk_bow");
        animationSwordAttack = ResourceManager.getInstance().getAnimation("hero_skill_sword_attack");
        animationStrongSwordAttack = ResourceManager.getInstance().getAnimation("hero_skill_strong_sword_attack");
        animationBowShot = ResourceManager.getInstance().getAnimation("hero_skill_bow_shot");
        animationWalk = animation;
    }

    @Override
    public void render(Graphics g, Camera camera) throws SlickException {
        Hero hero = (Hero) gameObject;

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
                            animation = animationBowShot;
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
                }
        }

        previousState = hero.getCurrentState();

        super.render(g, camera);
    }

}