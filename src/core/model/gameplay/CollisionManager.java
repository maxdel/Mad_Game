package core.model.gameplay;

import core.model.gameplay.units.Obstacle;
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
        for (Obstacle pivotObject: World.getInstance(false).getObstacles()) {
            for (Obstacle currentObstacle : World.getInstance(false).getObstacles()) {
                if (pivotObject != currentObstacle && isCollides(pivotObject, currentObstacle)) {
                    System.out.println("Collision: " + String.valueOf(pivotObject.getClass()) +
                    " vs " + String.valueOf(currentObstacle.getClass()));
                }
            }
        }
    }

    /*
     * Returns true if @param gameObject has no collisions with objects of @param otherClass Class
     */
    public boolean isCollidesWith(Obstacle obstacle, Class otherClass, double x, double y) {
        double originalX = obstacle.getX();
        double originalY = obstacle.getY();
        obstacle.setX(x);
        obstacle.setY(y);
        boolean isCollidesWith = false;
        for (Obstacle currentObstacle : World.getInstance(false).getObstacles()) {
            if (obstacle != currentObstacle && otherClass == currentObstacle.getClass()
                    && isCollides(obstacle, currentObstacle)) {
                isCollidesWith = true;
                break;
            }
        }
        obstacle.setX(originalX);
        obstacle.setY(originalY);
        return isCollidesWith;
    }

    /*
     */

    /**
     * Returns true if @param gameObject has no collisions in point x, y with gameObjects array
     *
     * @param  obstacle object to check collisions for
     */
    public boolean isPlaceFree(Obstacle obstacle, double x, double y) {
        double originalX = obstacle.getX();
        double originalY = obstacle.getY();
        obstacle.setX(x);
        obstacle.setY(y);
        boolean isCollides = false;
        for (Obstacle currentObstacle : World.getInstance(false).getObstacles()) {
            if (obstacle != currentObstacle && isCollides(obstacle, currentObstacle)) {
                isCollides = true;
                break;
            }
        }
        obstacle.setX(originalX);
        obstacle.setY(originalY);
        return !isCollides;
    }

    public boolean isPlaceFreeAdv(Obstacle obstacle, double x, double y) {
        double originalX = obstacle.getX();
        double originalY = obstacle.getY();
        obstacle.setX(x);
        obstacle.setY(y);
        boolean isCollides = false;
        Vector2f v = new Vector2f();
        for (Obstacle currentObstacle : World.getInstance(false).getObstacles()) {
            v.set((float)(currentObstacle.getX() - x), (float)(currentObstacle.getY() - y));
            // TODO fix magic number
            if (obstacle != currentObstacle && v.length() < 70 && isCollides(obstacle, currentObstacle)) {
                isCollides = true;
                break;
            }
        }
        obstacle.setX(originalX);
        obstacle.setY(originalY);
        return !isCollides;
    }

    /*
     * Returns the first GameObject object which collides with @param gameObject
     */
    public Obstacle collidesWith(Obstacle obstacle, double x, double y) {
        double originalX = obstacle.getX();
        double originalY = obstacle.getY();
        obstacle.setX(x);
        obstacle.setY(y);
        Obstacle other = null;
        Vector2f v = new Vector2f();
        for (Obstacle currentObstacle : World.getInstance(false).getObstacles()) {
            v.set((float)(currentObstacle.getX() - x), (float)(currentObstacle.getY() - y));
            // TODO fix magic number
            if (v.length() < 70 &&
                    obstacle != currentObstacle && isCollides(obstacle, currentObstacle)) {
                other = currentObstacle;
                break;
            }
        }
        obstacle.setX(originalX);
        obstacle.setY(originalY);
        return other;
    }

    /*
    * Returns clone of gameObject.getMask() with changed x, y and transformed (rotated on @param direction)
    *
    * Should be private(!) but used by View for debug purposes (drawMask)
    * */
    public Shape getUpdatedMask(Obstacle obstacle, double dx, double dy, double direction) {
        Shape mask = obstacle.getMask();
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

    public boolean isCollides(Obstacle obstacle1, Obstacle obstacle2) {
        Shape me = getUpdatedMask(obstacle1, obstacle1.getX(), obstacle1.getY(), obstacle1.getDirection());
        Shape him = getUpdatedMask(obstacle2, obstacle2.getX(), obstacle2.getY(), obstacle2.getDirection());

        return me.intersects(him);
        /*Vector2f v = new Vector2f((float)gameObject2.getX() - (float)gameObject1.getX(),
                (float)gameObject2.getY() - (float)gameObject1.getY());
        return v.length() < gameObject1.getMask().getBoundingCircleRadius() + gameObject2.getMask().getBoundingCircleRadius();*/
    }

}