package core.model.gameplay.gameobjects.ai;

import java.util.Arrays;

import org.newdawn.slick.geom.Point;

import core.model.Timer;
import core.model.gameplay.gameobjects.Hero;
import core.model.gameplay.items.ItemInstanceKind;
import core.model.gameplay.skills.SkillInstanceKind;

public class SkeletonAI extends BotAI {

    private enum SkeletonAIState implements BotAIState {
        STAND, WALK, PURSUE, FORCE_PURSUE, ATTACK, STRAFE, RETREAT
    }

    @Override
    protected void init() {
        final int standTime = 1000;
        final int pursueDistance = 300;
        final int attackDistance = 50;
        final int retreatDistance = 30;
        final int strafeTime = 250;
        final double attackProbability = 0.7;
        final double strafeProbability = 0.7;
        final int forcePursueTime = 7000;
        currentState = SkeletonAIState.STAND;
        stateMap.put(SkeletonAIState.STAND, new AIState() {
            private Timer timer;
            private double previousHP;
            public void enter()           { timer = new Timer(standTime); previousHP = owner.getAttribute().getCurrentHP(); }
            public void run(int delta)    { owner.stand(); }
            public void update(int delta) { timer.update(delta);
                if (getDistanceToHero() < pursueDistance && seeTarget(Hero.getInstance())) { currentState = SkeletonAIState.PURSUE; return; }
                if (timer.isTime()) { currentState = SkeletonAIState.WALK; return; }
                if (owner.getAttribute().getCurrentHP() < previousHP) { currentState = SkeletonAIState.FORCE_PURSUE; return; }
                previousHP = owner.getAttribute().getCurrentHP(); }
        });
        stateMap.put(SkeletonAIState.WALK, new AIState() {
            private Point target;
            private double previousHP;
            public void enter()           { target = getRandomTarget(); previousHP = owner.getAttribute().getCurrentHP(); }
            public void run(int delta)    { followTarget(target); }
            public void update(int delta) { if (getDistanceToHero() < pursueDistance && seeTarget(Hero.getInstance())) { currentState = SkeletonAIState.PURSUE; return; }
                if (getDistanceToTarget(target) < 2) { currentState = SkeletonAIState.STAND; return; }
                if (owner.getAttribute().getCurrentHP() < previousHP) { currentState = SkeletonAIState.FORCE_PURSUE; return; }
                previousHP = owner.getAttribute().getCurrentHP(); }
        });
        stateMap.put(SkeletonAIState.PURSUE, new AIState() {
            private boolean isFollowing;
            public void enter()           { isFollowing = true; }
            public void run(int delta)    { isFollowing = followHero(); }
            public void update(int delta) { if (getDistanceToHero() >= pursueDistance || !isFollowing) { currentState = SkeletonAIState.STAND; return; }
                if (getDistanceToHero() < attackDistance && Math.random() < attackProbability) { currentState = SkeletonAIState.ATTACK; return; }
                if (getDistanceToHero() < attackDistance && Math.random() < strafeProbability) { currentState = SkeletonAIState.STRAFE; return; }
                if (getDistanceToHero() < attackDistance)  currentState = SkeletonAIState.RETREAT;  }
        });
        stateMap.put(SkeletonAIState.FORCE_PURSUE, new AIState() {
            private Timer timer;
            private boolean isFollowing;
            public void enter()           { timer = new Timer(forcePursueTime); isFollowing = true; }
            public void run(int delta)    { isFollowing = followHero(); }
            public void update(int delta) {
                timer.update(delta);
                if ((getDistanceToHero() >= pursueDistance && timer.isTime()) || !isFollowing) { currentState = SkeletonAIState.STAND; return; }
                if (getDistanceToHero() < attackDistance && Math.random() < attackProbability) { currentState = SkeletonAIState.ATTACK; return; }
                if (getDistanceToHero() < attackDistance && Math.random() < strafeProbability) { currentState = SkeletonAIState.STRAFE; return; }
                if (getDistanceToHero() < attackDistance)  currentState = SkeletonAIState.RETREAT;  }
        });
        stateMap.put(SkeletonAIState.ATTACK, new AIState() {
            public void enter()           { }
            public void run(int delta)    { attackHeroWithSword(); }
            public void update(int delta) { if (getDistanceToHero() >= attackDistance && seeTarget(Hero.getInstance())) { currentState = SkeletonAIState.PURSUE; return; }
                if (Math.random() < strafeProbability) { currentState = SkeletonAIState.STRAFE; return; }
                currentState = SkeletonAIState.RETREAT; }
        });
        stateMap.put(SkeletonAIState.STRAFE, new AIState() {
            private Timer timer;
            private boolean strafeDirection;
            public void enter()           { timer = new Timer(strafeTime); strafeDirection = Math.random() < 0.5; }
            public void run(int delta)    { strafe(strafeDirection); }
            public void update(int delta) { timer.update(delta);
                if (timer.isTime() && getDistanceToHero() >= attackDistance && seeTarget(Hero.getInstance())) { currentState = SkeletonAIState.PURSUE; return; }
                if (timer.isTime() && Math.random() < attackProbability) { currentState = SkeletonAIState.ATTACK; return; }
                if (timer.isTime()) currentState = SkeletonAIState.RETREAT; }
        });
        stateMap.put(SkeletonAIState.RETREAT, new AIState() {
            private double currentRetreatDistance;
            public void enter()           { currentRetreatDistance = 0; }
            public void run(int delta)    { currentRetreatDistance += retreat(delta); }
            public void update(int delta) { if (currentRetreatDistance >= retreatDistance) currentState = SkeletonAIState.PURSUE; }
        });
    }

    private void attackHeroWithSword() {
        owner.stand();
        owner.getInventory().dressIfNotDressed(Arrays.asList(ItemInstanceKind.SWORD, ItemInstanceKind.STRONG_SWORD));
        owner.startCastSkill(SkillInstanceKind.SKELETON_SWORD_ATTACK);
    }

    private void strafe(boolean strafeDirection) {
        Hero hero = Hero.getInstance();
        owner.setDirection(Math.atan2(hero.getY() - owner.getY(), hero.getX() - owner.getX()));
        owner.move(Math.PI / 2 * (strafeDirection ? 1 : -1));
    }

    private double retreat(int delta) {
        Hero hero = Hero.getInstance();
        owner.setDirection(Math.atan2(hero.getY() - owner.getY(), hero.getX() - owner.getX()));
        owner.move(Math.PI);
        return owner.getAttribute().getCurrentSpeed() * delta;
    }

}
