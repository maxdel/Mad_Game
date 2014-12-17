package core.model.gameplay.gameobjects.ai;

import core.MathAdv;
import core.model.gameplay.CollisionManager;
import core.model.gameplay.skills.BlinkSkill;
import org.newdawn.slick.geom.Point;

import core.model.Timer;
import core.model.gameplay.gameobjects.Hero;
import core.model.gameplay.items.ItemInstanceKind;
import core.model.gameplay.skills.SkillInstanceKind;

import java.util.Arrays;

public class VampireAI extends BotAI {

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
            public void enter() { timer = new Timer(standTime); }
            public void run(int delta) { owner.stand(); }
            public void update(int delta) { if (timer.update(delta)) currentState = VampireAIState.WALK;
                                            if (getDistanceToHero() < pursueDistance && seeTarget(Hero.getInstance())) currentState = VampireAIState.PURSUE; }
        });
        stateMap.put(VampireAIState.WALK, new AIState() {
            private Point target;
            public void enter() { target = getRandomTarget(); }
            public void run(int delta) { followTarget(target); }
            public void update(int delta) { if (getDistanceToHero() < pursueDistance && seeTarget(Hero.getInstance())) currentState = VampireAIState.PURSUE;
                                            if (getDistanceToTarget(target) < 2) currentState = VampireAIState.STAND; }
        });
        stateMap.put(VampireAIState.PURSUE, new AIState() {
            private boolean isFollowing;
            public void enter() {isFollowing = true; }
            public void run(int delta) { isFollowing = followHero(); }
            public void update(int delta) { if (getDistanceToHero() >= pursueDistance || !isFollowing) currentState = VampireAIState.STAND;
                                            if (getDistanceToHero() < rangedAttackDistance && seeTarget(Hero.getInstance())) currentState = VampireAIState.RANGEDATTACK;
                                            if (getDistanceToHero() < meleeAttackDistance) currentState = VampireAIState.MELEEATTACK; }
        });
        stateMap.put(VampireAIState.RANGEDATTACK, new AIState() {
            private final int BLINK_TIME = 200;
            private Timer blinkTimer;
            private final int POWER_BEAM_TIME = 1000;
            private Timer powerBeamTimer;
            private final int DOOM_TIME = 4333;
            private Timer doomTimer;
            private boolean isAttacking;
            public void enter() { blinkTimer = new Timer(BLINK_TIME);
                                  powerBeamTimer = new Timer(POWER_BEAM_TIME);
                                  doomTimer = new Timer(DOOM_TIME);
                                  isAttacking = true; }
            public void run(int delta) { isAttacking = RangedAttackBehavior(blinkTimer, BLINK_TIME,
                                            powerBeamTimer, POWER_BEAM_TIME, doomTimer, DOOM_TIME);
                                         blinkTimer.update(delta);
                                         powerBeamTimer.update(delta);
                                         doomTimer.update(delta); }
            public void update(int delta) { if (getDistanceToHero() >= rangedAttackDistance || !isAttacking) currentState = VampireAIState.PURSUE;
                                            if (getDistanceToHero() < meleeAttackDistance) currentState = VampireAIState.MELEEATTACK; }
        });
        stateMap.put(VampireAIState.MELEEATTACK, new AIState() {
            private final int BLINK_TIME = 1500;
            private Timer blinkTimer;
            public void enter() {blinkTimer = new Timer(BLINK_TIME); }
            public void run(int delta) { MeleeAttackBehavior(blinkTimer, BLINK_TIME); blinkTimer.update(delta); }
            public void update(int delta) { if (getDistanceToHero() >= meleeAttackDistance) currentState = VampireAIState.RANGEDATTACK;
                                            if (getDistanceToHero() >= pursueDistance) currentState = VampireAIState.PURSUE; }
        });
    }

    private boolean RangedAttackBehavior(Timer blinkTimer, int blinkTime, Timer powerBeamTimer, int powerBeamTime,
                                         Timer doomTimer, int doomTime) {
        owner.setDirection(MathAdv.getAngle(owner.getX(), owner.getY(),
                Hero.getInstance().getX(), Hero.getInstance().getY()));
        if (blinkTimer.isTime() && owner.canStartCast(owner.getSkillByKind(SkillInstanceKind.BLINK))) {
            double blinkDirectionFirstAttemp = calculateBlinkDirectionModule() * (Math.random() < 0.5 ? 1 : -1);
            if (canBlink(blinkDirectionFirstAttemp)) {
                owner.setRelativeDirection(blinkDirectionFirstAttemp);
                owner.startCastSkill(SkillInstanceKind.BLINK);
                blinkTimer.activate(blinkTime);
                return true;
            } else if (canBlink(-blinkDirectionFirstAttemp)) {
                owner.setRelativeDirection(-blinkDirectionFirstAttemp);
                owner.startCastSkill(SkillInstanceKind.BLINK);
                blinkTimer.activate(blinkTime);
                return true;
            }
        }

        owner.stand();
        owner.setDirection(getPredictedDirection(owner.getSkillByKind(SkillInstanceKind.VAMPIRIC_KNIFE)));
        if (seeTarget(Hero.getInstance())) {
            if (powerBeamTimer.isTime() && owner.canStartCast(owner.getSkillByKind(SkillInstanceKind.POWER_BEAM))) {
                owner.startCastSkill(SkillInstanceKind.POWER_BEAM);
                powerBeamTimer.activate(powerBeamTime);
            } else if (doomTimer.isTime() && owner.canStartCast(owner.getSkillByKind(SkillInstanceKind.DOOM))) {
                owner.startCastSkill(SkillInstanceKind.DOOM);
                doomTimer.activate(doomTime);
            } else {
                owner.startCastSkill(SkillInstanceKind.VAMPIRIC_KNIFE);
            }
            return true;
        }
        return false;
    }

    private double calculateBlinkDirectionModule() {
        double blinkDistance = ((BlinkSkill)owner.getSkillByKind(SkillInstanceKind.BLINK)).getDistance();
        double distanceToHero = MathAdv.getDistance(owner.getX(), owner.getY(),
                Hero.getInstance().getX(), Hero.getInstance().getY());
        return Math.acos(0.5 * blinkDistance / distanceToHero);
    }

    private boolean canBlink(double blinkDirection) {
        double direction = owner.getDirection() + blinkDirection;
        double blinkDistance = ((BlinkSkill)owner.getSkillByKind(SkillInstanceKind.BLINK)).getDistance();
        double distanceToHero = MathAdv.getDistance(owner.getX(), owner.getY(),
                Hero.getInstance().getX(), Hero.getInstance().getY());
        double lengthDirX = MathAdv.lengthDirX(direction, blinkDistance);
        double lengthDirY = MathAdv.lengthDirY(direction, blinkDistance);
        return (CollisionManager.getInstance().isPlaceFreeAdv(owner, owner.getX() + lengthDirX, owner.getY() + lengthDirY) &&
                seeTarget(Hero.getInstance(), owner.getX() + lengthDirX, owner.getY() + lengthDirY) &&
                blinkDistance <= 2 * distanceToHero);
    }

    private void MeleeAttackBehavior(Timer blinkTimer, int blinkTime) {
        owner.setDirection(MathAdv.getAngle(owner.getX(), owner.getY(),
                Hero.getInstance().getX(), Hero.getInstance().getY()));
        if (blinkTimer.isTime() && owner.canStartCast(owner.getSkillByKind(SkillInstanceKind.BLINK))) {
            double blinkDirection = Math.random() * 2 * Math.PI;
            double blinkDistance = ((BlinkSkill)owner.getSkillByKind(SkillInstanceKind.BLINK)).getDistance();
            double lengthDirX = MathAdv.lengthDirX(blinkDirection, blinkDistance);
            double lengthDirY = MathAdv.lengthDirY(blinkDirection, blinkDistance);
            if (CollisionManager.getInstance().isPlaceFreeAdv(owner, owner.getX() + lengthDirX, owner.getY() + lengthDirY) &&
                    seeTarget(Hero.getInstance(), owner.getX() + lengthDirX, owner.getY() + lengthDirY)) {
                owner.setRelativeDirection(blinkDirection);
                owner.startCastSkill(SkillInstanceKind.BLINK);
                blinkTimer.activate(blinkTime);
                return;
            }
        }
        owner.stand();
        owner.setDirection(MathAdv.getAngle(owner.getX(), owner.getY(),
                Hero.getInstance().getX(), Hero.getInstance().getY()));
        owner.getInventory().dressIfNotDressed(Arrays.asList(ItemInstanceKind.SWORD, ItemInstanceKind.STRONG_SWORD));
        owner.startCastSkill(SkillInstanceKind.SWORD_ATTACK);
        owner.startCastSkill(SkillInstanceKind.SWORD_SPIN);
    }

}