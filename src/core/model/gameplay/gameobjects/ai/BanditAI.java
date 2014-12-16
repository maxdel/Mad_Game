package core.model.gameplay.gameobjects.ai;

import java.util.Arrays;

import org.newdawn.slick.geom.Point;

import core.model.Timer;
import core.model.gameplay.gameobjects.Hero;
import core.model.gameplay.items.ItemInstanceKind;
import core.model.gameplay.skills.SkillInstanceKind;

public class BanditAI extends BotAI {

    private enum BanditAIState implements BotAIState {
        STAND, WALK, PURSUE, ATTACK, STRAFE, RETREAT
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
        currentState = BanditAIState.STAND;
        stateMap.put(BanditAIState.STAND, new AIState() {
            private Timer timer;
            public void enter()           { timer = new Timer(standTime); }
            public void run(int delta)    { owner.stand(); }
            public void update(int delta) { timer.update(delta);
                                            if (getDistanceToHero() < pursueDistance && seeTarget(Hero.getInstance())) { currentState = BanditAIState.PURSUE; return; }
                                            if (timer.isTime()) currentState = BanditAIState.WALK; }
        });
        stateMap.put(BanditAIState.WALK, new AIState() {
            private Point target;
            public void enter()           { target = getRandomTarget(); }
            public void run(int delta)    { followTarget(target); }
            public void update(int delta) { if (getDistanceToHero() < pursueDistance && seeTarget(Hero.getInstance())) { currentState = BanditAIState.PURSUE; return; }
                                            if (getDistanceToTarget(target) < 2) currentState = BanditAIState.STAND; }
        });
        stateMap.put(BanditAIState.PURSUE, new AIState() {
            private boolean isFollowing;
            public void enter()           { isFollowing = true; }
            public void run(int delta)    { isFollowing = followHero(); }
            public void update(int delta) { if (getDistanceToHero() >= pursueDistance || !isFollowing) { currentState = BanditAIState.STAND; return; }
                                            if (getDistanceToHero() < attackDistance && Math.random() < attackProbability) { currentState = BanditAIState.ATTACK; return; }
                                            if (getDistanceToHero() < attackDistance && Math.random() < strafeProbability) { currentState = BanditAIState.STRAFE; return; }
                                            if (getDistanceToHero() < attackDistance)  currentState = BanditAIState.RETREAT;  }
        });
        stateMap.put(BanditAIState.ATTACK, new AIState() {
            public void enter()           { }
            public void run(int delta)    { attackHeroWithSword(); }
            public void update(int delta) { if (getDistanceToHero() >= attackDistance && seeTarget(Hero.getInstance())) { currentState = BanditAIState.PURSUE; return; }
                                            if (Math.random() < strafeProbability) { currentState = BanditAIState.STRAFE; return; }
                                            currentState = BanditAIState.RETREAT; }
        });
        stateMap.put(BanditAIState.STRAFE, new AIState() {
            private Timer timer;
            private boolean strafeDirection;
            public void enter()           { timer = new Timer(strafeTime); strafeDirection = Math.random() < 0.5; }
            public void run(int delta)    { strafe(strafeDirection); }
            public void update(int delta) { timer.update(delta);
                                            if (timer.isTime() && getDistanceToHero() >= attackDistance && seeTarget(Hero.getInstance())) { currentState = BanditAIState.PURSUE; return; }
                                            if (timer.isTime() && Math.random() < attackProbability) { currentState = BanditAIState.ATTACK; return; }
                                            if (timer.isTime()) currentState = BanditAIState.RETREAT; }
        });
        stateMap.put(BanditAIState.RETREAT, new AIState() {
            private double currentRetreatDistance;
            public void enter()           { currentRetreatDistance = 0; }
            public void run(int delta)    { currentRetreatDistance += retreat(delta); }
            public void update(int delta) { if (currentRetreatDistance >= retreatDistance) currentState = BanditAIState.PURSUE; }
        });
    }

    private void attackHeroWithSword() {
        owner.stand();
        owner.getInventory().dressIfNotDressed(Arrays.asList(ItemInstanceKind.SWORD, ItemInstanceKind.STRONG_SWORD));
        owner.startCastSkill(SkillInstanceKind.SWORD_ATTACK);
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
