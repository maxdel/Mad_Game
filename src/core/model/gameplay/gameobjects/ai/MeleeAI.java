package core.model.gameplay.gameobjects.ai;

import core.model.Timer;
import core.model.gameplay.gameobjects.Bot;
import core.model.gameplay.items.ItemInstanceKind;
import core.model.gameplay.skills.SkillInstanceKind;
import org.newdawn.slick.geom.Point;

public class MeleeAI extends BotAI {

    public MeleeAI() {
        this(null);
    }

    public MeleeAI(Bot bot) {
        super(bot);
    }

    private enum MeleeAIState implements BotAIState {

        STAND, WALK, PURSUE, ATTACK

    }

    @Override
    protected void init() {
        final int standTime = 1000;
        final int pursueDistance = 300;
        final int attackDistance = 55;
        currentState = MeleeAIState.STAND;
        stateMap.put(MeleeAIState.STAND, new AIState() {
            private Timer timer;
            public void enter()           { timer = new Timer(standTime);                                                  }
            public void run()             { owner.stand();                                                                 }
            public void update(int delta) { if (timer.update(delta))                  currentState = MeleeAIState.WALK;
                                            if (getDistanceToHero() < pursueDistance) currentState = MeleeAIState.PURSUE;  }
        });
        stateMap.put(MeleeAIState.WALK, new AIState() {
            private Point target;
            public void enter()           { target = getRandomTarget();                                                    }
            public void run()             { followTarget(target);                                                          }
            public void update(int delta) { if (getDistanceToHero() < pursueDistance) currentState = MeleeAIState.PURSUE;
                                            if (getDistanceToTarget(target) < 2)      currentState = MeleeAIState.STAND;   }
        });
        stateMap.put(MeleeAIState.PURSUE, new AIState() {
            public void enter()           {                                                                                }
            public void run()             { followHero();                                                                  }
            public void update(int delta) { if (getDistanceToHero() >= pursueDistance) currentState = MeleeAIState.STAND;
                                            if (getDistanceToHero() < attackDistance)  currentState = MeleeAIState.ATTACK; }
        });
        stateMap.put(MeleeAIState.ATTACK, new AIState() {
            public void enter()           {                                                                                }
            public void run()             { attackHeroWithSword();                                                         }
            public void update(int delta) { if (getDistanceToHero() >= attackDistance) currentState = MeleeAIState.PURSUE; }
        });
    }

    private void attackHeroWithSword() {
        owner.stand();
        //owner.setDirection(getPredictedDirection(1));
        owner.getInventory().useItem(owner.getInventory().addItem(ItemInstanceKind.SWORD));
        owner.startCastSkill(SkillInstanceKind.SWORD_ATTACK);
    }

}
