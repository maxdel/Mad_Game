package core.model.gameplay.gameobjects.ai;

import java.util.HashMap;
import java.util.Map;

import core.MathAdv;
import core.model.Timer;
import core.model.gameplay.CollisionManager;
import core.model.gameplay.World;

import core.model.gameplay.gameobjects.Bot;
import core.model.gameplay.gameobjects.GameObjectState;
import core.model.gameplay.skills.BulletSkill;
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
        currentState = VampireAIState.STAND;
        stateMap.put(VampireAIState.STAND, new AIState() {
            private Timer timer;
            public void enter()           { timer = new Timer(3000);                                                   }
            public void run()             { owner.stand();                                                             }
            public void update(int delta) { if (timer.update(delta))       currentState = VampireAIState.WALK;
                                            if (getDistanceToHero() < 300) currentState = VampireAIState.PURSUE;       }
        });
        stateMap.put(VampireAIState.WALK, new AIState() {
            private Point target;
            public void enter()           { target = getRandomTarget();                                                }
            public void run()             { followTarget(target);                                                      }
            public void update(int delta) { if (getDistanceToHero() < 300)       currentState = VampireAIState.PURSUE;
                                            if (getDistanceToTarget(target) < 2) currentState = VampireAIState.STAND;  }
        });
        stateMap.put(VampireAIState.PURSUE, new AIState() {
            public void enter()           {                                                                            }
            public void run()             { followHero();                                                              }
            public void update(int delta) { if (getDistanceToHero() >= 300) currentState = VampireAIState.STAND;
                                            if (getDistanceToHero() < 200)  currentState = VampireAIState.RANGEDATTACK;}
        });
        stateMap.put(VampireAIState.RANGEDATTACK, new AIState() {
            public void enter()           {                                                                            }
            public void run()             { attackHeroWithFireball();                                                  }
            public void update(int delta) { if (getDistanceToHero() >= 200) currentState = VampireAIState.PURSUE;
                                            if (getDistanceToHero() < 55)   currentState = VampireAIState.MELEEATTACK; }
        });
        stateMap.put(VampireAIState.MELEEATTACK, new AIState() {
            public void enter()           {                                                                            }
            public void run()             { attackHeroWithSword();                                                     }
            public void update(int delta) { if (getDistanceToHero() >= 55) currentState = VampireAIState.RANGEDATTACK; }
        });
    }

    // State methods

    private double getDistanceToHero() {
        return (new Vector2f((float) World.getInstance().getHero().getX() - (float)owner.getX(),
                (float)World.getInstance().getHero().getY() - (float)owner.getY())).length();
    }

    private Point getRandomTarget() {
        Point target = new Point(0, 0);
        int attemptNumber = 0;
        while (attemptNumber < 5) {
            double randomDistance = 30 + Math.random() * 10;
            double randomAngle = Math.random() * 2 * Math.PI;
            double tmpX = owner.getX() + MathAdv.lengthDirX(randomAngle, randomDistance);
            double tmpY = owner.getY() + MathAdv.lengthDirY(randomAngle, randomDistance);
            if (CollisionManager.getInstance().isPlaceFreeAdv(owner, tmpX, tmpY)) {
                target.setX((float) tmpX);
                target.setY((float) tmpY);
                return target;
            }
            attemptNumber++;
        }
        target.setX((float)owner.getX());
        target.setY((float)owner.getY());
        return target;
    }

    private void followTarget(Point target) {
        if (owner.getCurrentState() != GameObjectState.SKILL) {
            double direction = Math.atan2(target.getY() - owner.getY(), target.getX() - owner.getX());
            owner.setDirection(direction);
            owner.move();
        }
    }

    private void followHero() {
        followTarget(new Point((float)World.getInstance().getHero().getX(),
                (float)World.getInstance().getHero().getY()));
    }

    private double getDistanceToTarget(Point target) {
        return (new Vector2f(target.getX() - (float)owner.getX(),
                target.getY() - (float)owner.getY())).length();
    }

    private void attackHeroWithFireball() {
        owner.stand();
        owner.setDirection(getPredictedDirection(1));
        owner.getInventory().useItem(owner.getInventory().addItem("Staff"));
        owner.startCastSkill(1);
    }

    private void attackHeroWithSword() {
        owner.stand();
        owner.setDirection(getPredictedDirection(1));
        owner.getInventory().useItem(owner.getInventory().addItem("Sword"));
        owner.startCastSkill(0);
    }

    public double getPredictedDirection(int skillIndex) {
        Vector2f v = new Vector2f((float) World.getInstance().getHero().getX() - (float)owner.getX(),
                (float)World.getInstance().getHero().getY() - (float)owner.getY());
        double angleToTarget = v.getTheta() / 180 * Math.PI;
        double targetSpeed = World.getInstance().getHero().getAttribute().getCurrentSpeed();
        double targetDirection = World.getInstance().getHero().getDirection() + World.getInstance().getHero().getRelativeDirection();
        double bulletSpeed = ((BulletSkill) owner.getSkillList().get(skillIndex)).getBulletSpeed();

        if (targetSpeed > 0) {
            double alphaAngle = (Math.PI - targetDirection) + angleToTarget;
            double neededOffsetAngle = Math.asin(Math.sin(alphaAngle) * targetSpeed / bulletSpeed);
            return angleToTarget + neededOffsetAngle;
        } else {
            return angleToTarget;
        }
    }

}