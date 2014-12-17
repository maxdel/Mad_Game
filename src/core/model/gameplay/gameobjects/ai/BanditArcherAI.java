package core.model.gameplay.gameobjects.ai;

import java.util.Arrays;

import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Vector2f;

import core.model.Timer;
import core.model.gameplay.gameobjects.Hero;
import core.model.gameplay.items.ItemInstanceKind;
import core.model.gameplay.skills.BulletShot;
import core.model.gameplay.skills.SkillInstanceKind;

public class BanditArcherAI extends BotAI {

    private enum BanditArcherAIState implements BotAI.BotAIState {
        STAND, WALK, PURSUE, ATTACK
    }

    @Override
    protected void init() {
        final int standTime = 3000;
        final int pursueDistance = 400;
        final int attackDistance = 200;
        currentState = BanditArcherAIState.STAND;
        stateMap.put(BanditArcherAIState.STAND, new AIState() {
            private Timer timer;
            public void enter()           { timer = new Timer(standTime);                                                  }
            public void run(int delta)    { owner.stand();                                                                 }
            public void update(int delta) { if (timer.update(delta)) currentState = BanditArcherAIState.WALK;
                                            if (getDistanceToHero() < pursueDistance && seeTarget(Hero.getInstance())) currentState = BanditArcherAIState.PURSUE; }
        });
        stateMap.put(BanditArcherAIState.WALK, new AIState() {
            private Point target;
            public void enter()           { target = getRandomTarget();                                                     }
            public void run(int delta)    { followTarget(target);                                                           }
            public void update(int delta) { if (getDistanceToHero() < pursueDistance && seeTarget(Hero.getInstance())) currentState = BanditArcherAIState.PURSUE;
                                            if (getDistanceToTarget(target) < 2)      currentState = BanditArcherAIState.STAND;   }
        });
        stateMap.put(BanditArcherAIState.PURSUE, new AIState() {
            private boolean isFollowing;
            public void enter()           { isFollowing = true;                                                                  }
            public void run(int delta)    { isFollowing = followHero();         }
            public void update(int delta) { if (getDistanceToHero() >= pursueDistance || !isFollowing) currentState = BanditArcherAIState.STAND;
                                            if (getDistanceToHero() < attackDistance && seeTarget(Hero.getInstance())) currentState = BanditArcherAIState.ATTACK; }
        });
        stateMap.put(BanditArcherAIState.ATTACK, new AIState() {
            private boolean isAttacking;
            public void enter()           { isAttacking = true;                                                             }
            public void run(int delta)    { isAttacking = attackHeroWithBowShot();                                      }
            public void update(int delta) { if (getDistanceToHero() >= attackDistance || !isAttacking) currentState = BanditArcherAIState.PURSUE; }
        });
    }

    private boolean attackHeroWithBowShot() {
        owner.stand();
        owner.setDirection(getPredictedDirection(owner.getSkillByKind(SkillInstanceKind.BANDIT_BOW_SHOT)));
        if (seeTarget(Hero.getInstance())) {
            owner.getInventory().dressIfNotDressed(Arrays.asList(ItemInstanceKind.BOW, ItemInstanceKind.STRONG_BOW));
            owner.startCastSkill(SkillInstanceKind.BANDIT_BOW_SHOT);
            return true;
        }
        return false;
    }
    
}