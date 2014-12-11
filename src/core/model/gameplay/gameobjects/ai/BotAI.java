package core.model.gameplay.gameobjects.ai;

import core.MathAdv;
import core.model.gameplay.CollisionManager;
import core.model.gameplay.World;
import core.model.gameplay.gameobjects.Bot;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Vector2f;

import java.util.HashMap;
import java.util.Map;

public abstract class BotAI {

    protected Bot owner;
    protected Map<BotAIState, AIState> stateMap;
    protected BotAIState currentState;
    private BotAIState previousState;

    public BotAI() {
        this(null);
    }

    public BotAI(Bot bot) {
        this.owner = bot;
        stateMap = new HashMap<BotAIState, AIState>();
        init();
    }

    public interface BotAIState {

    }

    public void run(int delta) {
        if (currentState != previousState) {
            stateMap.get(currentState).enter();
            previousState = currentState;
        }
        stateMap.get(currentState).run();
        stateMap.get(currentState).update(delta);
    }

    protected abstract void init();

    // AI methods

    protected double getDistanceToHero() {
        return (new Vector2f((float) World.getInstance().getHero().getX() - (float)owner.getX(),
                (float)World.getInstance().getHero().getY() - (float)owner.getY())).length();
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

    protected void followTarget(Point target) {
        //if (owner.getCurrentState() != GameObjectState.SKILL) {
        double direction = Math.atan2(target.getY() - owner.getY(), target.getX() - owner.getX());
        owner.setDirection(direction); // TODO unwanted direct field changing
        owner.move();
        //}
    }

    protected void followHero() {
        followTarget(new Point((float)World.getInstance().getHero().getX(),
                (float)World.getInstance().getHero().getY()));
    }

    protected double getDistanceToTarget(Point target) {
        return (new Vector2f(target.getX() - (float)owner.getX(),
                target.getY() - (float)owner.getY())).length();
    }

    // Getters and setters

    public BotAIState getCurrentState() {
        return currentState;
    }

    public void setOwner(Bot owner) {
        this.owner = owner;
    }

}