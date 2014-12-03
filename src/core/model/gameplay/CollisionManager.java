package core.model.gameplay;

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
        for (GameObject pivotObject: World.getInstance(false).getGameObjects()) {
            for (GameObject currentGameObject : World.getInstance(false).getGameObjects()) {
                if (pivotObject != currentGameObject && isCollides(pivotObject, currentGameObject)) {
                    System.out.println("Collision: " + String.valueOf(pivotObject.getClass()) +
                    " vs " + String.valueOf(currentGameObject.getClass()));
                }
            }
        }
    }

    /*
     * Returns true if @param gameObject has no collisions with objects of @param otherClass Class
     */
    public boolean isCollidesWith(GameObject gameObject, Class otherClass, double x, double y) {
        double originalX = gameObject.getX();
        double originalY = gameObject.getY();
        gameObject.setX(x);
        gameObject.setY(y);
        boolean isCollidesWith = false;
        for (GameObject currentGameObject: World.getInstance(false).getGameObjects()) {
            if (gameObject != currentGameObject && otherClass == currentGameObject.getClass()
                    && isCollides(gameObject, currentGameObject)) {
                isCollidesWith = true;
                break;
            }
        }
        gameObject.setX(originalX);
        gameObject.setY(originalY);
        return isCollidesWith;
    }

    /*
     */

    /**
     * Returns true if @param gameObject has no collisions in point x, y with gameObjects array
     *
     * @param  gameObject object to check collisions for
     */
    public boolean isPlaceFree(GameObject gameObject, double x, double y) {
        double originalX = gameObject.getX();
        double originalY = gameObject.getY();
        gameObject.setX(x);
        gameObject.setY(y);
        boolean isCollides = false;
        for (GameObject currentGameObject: World.getInstance(false).getGameObjects()) {
            if (gameObject != currentGameObject && isCollides(gameObject, currentGameObject)) {
                isCollides = true;
                break;
            }
        }
        gameObject.setX(originalX);
        gameObject.setY(originalY);
        return !isCollides;
    }

    public boolean isPlaceFreeAdv(GameObject gameObject, double x, double y) {
        double originalX = gameObject.getX();
        double originalY = gameObject.getY();
        gameObject.setX(x);
        gameObject.setY(y);
        boolean isCollides = false;
        Vector2f v = new Vector2f();
        for (GameObject currentGameObject: World.getInstance(false).getGameObjects()) {
            v.set((float)(currentGameObject.getX() - x), (float)(currentGameObject.getY() - y));
            // TODO fix magic number
            if (gameObject != currentGameObject && v.length() < 70 && isCollides(gameObject, currentGameObject)) {
                isCollides = true;
                break;
            }
        }
        gameObject.setX(originalX);
        gameObject.setY(originalY);
        return !isCollides;
    }

    /*
     * Returns the first GameObject object which collides with @param gameObject
     */
    public GameObject collidesWith(GameObject gameObject, double x, double y) {
        double originalX = gameObject.getX();
        double originalY = gameObject.getY();
        gameObject.setX(x);
        gameObject.setY(y);
        GameObject other = null;
        Vector2f v = new Vector2f();
        for (GameObject currentGameObject: World.getInstance(false).getGameObjects()) {
            v.set((float)(currentGameObject.getX() - x), (float)(currentGameObject.getY() - y));
            // TODO fix magic number
            if (v.length() < 70 &&
                    gameObject != currentGameObject && isCollides(gameObject, currentGameObject)) {
                other = currentGameObject;
                break;
            }
        }
        gameObject.setX(originalX);
        gameObject.setY(originalY);
        return other;
    }

    /*
    * Returns clone of gameObject.getMask() with changed x, y and transformed (rotated on @param direction)
    *
    * Should be private(!) but used by View for debug purposes (drawMask)
    * */
    public Shape getUpdatedMask(GameObject gameObject, double dx, double dy, double direction) {
        Shape mask = gameObject.getMask();
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

    public boolean isCollides(GameObject gameObject1, GameObject gameObject2) {
        Shape me = getUpdatedMask(gameObject1, gameObject1.getX(), gameObject1.getY(), gameObject1.getDirection());
        Shape him = getUpdatedMask(gameObject2, gameObject2.getX(), gameObject2.getY(), gameObject2.getDirection());

        return me.intersects(him);
        /*Vector2f v = new Vector2f((float)gameObject2.getX() - (float)gameObject1.getX(),
                (float)gameObject2.getY() - (float)gameObject1.getY());
        return v.length() < gameObject1.getMask().getBoundingCircleRadius() + gameObject2.getMask().getBoundingCircleRadius();*/
    }

}