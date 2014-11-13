package core.model.gameplay;

public abstract class GameObjectMovingManager extends GameObjectManager {

    GameObjectMoving gameObjectMoving;

    @Override
    public void update(GameObject gameObject, final int delta) {
        gameObjectMoving = (GameObjectMoving) gameObject;
        double length, lengthDirX, lengthDirY;
        length = gameObjectMoving.getCurrentSpeed() * delta;

        lengthDirX = lengthDirX(gameObjectMoving.getDirection() + gameObjectMoving.getRelativeDirection(), length);
        lengthDirY = lengthDirY(gameObjectMoving.getDirection() + gameObjectMoving.getRelativeDirection(), length);

        gameObjectMoving.setX(gameObjectMoving.getX() + lengthDirX);
        gameObjectMoving.setY(gameObjectMoving.getY() + lengthDirY);
    }

    protected double lengthDirX(double direction, double length) {
        return Math.cos(direction) * length;
    }

    protected double lengthDirY(double direction, double length) {
        return Math.sin(direction) * length;
    }

}