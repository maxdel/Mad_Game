package core.model.gameplay;

public class GameObjectMovingManager {

    protected GameObjectMoving gameObjectMoving;

    public GameObjectMovingManager(GameObjectMoving gameObjectMoving) {
        this.gameObjectMoving = gameObjectMoving;
    }

    public void update(final int delta) {
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