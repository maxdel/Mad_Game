package core.model.gameplay;

import org.newdawn.slick.geom.*;

import core.MathAdv;
import core.model.gameplay.gameobjects.GameObject;
import core.model.gameplay.gameobjects.GameObjectSolid;

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
        for (GameObject pivotObject: World.getInstance().getGameObjectList()) {
            if (pivotObject instanceof GameObjectSolid) {
                GameObjectSolid pivotObjectSolid = (GameObjectSolid) pivotObject;
                for (GameObject currentGameObject : World.getInstance().getGameObjectList()) {
                    if (currentGameObject instanceof GameObjectSolid) {
                        GameObjectSolid gameObjectSolid = (GameObjectSolid) currentGameObject;
                        if (pivotObject != currentGameObject && isCollides(pivotObjectSolid,
                                pivotObject.getX(), pivotObject.getY(), gameObjectSolid)) {
                            System.out.println("Collision: " + String.valueOf(pivotObject.getType()) +
                                    " vs " + String.valueOf(currentGameObject.getType()));
                        }
                    }
                }
            }
        }
    }

    /*
     * Returns true if @param gameObject has no collisions with objects of @param otherClass Class
     */
    public boolean isCollidesWith(GameObjectSolid gameObjectSolid, Class otherClass, double x, double y) {
        boolean isCollidesWith = false;
        for (GameObject currentGameObject : World.getInstance().getGameObjectList()) {
            if (currentGameObject instanceof GameObjectSolid) {
                GameObjectSolid currentGameObjectSolid = (GameObjectSolid) currentGameObject;
                if (gameObjectSolid != currentGameObject && otherClass == currentGameObject.getClass()
                        && isCollides(gameObjectSolid, x, y, currentGameObjectSolid)) {
                    isCollidesWith = true;
                    break;
                }
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
        for (GameObject currentGameObject : World.getInstance().getGameObjectList()) {
            if (currentGameObject instanceof GameObjectSolid) {
                GameObjectSolid currentGameObjectSolid = (GameObjectSolid) currentGameObject;
                if (gameObjectSolid != currentGameObjectSolid && isCollides(gameObjectSolid, x, y, currentGameObjectSolid)) {
                    isCollides = true;
                    break;
                }
            }
        }
        return !isCollides;
    }

    public boolean isPlaceFreeAdv(GameObjectSolid gameObjectSolid, double x, double y) {
        boolean isCollides = false;
        for (GameObject currentGameObject : World.getInstance().getGameObjectList()) {
            if (currentGameObject instanceof GameObjectSolid) {
                GameObjectSolid currentGameObjectSolid = (GameObjectSolid) currentGameObject;
                double distanceToObject = MathAdv.getDistance(x, y, currentGameObjectSolid.getX(), currentGameObjectSolid.getY());
                double freeDistance = gameObjectSolid.getMask().getBoundingCircleRadius() +
                        currentGameObjectSolid.getMask().getBoundingCircleRadius();
                if (gameObjectSolid != currentGameObjectSolid && distanceToObject < freeDistance &&
                        isCollides(gameObjectSolid, x, y, currentGameObjectSolid)) {
                    isCollides = true;
                    break;
                }
            }
        }
        return !isCollides;
    }

    /*
     * Returns the first GameObject object which collides with @param gameObject
     */
    public GameObjectSolid collidesWith(GameObjectSolid gameObjectSolid, double x, double y) {
        GameObjectSolid other = null;
        for (GameObject currentGameObject : World.getInstance(false).getGameObjectList()) {
            if (currentGameObject instanceof GameObjectSolid) {
                GameObjectSolid currentGameObjectSolid = (GameObjectSolid) currentGameObject;
                double distanceToObject = MathAdv.getDistance(x, y, currentGameObjectSolid.getX(), currentGameObjectSolid.getY());
                double freeDistance = gameObjectSolid.getMask().getBoundingCircleRadius() +
                        currentGameObjectSolid.getMask().getBoundingCircleRadius();
                if (gameObjectSolid != currentGameObjectSolid && distanceToObject < freeDistance &&
                        isCollides(gameObjectSolid, x, y, currentGameObjectSolid)) {
                    other = currentGameObjectSolid;
                    break;
                }
            }
        }
        return other;
    }


    public GameObjectSolid collidesWithAStar(GameObjectSolid gameObjectSolid, double x, double y) {
        GameObjectSolid other = null;
        for (GameObject currentGameObject : World.getInstance(false).getGameObjectList()) {
            if (currentGameObject instanceof GameObjectSolid) {
                GameObjectSolid currentGameObjectSolid = (GameObjectSolid) currentGameObject;
                double distanceToObject = MathAdv.getDistance(x, y, currentGameObjectSolid.getX(), currentGameObjectSolid.getY());
                double freeDistance = gameObjectSolid.getMask().getBoundingCircleRadius() +
                        currentGameObjectSolid.getMask().getBoundingCircleRadius();
                if (gameObjectSolid != currentGameObjectSolid && distanceToObject < freeDistance &&
                        isCollidesAStar(gameObjectSolid, x, y, currentGameObjectSolid)) {
                    other = currentGameObjectSolid;
                    break;
                }
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

        /*if (mask1 instanceof Circle && mask2 instanceof Circle) {
            return MathAdv.getDistance(mask1.getX(), mask1.getY(), mask2.getX(), mask2.getY()) < mask1.getBoundingCircleRadius() + mask2.getBoundingCircleRadius();
        } else {
            return mask1.intersects(mask2);
        }*/
        return mask1.intersects(mask2);
    }

    public boolean isCollidesAStar(GameObjectSolid gameObjectSolid1, double x, double y, GameObjectSolid gameObjectSolid2) {
        Shape mask1 = getUpdatedMaskAStar(gameObjectSolid1.getMask(), x, y, gameObjectSolid1.getDirection());
        Shape mask2 = getUpdatedMask(gameObjectSolid2.getMask(), gameObjectSolid2.getX(), gameObjectSolid2.getY(), gameObjectSolid2.getDirection());

        /*if (mask1 instanceof Circle && mask2 instanceof Circle) {
            return MathAdv.getDistance(mask1.getX(), mask1.getY(), mask2.getX(), mask2.getY()) < mask1.getBoundingCircleRadius() + mask2.getBoundingCircleRadius();
        } else {
            return mask1.intersects(mask2);
        }*/
        return mask1.intersects(mask2);
    }
    public Shape getUpdatedMaskAStar(Shape mask, double dx, double dy, double direction) {
        Shape updatedMask;

        if (mask instanceof Circle) {
            updatedMask = new Circle(mask.getCenterX() + (float) dx,
                    mask.getCenterY() + (float) dy,
                    mask.getBoundingCircleRadius() + 10);
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

}