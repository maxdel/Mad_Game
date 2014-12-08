package core.model.gameplay.units;

import java.util.HashMap;
import java.util.Map;

import core.model.Timer;
import core.model.gameplay.CollisionManager;
import core.model.gameplay.World;

import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Vector2f;

public class VampireAI {

    private Vampire vampire;
    private Map<AIStateEnum, AIState> stateMap;
    private AIStateEnum previousState;
    private AIStateEnum currentState;

    public VampireAI(Vampire vampire) {
        this.vampire = vampire;
        stateMap = new HashMap<AIStateEnum, AIState>();
        currentState = AIStateEnum.STAND;
        previousState = null;
        init();
    }

    private enum AIStateEnum {
        STAND, WALK, PURSUE, RANGEDATTACK, MELEEATTACK
    }

    public void run(int delta) {
        if (currentState != previousState) {
            stateMap.get(currentState).enter();
            previousState = currentState;
        }
        stateMap.get(currentState).run();
        stateMap.get(currentState).update(delta);
    }

    private void init() {
        stateMap.put(AIStateEnum.STAND, new AIState() {
            private Timer timer;
            public void enter()           { timer = new Timer(3000);                                                  }
            public void run()             { vampire.stand();                                                          }
            public void update(int delta) { if (timer.update(delta))       currentState = AIStateEnum.WALK;
                                            if (getDistanceToHero() < 300) currentState = AIStateEnum.PURSUE;         }
        });
        stateMap.put(AIStateEnum.WALK, new AIState() {
            private Point target;
            public void enter()           { target = getRandomTarget();                                               }
            public void run()             { followTarget(target);                                                     }
            public void update(int delta) { if (getDistanceToHero() < 300)       currentState = AIStateEnum.PURSUE;
                                            if (getDistanceToTarget(target) < 2) currentState = AIStateEnum.STAND;    }
        });
        stateMap.put(AIStateEnum.PURSUE, new AIState() {
            public void enter()           {                                                                           }
            public void run()             { followHero();                                                             }
            public void update(int delta) { if (getDistanceToHero() >= 300) currentState = AIStateEnum.STAND;
                                            if (getDistanceToHero() < 200)  currentState = AIStateEnum.RANGEDATTACK;  }
        });
        stateMap.put(AIStateEnum.RANGEDATTACK, new AIState() {
            public void enter()           {                                                                           }
            public void run()             { attackHeroWithFireball();                                                 }
            public void update(int delta) { if (getDistanceToHero() >= 200) currentState = AIStateEnum.PURSUE;
                                            if (getDistanceToHero() < 55)   currentState = AIStateEnum.MELEEATTACK;   }
        });
        stateMap.put(AIStateEnum.MELEEATTACK, new AIState() {
            public void enter()           {                                                                           }
            public void run()             { attackHeroWithSword();                                                    }
            public void update(int delta) { if (getDistanceToHero() >= 55) currentState = AIStateEnum.RANGEDATTACK;   }
        });
    }

    // State methods

    private double getDistanceToHero() {
        return (new Vector2f((float) World.getInstance().getHero().getX() - (float)vampire.getX(),
                (float)World.getInstance().getHero().getY() - (float)vampire.getY())).length();
    }

    private Point getRandomTarget() {
        Point target = new Point(0, 0);
        int attemptNumber = 0;
        while (attemptNumber < 5) {
            double randomDistance = 30 + Math.random() * 10;
            double randomAngle = Math.random() * 2 * Math.PI;
            double tmpX = vampire.getX() + lengthDirX(randomAngle, randomDistance);
            double tmpY = vampire.getY() + lengthDirY(randomAngle, randomDistance);
            if (CollisionManager.getInstance().isPlaceFreeAdv(vampire, tmpX, tmpY)) {
                target.setX((float) tmpX);
                target.setY((float) tmpY);
                return target;
            }
            attemptNumber++;
        }
        target.setX((float)vampire.getX());
        target.setY((float)vampire.getY());
        return target;
    }

    private double lengthDirX(double direction, double length) {
        return Math.cos(direction) * length;
    }

    private double lengthDirY(double direction, double length) {
        return Math.sin(direction) * length;
    }

    private void followTarget(Point target) {
        if (vampire.getCurrentState() != GameObjectState.CAST) {
            double direction = Math.atan2(target.getY() - vampire.getY(), target.getX() - vampire.getX());
            vampire.setDirection(direction);
            vampire.run(0);
        }
    }

    private void followHero() {
        followTarget(new Point((float)World.getInstance().getHero().getX(),
                (float)World.getInstance().getHero().getY()));
    }

    private double getDistanceToTarget(Point target) {
        return (new Vector2f(target.getX() - (float)vampire.getX(),
                target.getY() - (float)vampire.getY())).length();
    }

    private void attackHeroWithFireball() {
        vampire.stand();
        vampire.setDirection(vampire.getPredictedDirection(1));
        vampire.inventory.useItem(vampire.inventory.addItem("Staff"));
        vampire.startCastSkill(1);
    }

    private void attackHeroWithSword() {
        vampire.stand();
        vampire.setDirection(vampire.getPredictedDirection(1));
        vampire.inventory.useItem(vampire.inventory.addItem("Sword"));
        vampire.startCastSkill(0);
    }

    // Getters and setters

    public AIStateEnum getCurrentState() {
        return currentState;
    }

}