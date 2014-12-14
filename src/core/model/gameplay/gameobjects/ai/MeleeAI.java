package core.model.gameplay.gameobjects.ai;

import core.model.Timer;
import core.model.gameplay.World;
import core.model.gameplay.gameobjects.Bot;
import core.model.gameplay.gameobjects.Hero;
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
        currentState = MeleeAIState.STAND;
        stateMap.put(MeleeAIState.STAND, new AIState() {
            private Timer timer;
            public void enter()           { timer = new Timer(standTime); }
            public void run(int delta)    { owner.stand(); }
            public void update(int delta) { timer.update(delta);
                                            if (getDistanceToHero() < pursueDistance) { currentState = MeleeAIState.PURSUE; return; }
                                            if (timer.isTime()) currentState = MeleeAIState.WALK; }
        });
        stateMap.put(MeleeAIState.WALK, new AIState() {
            private Point target;
            public void enter()           { target = getRandomTarget(); }
            public void run(int delta)    { followTarget(target); }
            public void update(int delta) { if (getDistanceToHero() < pursueDistance) { currentState = MeleeAIState.PURSUE; return; }
                                            if (getDistanceToTarget(target) < 2) currentState = MeleeAIState.STAND; }
        });
        stateMap.put(MeleeAIState.PURSUE, new AIState() {
            public void enter()           { }
            public void run(int delta)    { followHero(); }
            public void update(int delta) { if (getDistanceToHero() >= pursueDistance) { currentState = MeleeAIState.STAND; return; }
                                            if (getDistanceToHero() < attackDistance && Math.random() < attackProbability) { currentState = MeleeAIState.ATTACK; return; }
                                            if (getDistanceToHero() < attackDistance && Math.random() < strafeProbability) { currentState = MeleeAIState.STRAFE; return; }
                                            if (getDistanceToHero() < attackDistance)  currentState = MeleeAIState.RETREAT;  }
        });
        stateMap.put(MeleeAIState.ATTACK, new AIState() {
            public void enter()           { }
            public void run(int delta)    { attackHeroWithSword(); }
            public void update(int delta) { if (getDistanceToHero() >= attackDistance) { currentState = MeleeAIState.PURSUE; return; }
                                            if (Math.random() < strafeProbability) { currentState = MeleeAIState.STRAFE; return; }
                                            currentState = MeleeAIState.RETREAT; }
        });
        stateMap.put(MeleeAIState.STRAFE, new AIState() {
            private Timer timer;
            private boolean strafeDirection;
            public void enter()           { timer = new Timer(strafeTime); strafeDirection = Math.random() < 0.5; }
            public void run(int delta)    { strafe(strafeDirection); }
            public void update(int delta) { timer.update(delta);
                                            if (timer.isTime() && getDistanceToHero() >= attackDistance) { currentState = MeleeAIState.PURSUE; return; }
                                            if (timer.isTime() && Math.random() < attackProbability) { currentState = MeleeAIState.ATTACK; return; }
                                            if (timer.isTime()) currentState = MeleeAIState.RETREAT; }
        });
        stateMap.put(MeleeAIState.RETREAT, new AIState() {
            private double currentRetreatDistance;
            public void enter()           { currentRetreatDistance = 0; }
            public void run(int delta)    { currentRetreatDistance += retreat(delta); }
            public void update(int delta) { if (currentRetreatDistance >= retreatDistance) currentState = MeleeAIState.PURSUE; }
        });
    }

    private void attackHeroWithSword() {
        owner.stand();
        //owner.setDirection(getPredictedDirection(1));
        owner.getInventory().useItem(owner.getInventory().addItem(ItemInstanceKind.SWORD));
        owner.startCastSkill(SkillInstanceKind.SWORD_ATTACK);
    }

    private void strafe(boolean strafeDirection) {
        Hero hero = World.getInstance().getHero();
        owner.setDirection(Math.atan2(hero.getY() - owner.getY(), hero.getX() - owner.getX()));
        owner.move(Math.PI / 2 * (strafeDirection ? 1 : -1));
    }

    private double retreat(int delta) {
        Hero hero = World.getInstance().getHero();
        owner.setDirection(Math.atan2(hero.getY() - owner.getY(), hero.getX() - owner.getX()));
        owner.move(Math.PI);
        return owner.getAttribute().getCurrentSpeed() * delta;
    }

}
