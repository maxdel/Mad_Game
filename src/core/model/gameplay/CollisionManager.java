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
     * For debug
     */
    public void update() {
        for (GameObjectSolid pivotObject: World.getInstance(false).getGameObjectSolids()) {
            for (GameObjectSolid currentGameObjectSolid : World.getInstance(false).getGameObjectSolids()) {
                if (pivotObject != currentGameObjectSolid && isCollides(pivotObject, currentGameObjectSolid)) {
                    System.out.println("Collision: " + String.valueOf(pivotObject.getClass()) +
                    " vs " + String.valueOf(currentGameObjectSolid.getClass()));
                }
            }
        }
    }

    /*
     * Returns true if @param gameObject has no collisions with objects of @param otherClass Class
     */
    public boolean isCollidesWith(GameObjectSolid gameObjectSolid, Class otherClass, double x, double y) {
        double originalX = gameObjectSolid.getX();
        double originalY = gameObjectSolid.getY();
        gameObjectSolid.setX(x);
        gameObjectSolid.setY(y);
        boolean isCollidesWith = false;
        for (GameObjectSolid currentGameObjectSolid : World.getInstance(false).getGameObjectSolids()) {
            if (gameObjectSolid != currentGameObjectSolid && otherClass == currentGameObjectSolid.getClass()
                    && isCollides(gameObjectSolid, currentGameObjectSolid)) {
                isCollidesWith = true;
                break;
            }
        }
        gameObjectSolid.setX(originalX);
        gameObjectSolid.setY(originalY);
        return isCollidesWith;
    }

    /**
     * Returns true if @param gameObject has no collisions in point x, y with gameObjects array
     *
     * @param  gameObjectSolid object to check collisions for
     */
    public boolean isPlaceFree(GameObjectSolid gameObjectSolid, double x, double y) {
        double originalX = gameObjectSolid.getX();
        double originalY = gameObjectSolid.getY();
        gameObjectSolid.setX(x);
        gameObjectSolid.setY(y);
        boolean isCollides = false;
        for (GameObjectSolid currentGameObjectSolid : World.getInstance(false).getGameObjectSolids()) {
            if (gameObjectSolid != currentGameObjectSolid && isCollides(gameObjectSolid, currentGameObjectSolid)) {
                isCollides = true;
                break;
            }
        }
        gameObjectSolid.setX(originalX);
        gameObjectSolid.setY(originalY);
        return !isCollides;
    }

    public boolean isPlaceFreeAdv(GameObjectSolid gameObjectSolid, double x, double y) {
        double originalX = gameObjectSolid.getX();
        double originalY = gameObjectSolid.getY();
        gameObjectSolid.setX(x);
        gameObjectSolid.setY(y);
        boolean isCollides = false;
        Vector2f v = new Vector2f();
        for (GameObjectSolid currentGameObjectSolid : World.getInstance(false).getGameObjectSolids()) {
            v.set((float)(currentGameObjectSolid.getX() - x), (float)(currentGameObjectSolid.getY() - y));
            // TODO fix magic number
            if (gameObjectSolid != currentGameObjectSolid && v.length() < 70 && isCollides(gameObjectSolid, currentGameObjectSolid)) {
                isCollides = true;
                break;
            }
        }
        gameObjectSolid.setX(originalX);
        gameObjectSolid.setY(originalY);
        return !isCollides;
    }

    /*
     * Returns the first GameObject object which collides with @param gameObject
     */
    public GameObjectSolid collidesWith(GameObjectSolid gameObjectSolid, double x, double y) {
        double originalX = gameObjectSolid.getX();
        double originalY = gameObjectSolid.getY();
        gameObjectSolid.setX(x);
        gameObjectSolid.setY(y);
        GameObjectSolid other = null;
        Vector2f v = new Vector2f();
        for (GameObjectSolid currentGameObjectSolid : World.getInstance(false).getGameObjectSolids()) {
            v.set((float)(currentGameObjectSolid.getX() - x), (float)(currentGameObjectSolid.getY() - y));
            // TODO fix magic number
            if (v.length() < 70 &&
                    gameObjectSolid != currentGameObjectSolid && isCollides(gameObjectSolid, currentGameObjectSolid)) {
                other = currentGameObjectSolid;
                break;
            }
        }
        gameObjectSolid.setX(originalX);
        gameObjectSolid.setY(originalY);
        return other;
    }

    /*
    * Returns clone of gameObject.getMask() with changed x, y and transformed (rotated on @param direction)
    *
    * Should be private(!) but used by View for debug purposes (drawMask)
    * */
    public Shape getUpdatedMask(GameObjectSolid gameObjectSolid, double dx, double dy, double direction) {
        Shape mask = gameObjectSolid.getMask();
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

    public boolean isCollides(GameObjectSolid gameObjectSolid1, GameObjectSolid gameObjectSolid2) {
        Shape me = getUpdatedMask(gameObjectSolid1, gameObjectSolid1.getX(), gameObjectSolid1.getY(), gameObjectSolid1.getDirection());
        Shape him = getUpdatedMask(gameObjectSolid2, gameObjectSolid2.getX(), gameObjectSolid2.getY(), gameObjectSolid2.getDirection());

        return me.intersects(him);
        /*Vector2f v = new Vector2f((float)gameObject2.getX() - (float)gameObject1.getX(),
                (float)gameObject2.getY() - (float)gameObject1.getY());
        return v.length() < gameObject1.getMask().getBoundingCircleRadius() + gameObject2.getMask().getBoundingCircleRadius();*/
    }

}