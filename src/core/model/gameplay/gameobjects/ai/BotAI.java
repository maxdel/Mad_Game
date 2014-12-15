package core.model.gameplay.gameobjects.ai;

import core.MathAdv;
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
    private boolean isTargetVisible;
    private double lastTargetX;
    private double lastTargetY;
    private Point nextPoint;

    public BotAI() {
        this(null);
    }

    public BotAI(Bot bot) {
        this.owner = bot;
        this.stateMap = new HashMap<>();
        this.currentState = null;
        this.previousState = null;
        this.aStar = new AStar();
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
            isTargetVisible = true;
            lastTargetX = hero.getX();
            lastTargetY = hero.getY();
            return followTarget(lastTargetX, lastTargetY);
        } else {
            if (isTargetVisible) {
                isTargetVisible = false;
            }
            return followTarget(lastTargetX, lastTargetY);
        }
    }

    /*protected void followHero() {
        if (isWayFree(World.getInstance().getHero())) {
            followTarget(new Point((float) World.getInstance().getHero().getX(), (float) World.getInstance().getHero().getY()));
            usePath = false;
        } else {
            if (pathTarget == null || MathAdv.getDistance(World.getInstance().getHero().getX(), World.getInstance().getHero().getY(),
                    pathTarget.getX(), pathTarget.getY()) > aStar.getGridStep()) {
                buildPath(World.getInstance().getHero(), owner);
            }
            Point nextPoint = aStar.getFirstReachablePoint(owner);
            if (MathAdv.getDistance(owner.getX(), owner.getY(), nextPoint.getX(), nextPoint.getY()) < 1) {
                aStar.removeFrom(nextPoint, true);
            } else {
                aStar.removeFrom(nextPoint, false);
            }
            if (usePath && nextPoint != null) {
                followTarget(nextPoint);
            } else {
                buildPath(World.getInstance().getHero(), owner);
            }
            previousPoint = nextPoint;
        }
    }*/

    protected boolean seeTarget(GameObjectSolid target) {
        int step = 5;
        Bullet dummy = new Bullet(owner, owner.getX(), owner.getY(), owner.getDirection(), 0, 0, 0, GameObjInstanceKind.ARROW);
        for (int i = 0; i < MathAdv.getDistance(owner.getX(), owner.getY(), target.getX(), target.getY()) / step; ++i) {
            GameObjectSolid collisionObject = CollisionManager.getInstance().collidesWith(dummy,
                    owner.getX() + MathAdv.lengthDirX(owner.getDirection(), step * i),
                    owner.getY() + MathAdv.lengthDirY(owner.getDirection(), step * i));
            if (collisionObject != owner && collisionObject != null && collisionObject != target) {
                return false;
            }
        }
        return true;
    }

    protected double getDistanceToTarget(Point target) {
        return MathAdv.getDistance(target.getX(), target.getY(), owner.getX(), owner.getY());
    }

    protected void buildPath(GameObjectSolid target, Unit owner) {
        aStar.buildPath(target, owner);
        //usePath = true;
        //pathTarget = aStar.getGoalPoint();
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