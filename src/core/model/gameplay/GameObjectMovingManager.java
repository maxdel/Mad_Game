package core.model.gameplay;

public class GameObjectMovingManager {

    protected GameObjectMoving gameObjectMoving;

    public GameObjectMovingManager(GameObjectMoving gameObjectMoving) {
        this.gameObjectMoving = gameObjectMoving;
    }

    public void update(final int delta) {
        double length, lengthDirX, lengthDirY;
        length = gameObjectMoving.getCurrentSpeed() * delta;

        lengthDirX = Math.cos(gameObjectMoving.getDirection() + gameObjectMoving.getRelativeDirection()) * length;
        lengthDirY = Math.sin(gameObjectMoving.getDirection() + gameObjectMoving.getRelativeDirection()) * length;

        gameObjectMoving.setX(gameObjectMoving.getX() + lengthDirX);
        gameObjectMoving.setY(gameObjectMoving.getY() + lengthDirY);

    }

}

