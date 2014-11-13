package core.model;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

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
                    collidedAction(pivotObject, currentGameObject);
                    System.out.println("Collision: " + String.valueOf(pivotObject.getClass()) +
                    " vs " + String.valueOf(currentGameObject.getClass()));
                }
            }
        }

    }

    public boolean isPlaceFree(GameObject gameObject, double x, double y) {
        double originalX = gameObject.getX();
        double originalY = gameObject.getY();
        gameObject.setX(x);
        gameObject.setY(y);
        boolean isCollided = false;
        for (GameObject currentGameObject: World.getInstance(false).getGameObjects()) {
            if (gameObject != currentGameObject && isCollides(gameObject, currentGameObject)) {
                isCollided = true;
                break;
            }
        }
        gameObject.setX(originalX);
        gameObject.setY(originalY);
        return !isCollided;
    }


    public void collidedAction(GameObject pivot, GameObject current) {
        if (pivot instanceof Hero) {

            if (current instanceof Hero) {
                // pass
            } else if (current instanceof Enemy) {
                // pass
            } else if (current instanceof Wall) {
                // pass
            } else {
                // pass
            }
        } else if (pivot instanceof Wall) {
            //WallManager wallManager = (WallManager) World.getInstance(false).getGameObjManagers().get(Wall.class);

            if (current instanceof Hero) {
                pivot.collidedWith((Hero) current);
            } else if (current instanceof Enemy) {
                // pass
            } else {
                // pass
            }
        } else if (pivot instanceof Enemy) {
            if (current instanceof Hero) {
                // pass
            } else if (current instanceof Wall) {
                // pass
            } else {
                // pass
            }
        }


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
    }

}