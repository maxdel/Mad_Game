package core.model.gameplay.gameobjects.ai;

import core.MathAdv;
import core.model.Timer;
import core.model.gameplay.CollisionManager;
import core.model.gameplay.World;
import core.model.gameplay.gameobjects.*;
import org.newdawn.slick.geom.Point;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BotAI {

    protected Bot owner;
    protected Map<BotAIState, AIState> stateMap;
    protected BotAIState currentState;
    private BotAIState previousState;
    private AStar aStar;
    private double lastTargetX;
    private double lastTargetY;
    private boolean usePath;
    private Timer rebuildTimer;

    public BotAI() {
        this(null);
    }

    public BotAI(Bot bot) {
        this.owner = bot;
        this.stateMap = new HashMap<>();
        this.currentState = null;
        this.previousState = null;
        this.aStar = new AStar();
        rebuildTimer = new Timer();
        init();
    }

    public interface BotAIState {

    }

    public void run(int delta) {
        if (currentState != previousState) {
            stateMap.get(currentState).enter();
            previousState = currentState;
        }
        stateMap.get(currentState).run(delta);
        stateMap.get(currentState).update(delta);
    }

    protected abstract void init();

    // AI methods

    protected double getDistanceToHero() {
        return MathAdv.getDistance(World.getInstance().getHero().getX(), World.getInstance().getHero().getY(),
                owner.getX(), owner.getY());
    }

    protected Point getRandomTarget() {
        Point target = new Point(0, 0);
        int attemptNumber = 0;
        while (attemptNumber < 5) {
            double randomDistance = 100 + Math.random() * 10;
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

    protected boolean followTarget(double x, double y) {
        return followTarget(new Point((float) x, (float) y));
    }

    protected boolean followTarget(GameObjectSolid target) {
        return followTarget(new Point((float) target.getX(), (float) target.getY()));
    }

    protected boolean followTarget(Point target) {
        double direction = Math.atan2(target.getY() - owner.getY(), target.getX() - owner.getX());
        owner.setDirection(direction);
        owner.move();
        if (MathAdv.getDistance(owner.getX(), owner.getY(), target.getX(), target.getY()) < 3 &&
                CollisionManager.getInstance().isPlaceFreeAdv(owner, target.getX(), target.getY())) {
            owner.setX(target.getX());
            owner.setY(target.getY());
            return false;
        }
        return true;
    }

    protected boolean followHero() {
        Unit hero = World.getInstance().getHero();
        if (seeTarget(hero)) {
            lastTargetX = hero.getX();
            lastTargetY = hero.getY();
            if (isDirectPathFree(hero)) {
                usePath = false;
                return followTarget(lastTargetX, lastTargetY);
            } else {
                if (!usePath && rebuildTimer.isTime()) {
                    aStar.buildPath(hero, owner, lastTargetX, lastTargetY);
                    aStar.removeFrom(aStar.getFirstReachablePoint(owner), false);
                    usePath = true;
                    System.out.print("********Fucking rebuild!");
                    rebuildTimer.activate(100);
                }
                Point p = aStar.getFirstReachablePoint(owner);
                boolean isFollowing = followTarget(p);
                System.out.println("Follow:" + isFollowing);
                System.out.println("Follow(X:Y):" + p.getX() + ":" + p.getY());
                if (!isFollowing) {
                    aStar.removeFrom(p, true);
                }
                if (aStar.getPath().size() > 1) {
                    return true;
                } else {
                    return isFollowing;
                }
            }
        } else {
            usePath = false;
            if (aStar.getFirstReachablePoint(owner) == null) {
                aStar.buildPath(hero, owner, lastTargetX, lastTargetY);
                aStar.removeFrom(aStar.getFirstReachablePoint(owner), false);
                usePath = true;
            }
            aStar.removeFrom(aStar.getFirstReachablePoint(owner), false);
            boolean isFollowing = followTarget(aStar.getFirstReachablePoint(owner));
            if (!isFollowing) {
                aStar.removeFrom(aStar.getFirstReachablePoint(owner), true);
            }
            if (aStar.getPath().size() > 1) {
                return true;
            } else {
                return isFollowing;
            }
        }
    }

    protected boolean seeTarget(GameObjectSolid target) {
        int step = 1;
        double direction = Math.atan2(target.getY() - owner.getY(), target.getX() - owner.getX());
        Bullet dummy = new Bullet(owner, owner.getX(), owner.getY(), direction, 0, 0, 0, GameObjInstanceKind.ARROW);
        for (int i = 0; i < MathAdv.getDistance(owner.getX(), owner.getY(), target.getX(), target.getY()) / step; ++i) {
            GameObjectSolid collisionObject = CollisionManager.getInstance().collidesWith(dummy,
                    owner.getX() + MathAdv.lengthDirX(direction, step * i),
                    owner.getY() + MathAdv.lengthDirY(direction, step * i));
            if (collisionObject != owner && collisionObject != null && collisionObject != target && !(collisionObject instanceof Bullet)) {
                return false;
            }
        }
        System.out.println("See" + Math.random());
        return true;
    }

    protected double getDistanceToTarget(Point target) {
        return MathAdv.getDistance(target.getX(), target.getY(), owner.getX(), owner.getY());
    }

    protected boolean isDirectPathFree(Unit target) {
        int step = 5;
        double currentDirection = Math.atan2(target.getY() - owner.getY(), target.getX() - owner.getX());
        for (int j = 0; j < MathAdv.getDistance(owner.getX(), owner.getY(), target.getX(), target.getY()) / step; ++j) {
            GameObjectSolid collisionObject = CollisionManager.getInstance().collidesWith(owner,
                    owner.getX() + MathAdv.lengthDirX(currentDirection, step * j),
                    owner.getY() + MathAdv.lengthDirY(currentDirection, step * j));
            if (collisionObject != owner && collisionObject != null && !(collisionObject instanceof Bullet) && collisionObject != target) {
                return false;
            }
        }
        return true;
    }

    public List<Cell> getPath() {
        return aStar.getPath();
    }

    // Getters and setters

    public BotAIState getCurrentState() {
        return currentState;
    }

    public void setOwner(Bot owner) {
        this.owner = owner;
    }

    public AStar getAStar() {
        return aStar;
    }
}