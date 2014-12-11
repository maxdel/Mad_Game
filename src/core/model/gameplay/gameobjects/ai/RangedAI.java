package core.model.gameplay.gameobjects.ai;

import core.model.Timer;
import core.model.gameplay.World;
import core.model.gameplay.gameobjects.Bot;
import core.model.gameplay.skills.BulletSkill;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Vector2f;

public class RangedAI extends BotAI {

    public RangedAI() {
        this(null);
    }

    public RangedAI(Bot bot) {
        super(bot);
    }

    private enum RangedAIState implements BotAI.BotAIState {

        STAND, WALK, PURSUE, ATTACK

    }

    @Override
    protected void init() {
        currentState = RangedAIState.STAND;
        stateMap.put(RangedAIState.STAND, new AIState() {
            private Timer timer;
            public void enter()           { timer = new Timer(3000);                                                   }
            public void run()             { owner.stand();                                                             }
            public void update(int delta) { if (timer.update(delta))       currentState = RangedAIState.WALK;
                                            if (getDistanceToHero() < 400) currentState = RangedAIState.PURSUE;         }
        });
        stateMap.put(RangedAIState.WALK, new AIState() {
            private Point target;
            public void enter()           { target = getRandomTarget();                                                }
            public void run()             { followTarget(target);                                                      }
            public void update(int delta) { if (getDistanceToHero() < 400)       currentState = RangedAIState.PURSUE;
                                            if (getDistanceToTarget(target) < 2) currentState = RangedAIState.STAND;  }
        });
        stateMap.put(RangedAIState.PURSUE, new AIState() {
            public void enter()           {                                                                            }
            public void run()             { followHero();                                                              }
            public void update(int delta) { if (getDistanceToHero() >= 400) currentState = RangedAIState.STAND;
                                            if (getDistanceToHero() < 220)  currentState = RangedAIState.ATTACK;}
        });
        stateMap.put(RangedAIState.ATTACK, new AIState() {
            public void enter()           {                                                                            }
            public void run()             { attackHeroWithRangedSkill();                                               }
            public void update(int delta) { if (getDistanceToHero() >= 220) currentState = RangedAIState.PURSUE;         }
        });
    }

    private void attackHeroWithRangedSkill() {
        owner.stand();
        owner.setDirection(getPredictedDirection(0));
        if (owner.getSkillList().get(0).getName().equals("Bow shot")) {
            owner.getInventory().useItem(owner.getInventory().addItem("Strong bow"));
        } else {
            owner.getInventory().useItem(owner.getInventory().addItem("Staff"));
        }
        owner.startCastSkill(0);
    }

    private double getPredictedDirection(int skillIndex) {
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