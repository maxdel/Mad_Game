package core.model.gameplay;

import org.newdawn.slick.geom.Vector2f;

public class Skill {

    private GameObjectMoving owner;

    private String name;
    private int castTime;
    private int currentCastTime;
    private int cooldownTime;
    private int currentCooldownTime;

    private String type;
    private double delta;
    private String requiredItem;
    private double requiredMP;

    private double radius;
    private double angle;

    protected Skill(GameObjectMoving owner, String name, int castTime, int cooldownTime, String type, double delta, String requiredItem,
                    double requiredMP, double radius, double angle) {
        this.owner = owner;
        this.name = name;
        this.castTime = castTime;
        this.currentCastTime = 0;
        this.cooldownTime = cooldownTime;
        this.currentCooldownTime = 0;
        this.type = type;
        this.delta = delta;
        this.requiredItem = requiredItem;
        this.requiredMP = requiredMP;
        this.radius = radius;
        this.angle = angle;
    }

    protected void update(int delta) {
        if (currentCooldownTime > 0) {
            currentCooldownTime -= delta;
            if (currentCooldownTime < 0) {
                currentCooldownTime = 0;
            }
        }
    }

    protected boolean startCast() {
        if (owner.getInventory().isItemDressed(requiredItem) &&
                owner.getAttribute().getCurrentMP() >= requiredMP
                && currentCooldownTime == 0) {
            currentCastTime = castTime;
            owner.getAttribute().setCurrentMP(owner.getAttribute().getCurrentMP() - requiredMP);
            currentCooldownTime = cooldownTime;
            return true;
        }
        return false;
    }

    protected void cast() {
        if (type.equals("attack")) {
            for (GameObject gameObject : World.getInstance().getGameObjects()) {
                if (gameObject instanceof GameObjectMoving && gameObject != owner) {
                    GameObjectMoving target = (GameObjectMoving) gameObject;

                    Vector2f v1 = new Vector2f((float)lengthDirX(owner.getDirection(), angle),
                            (float)lengthDirY(owner.getDirection(), angle));
                    Vector2f v2 = new Vector2f((float)(target.getX() - owner.getX()), (float)(target.getY() - owner.getY()));
                    double angleBetweenObjects = Math.acos(v1.dot(v2) / (v1.length() * v2.length()));

                    if (v2.length() - target.getMask().getBoundingCircleRadius() <= radius &&
                            Math.abs(angleBetweenObjects) < angle / 2) {
                        target.getAttribute().setCurrentHP(target.getAttribute().getCurrentHP() - delta);
                    }
                }
            }
        }
    }

    protected double lengthDirX(double direction, double length) {
        return Math.cos(direction) * length;
    }

    protected double lengthDirY(double direction, double length) {
        return Math.sin(direction) * length;
    }

    public int getCurrentCastTime() {
        return currentCastTime;
    }

    public void setCurrentCastTime(int currentCastTime) {
        this.currentCastTime = currentCastTime;
    }

    public double getRadius() {
        return radius;
    }
}