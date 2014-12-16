package core.model.gameplay.gameobjects.ai;

import core.model.Timer;
import core.model.gameplay.World;

import core.model.gameplay.gameobjects.Bot;
import core.model.gameplay.gameobjects.Hero;
import core.model.gameplay.items.ItemInstanceKind;
import core.model.gameplay.skills.BulletShot;
import core.model.gameplay.skills.SkillInstanceKind;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Vector2f;

public class VampireAI extends BotAI {

    public VampireAI() {
        this(null);
    }

    public VampireAI(Bot bot) {
        super(bot);
    }

    private enum VampireAIState implements BotAIState {
        STAND, WALK, PURSUE, RANGEDATTACK, MELEEATTACK
    }

    @Override
    protected void init() {
        final int standTime = 3000;
        final int pursueDistance = 300;
        final int rangedAttackDistance = 220;
        final int meleeAttackDistance = 55;
        currentState = VampireAIState.STAND;
        stateMap.put(VampireAIState.STAND, new AIState() {
            private Timer timer;
            public void enter()           { timer = new Timer(standTime);                                                                }
            public void run(int delta)             { owner.stand();                                                                               }
            public void update(int delta) { if (timer.update(delta))                  currentState = VampireAIState.WALK;
                                            if (getDistanceToHero() < pursueDistance) currentState = VampireAIState.PURSUE;              }
        });
        stateMap.put(VampireAIState.WALK, new AIState() {
            private Point target;
            public void enter()           { target = getRandomTarget();                                                                  }
            public void run(int delta)             { followTarget(target);                                                                        }
            public void update(int delta) { if (getDistanceToHero() < pursueDistance) currentState = VampireAIState.PURSUE;
                                            if (getDistanceToTarget(target) < 2)      currentState = VampireAIState.STAND;               }
        });
        stateMap.put(VampireAIState.PURSUE, new AIState() {
            public void enter()           {                                                                                              }
            public void run(int delta)             { followHero();                                                                                }
            public void update(int delta) { if (getDistanceToHero() >= pursueDistance)       currentState = VampireAIState.STAND;
                                            if (getDistanceToHero() < rangedAttackDistance)  currentState = VampireAIState.RANGEDATTACK; }
        });
        stateMap.put(VampireAIState.RANGEDATTACK, new AIState() {
            public void enter()           {                                                                                              }
            public void run(int delta)             { attackHeroWithFireball();                                                                    }
            public void update(int delta) { if (getDistanceToHero() >= rangedAttackDistance) currentState = VampireAIState.PURSUE;
                                            if (getDistanceToHero() < meleeAttackDistance)   currentState = VampireAIState.MELEEATTACK;  }
        });
        stateMap.put(VampireAIState.MELEEATTACK, new AIState() {
            public void enter()           {                                                                                              }
            public void run(int delta)             { attackHeroWithSword();                                                                       }
            public void update(int delta) { if (getDistanceToHero() >= meleeAttackDistance) currentState = VampireAIState.RANGEDATTACK;  }
        });
    }

    private void attackHeroWithFireball() {
        owner.stand();
        owner.setDirection(getPredictedDirection(1));
        owner.getInventory().useItem(owner.getInventory().addItem(ItemInstanceKind.STAFF));
        owner.startCastSkill(SkillInstanceKind.FIREBALL);
    }

    private void attackHeroWithSword() {
        owner.stand();
        owner.setDirection(getPredictedDirection(1));
        owner.getInventory().useItem(owner.getInventory().addItem(ItemInstanceKind.SWORD));
        owner.startCastSkill(SkillInstanceKind.SWORD_ATTACK);
    }

    private double getPredictedDirection(int skillIndex) {
        Vector2f v = new Vector2f((float) Hero.getInstance().getX() - (float)owner.getX(),
                (float)Hero.getInstance().getY() - (float)owner.getY());
        double angleToTarget = v.getTheta() / 180 * Math.PI;
        double targetSpeed = Hero.getInstance().getAttribute().getCurrentSpeed();
        double targetDirection = Hero.getInstance().getDirection() + Hero.getInstance().getRelativeDirection();
        double bulletSpeed = ((BulletShot) owner.getSkillList().get(skillIndex)).getBulletSpeed();

        if (targetSpeed > 0) {
            double alphaAngle = (Math.PI - targetDirection) + angleToTarget;
            double neededOffsetAngle = Math.asin(Math.sin(alphaAngle) * targetSpeed / bulletSpeed);
            return angleToTarget + neededOffsetAngle;
        } else {
            return angleToTarget;
        }
    }

}