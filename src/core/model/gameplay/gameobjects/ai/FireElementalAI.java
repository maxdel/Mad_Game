package core.model.gameplay.gameobjects.ai;

import java.util.Arrays;

import org.newdawn.slick.geom.Point;

import core.model.Timer;
import core.model.gameplay.gameobjects.Hero;
import core.model.gameplay.items.ItemInstanceKind;
import core.model.gameplay.skills.SkillInstanceKind;

public class FireElementalAI extends BotAI {

    private enum FireElementalAIState implements BotAI.BotAIState {
        STAND, WALK, PURSUE, FORCE_PURSUE, ATTACK
    }

    @Override
    protected void init() {
        final int standTime = 3000;
        final int pursueDistance = 400;
        final int attackDistance = 200;
        final int forcePursueTime = 7000;
        currentState = FireElementalAIState.STAND;
        stateMap.put(FireElementalAIState.STAND, new AIState() {
            private Timer timer;
            private double previousHP;
            public void enter()           { timer = new Timer(standTime); previousHP = owner.getAttribute().getCurrentHP(); }
            public void run(int delta)    { owner.stand();                                                                 }
            public void update(int delta) { if (timer.update(delta)) { currentState = FireElementalAIState.WALK; return; }
                                            if (getDistanceToHero() < pursueDistance && seeTarget(Hero.getInstance())) { currentState = FireElementalAIState.PURSUE; return; }
                                            if (owner.getAttribute().getCurrentHP() < previousHP) { currentState = FireElementalAIState.FORCE_PURSUE; return; }
                                            previousHP = owner.getAttribute().getCurrentHP(); }
        });
        stateMap.put(FireElementalAIState.WALK, new AIState() {
            private Point target;
            private double previousHP;
            public void enter()           { target = getRandomTarget(); previousHP = owner.getAttribute().getCurrentHP(); }
            public void run(int delta)    { followTarget(target);                                                           }
            public void update(int delta) { if (getDistanceToHero() < pursueDistance && seeTarget(Hero.getInstance())) { currentState = FireElementalAIState.PURSUE; return; }
                                            if (getDistanceToTarget(target) < 2) { currentState = FireElementalAIState.STAND; return; }
                                            if (owner.getAttribute().getCurrentHP() < previousHP) { currentState = FireElementalAIState.FORCE_PURSUE; return; }
                                            previousHP = owner.getAttribute().getCurrentHP(); }
        });
        stateMap.put(FireElementalAIState.PURSUE, new AIState() {
            private boolean isFollowing;
            public void enter()           { isFollowing = true;                                                                  }
            public void run(int delta)    { isFollowing = followHero();         }
            public void update(int delta) { if (getDistanceToHero() >= pursueDistance || !isFollowing) currentState = FireElementalAIState.STAND;
                if (getDistanceToHero() < attackDistance && seeTarget(Hero.getInstance())) currentState = FireElementalAIState.ATTACK; }
        });
        stateMap.put(FireElementalAIState.FORCE_PURSUE, new AIState() {
            private Timer timer;
            private boolean isFollowing;
            public void enter()           { timer = new Timer(forcePursueTime); isFollowing = true;                                                                  }
            public void run(int delta)    { isFollowing = followHero();         }
            public void update(int delta) {
                timer.update(delta);
                if ((getDistanceToHero() >= pursueDistance && timer.isTime()) || !isFollowing) { currentState = FireElementalAIState.STAND; return; }
                if (getDistanceToHero() < attackDistance && seeTarget(Hero.getInstance())) { currentState = FireElementalAIState.ATTACK; return; } }
        });
        stateMap.put(FireElementalAIState.ATTACK, new AIState() {
            private boolean isAttacking;
            public void enter()           { isAttacking = true;                                                             }
            public void run(int delta)    { isAttacking = attackHeroWithFireball();                                      }
            public void update(int delta) { if (getDistanceToHero() >= attackDistance || !isAttacking) currentState = FireElementalAIState.PURSUE; }
        });
    }

    private boolean attackHeroWithFireball() {
        owner.stand();
        owner.setDirection(getPredictedDirection(owner.getSkillByKind(SkillInstanceKind.ELEMENTAL_FIREBALL)));
        if (seeTarget(Hero.getInstance())) {
            owner.getInventory().dressIfNotDressed(Arrays.asList(ItemInstanceKind.STAFF, ItemInstanceKind.STRONG_STAFF));
            owner.startCastSkill(SkillInstanceKind.ELEMENTAL_FIREBALL);
            return true;
        }
        return false;
    }

}