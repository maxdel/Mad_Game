package core.model.gameplay;

import core.model.gameplay.gameobjects.GameObjectSolid;

import org.newdawn.slick.geom.*;

public class CollisionManager {

    private static CollisionManager instance;

    private CollisionManager() {

    }

    public static CollisionManager getInstance() {
        if (instance == null) {
            instance = new CollisionManager();
        }
        return instance;
    }

    /**
     * For debug purposes
     */
    public void update() {
        for (GameObjectSolid pivotObject: World.getInstance(false).getGameObjectSolids()) {
            for (GameObjectSolid currentGameObjectSolid : World.getInstance(false).getGameObjectSolids()) {
                if (pivotObject != currentGameObjectSolid && isCollides(pivotObject,
                        pivotObject.getX(), pivotObject.getY(), currentGameObjectSolid)) {
                    System.out.println("Collision: " + String.valueOf(pivotObject.getType()) +
                    " vs " + String.valueOf(currentGameObjectSolid.getType()));
                }
            }
        }
    }

    /*
     * Returns true if @param gameObject has no collisions with objects of @param otherClass Class
     */
    public boolean isCollidesWith(GameObjectSolid gameObjectSolid, Class otherClass, double x, double y) {
        boolean isCollidesWith = false;
        for (GameObjectSolid currentGameObjectSolid : World.getInstance(false).getGameObjectSolids()) {
            if (gameObjectSolid != currentGameObjectSolid && otherClass == currentGameObjectSolid.getClass()
                    && isCollides(gameObjectSolid, x, y, currentGameObjectSolid)) {
                isCollidesWith = true;
                break;
            }
        }
        return isCollidesWith;
    }

    /**
     * Returns true if @param gameObject has no collisions in point x, y with gameObjects array
     * @param  gameObjectSolid object to check collisions for
     */
    public boolean isPlaceFree(GameObjectSolid gameObjectSolid, double x, double y) {
        boolean isCollides = false;
        for (GameObjectSolid currentGameObjectSolid : World.getInstance(false).getGameObjectSolids()) {
            if (gameObjectSolid != currentGameObjectSolid && isCollides(gameObjectSolid, x, y, currentGameObjectSolid)) {
                isCollides = true;
                break;
            }
        }
        return !isCollides;
    }

    public boolean isPlaceFreeAdv(GameObjectSolid gameObjectSolid, double x, double y) {
        boolean isCollides = false;
        Vector2f v = new Vector2f();
        for (GameObjectSolid currentGameObjectSolid : World.getInstance(false).getGameObjectSolids()) {
            v.set((float)(currentGameObjectSolid.getX() - x), (float)(currentGameObjectSolid.getY() - y));
            if (gameObjectSolid != currentGameObjectSolid && v.length() < gameObjectSolid.getMask().getBoundingCircleRadius() +
                    currentGameObjectSolid.getMask().getBoundingCircleRadius() &&
                    isCollides(gameObjectSolid, x, y, currentGameObjectSolid)) {
                isCollides = true;
                break;
            }
        }
        return !isCollides;
    }

    /*
     * Returns the first GameObject object which collides with @param gameObject
     */
    public GameObjectSolid collidesWith(GameObjectSolid gameObjectSolid, double x, double y) {
        GameObjectSolid other = null;
        Vector2f v = new Vector2f();
        for (GameObjectSolid currentGameObjectSolid : World.getInstance(false).getGameObjectSolids()) {
            v.set((float)(currentGameObjectSolid.getX() - x), (float)(currentGameObjectSolid.getY() - y));
            if (v.length() < gameObjectSolid.getMask().getBoundingCircleRadius() +
                    currentGameObjectSolid.getMask().getBoundingCircleRadius() &&
                    gameObjectSolid != currentGameObjectSolid && isCollides(gameObjectSolid, x, y, currentGameObjectSolid)) {
                other = currentGameObjectSolid;
                break;
            }
        }
        return other;
    }

    /*
    * Returns clone of gameObject.getMask() with changed x, y and transformed (rotated on @param direction)
    *
    * Should be private(!) but used by View for debug purposes (drawMask)
    * */
    public Shape getUpdatedMask(Shape mask, double dx, double dy, double direction) {
        Shape updatedMask;

        if (mask instanceof Circle) {
            updatedMask = new Circle(mask.getCenterX() + (float) dx,
                    mask.getCenterY() + (float) dy,
                    mask.getBoundingCircleRadius());
        } else if (mask instanceof Rectangle) {
            updatedMask = new Rectangle(mask.getX() + (float) dx,
                    mask.getY() + (float) dy,
                    mask.getWidth(), mask.getHeight());
            updatedMask = updatedMask.transform(Transform.createRotateTransform((float) (direction),
                    updatedMask.getCenterX(), updatedMask.getCenterY()));
        } else {
            updatedMask = null;
        }

        return updatedMask;
    }

    public boolean isCollides(GameObjectSolid gameObjectSolid1, double x, double y, GameObjectSolid gameObjectSolid2) {
        Shape mask1 = getUpdatedMask(gameObjectSolid1.getMask(), x, y, gameObjectSolid1.getDirection());
        Shape mask2 = getUpdatedMask(gameObjectSolid2.getMask(), gameObjectSolid2.getX(), gameObjectSolid2.getY(), gameObjectSolid2.getDirection());

        if (mask1 instanceof Circle && mask2 instanceof Circle) {
            Vector2f v = new Vector2f(mask2.getX() - mask1.getX(), mask2.getY() - mask1.getY());
            return v.length() < mask1.getBoundingCircleRadius() + mask2.getBoundingCircleRadius();
        } else {
            return mask1.intersects(mask2);
        }
    }

}