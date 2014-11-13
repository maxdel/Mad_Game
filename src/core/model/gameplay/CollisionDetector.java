package core.model.gameplay;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

public class CollisionDetector {

    private static CollisionDetector instance;

    private CollisionDetector() {

    }

    public static CollisionDetector getInstance() {
        if (instance == null) {
            instance = new CollisionDetector();
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