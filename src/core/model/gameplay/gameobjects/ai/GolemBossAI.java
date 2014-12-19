package core.model.gameplay.gameobjects.ai;

import org.newdawn.slick.geom.Point;

import core.model.Timer;
import core.model.gameplay.gameobjects.Hero;
import core.model.gameplay.skills.SkillInstanceKind;

public class GolemBossAI extends BotAI {

    private enum GolemBossAIState implements BotAIState {
        STAND, WALK, PURSUE, FORCE_PURSUE, ATTACK, STRAFE, RETREAT
    }

    @Override
    protected void init() {
        final int standTime = 1000;
        final int pursueDistance = 300;
        final int attackDistance = (int) (owner.getMask().getBoundingCircleRadius() +
                Hero.getInstance().getMask().getBoundingCircleRadius() + 5);
        final int retreatDistance = 30;
        final int strafeTime = 250;
        final double attackProbability = 0.7;
        final double strafeProbability = 0.7;
        final int forcePursueTime = 7000;
        currentState = GolemBossAIState.STAND;
        stateMap.put(GolemBossAIState.STAND, new AIState() {
            private Timer timer;
            private double previousHP;
            public void enter()           { timer = new Timer(standTime); previousHP = owner.getAttribute().getCurrentHP(); }
            public void run(int delta)    { owner.stand(); }
            public void update(int delta) { timer.update(delta);
                                            if (getDistanceToHero() < pursueDistance && seeTarget(Hero.getInstance())) { currentState = GolemBossAIState.PURSUE; return; }
                                            if (timer.isTime()) { currentState = GolemBossAIState.WALK; return; }
                                            if (owner.getAttribute().getCurrentHP() < previousHP) { currentState = GolemBossAIState.FORCE_PURSUE; return; }
                                            previousHP = owner.getAttribute().getCurrentHP();  }
        });
        stateMap.put(GolemBossAIState.WALK, new AIState() {
            private Point target;
            private double previousHP;
            public void enter()           { target = getRandomTarget(); previousHP = owner.getAttribute().getCurrentHP(); }
            public void run(int delta)    { followTarget(target); }
            public void update(int delta) { if (getDistanceToHero() < pursueDistance && seeTarget(Hero.getInstance())) { currentState = GolemBossAIState.PURSUE; return; }
                                            if (getDistanceToTarget(target) < 2) { currentState = GolemBossAIState.STAND; return; }
                                            if (owner.getAttribute().getCurrentHP() < previousHP) { currentState = GolemBossAIState.FORCE_PURSUE; return; }
                                            previousHP = owner.getAttribute().getCurrentHP();  }
        });
        stateMap.put(GolemBossAIState.PURSUE, new AIState() {
            private boolean isFollowing;
            private double previousHP;
            public void enter()           { isFollowing = true; previousHP = owner.getAttribute().getCurrentHP(); }
            public void run(int delta)    { isFollowing = followHero(); checkThornSpawn(owner.getAttribute().getCurrentHP() < previousHP); previousHP = owner.getAttribute().getCurrentHP(); attackHeroWithThrowStone(); }
            public void update(int delta) { if (getDistanceToHero() >= pursueDistance || !isFollowing) { currentState = GolemBossAIState.STAND; return; }
                if (getDistanceToHero() < attackDistance && Math.random() < attackProbability) { currentState = GolemBossAIState.ATTACK; return; }
                if (getDistanceToHero() < attackDistance && Math.random() < strafeProbability) { currentState = GolemBossAIState.STRAFE; return; }
                if (getDistanceToHero() < attackDistance)  currentState = GolemBossAIState.RETREAT;  }
        });
        stateMap.put(GolemBossAIState.FORCE_PURSUE, new AIState() {
            private Timer timer;
            private boolean isFollowing;
            private double previousHP;
            public void enter()           { timer = new Timer(forcePursueTime); isFollowing = true; previousHP = owner.getAttribute().getCurrentHP(); }
            public void run(int delta)    { isFollowing = followHero(); checkThornSpawn(owner.getAttribute().getCurrentHP() < previousHP); previousHP = owner.getAttribute().getCurrentHP(); attackHeroWithThrowStone(); }
            public void update(int delta) {
                timer.update(delta);
                if ((getDistanceToHero() >= pursueDistance && timer.isTime()) || !isFollowing) { currentState = GolemBossAIState.STAND; return; }
                if (getDistanceToHero() < attackDistance && Math.random() < attackProbability) { currentState = GolemBossAIState.ATTACK; return; }
                if (getDistanceToHero() < attackDistance && Math.random() < strafeProbability) { currentState = GolemBossAIState.STRAFE; return; }
                if (getDistanceToHero() < attackDistance)  currentState = GolemBossAIState.RETREAT;  }
        });
        stateMap.put(GolemBossAIState.ATTACK, new AIState() {
            private double previousHP;
            public void enter()           { previousHP = owner.getAttribute().getCurrentHP(); }
            public void run(int delta)    { attackHeroWithPunch(); checkThornSpawn(owner.getAttribute().getCurrentHP() < previousHP); previousHP = owner.getAttribute().getCurrentHP(); }
            public void update(int delta) { if (getDistanceToHero() >= attackDistance && seeTarget(Hero.getInstance())) { currentState = GolemBossAIState.PURSUE; return; }
                                            if (Math.random() < strafeProbability) { currentState = GolemBossAIState.STRAFE; return; }
                                            currentState = GolemBossAIState.RETREAT; }
        });
        stateMap.put(GolemBossAIState.STRAFE, new AIState() {
            private Timer timer;
            private boolean strafeDirection;
            private double previousHP;
            public void enter() { timer = new Timer(strafeTime); strafeDirection = Math.random() < 0.5; previousHP = owner.getAttribute().getCurrentHP(); }
            public void run(int delta) { strafe(strafeDirection); checkThornSpawn(owner.getAttribute().getCurrentHP() < previousHP); previousHP = owner.getAttribute().getCurrentHP(); }
            public void update(int delta) { timer.update(delta);
                                            if (timer.isTime() && getDistanceToHero() >= attackDistance && seeTarget(Hero.getInstance())) { currentState = GolemBossAIState.PURSUE; return; }
                                            if (timer.isTime() && Math.random() < attackProbability) { currentState = GolemBossAIState.ATTACK; return; }
                                            if (timer.isTime()) currentState = GolemBossAIState.RETREAT; }
        });
        stateMap.put(GolemBossAIState.RETREAT, new AIState() {
            private double currentRetreatDistance;
            private double previousHP;
            public void enter()           { currentRetreatDistance = 0; previousHP = owner.getAttribute().getCurrentHP(); }
            public void run(int delta)    { currentRetreatDistance += retreat(delta); checkThornSpawn(owner.getAttribute().getCurrentHP() < previousHP); previousHP = owner.getAttribute().getCurrentHP(); }
            public void update(int delta) { if (currentRetreatDistance >= retreatDistance) currentState = GolemBossAIState.PURSUE; }
        });
    }

    private void attackHeroWithPunch() {
        owner.stand();
        owner.startCastSkill(SkillInstanceKind.STRONG_PUNCH);
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

    private void checkThornSpawn(boolean wasAttacked) {
        if (wasAttacked) {
            owner.startCastSkill(SkillInstanceKind.STRONG_THORN);
        }
    }

    private void attackHeroWithThrowStone() {
        if (owner.canStartCast(owner.getSkillByKind(SkillInstanceKind.THROW_STONE)) && seeTarget(Hero.getInstance())) {
            owner.stand();
            owner.setDirection(getPredictedDirection(owner.getSkillByKind(SkillInstanceKind.THROW_STONE)));
            owner.startCastSkill(SkillInstanceKind.THROW_STONE);
        }
    }

}