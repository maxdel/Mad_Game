package core.model.gameplay;

public class MovingGameObjectManager {

    protected MovingGameObject movingGameObject;

    public MovingGameObjectManager(MovingGameObject movingGameObject) {
        this.movingGameObject = movingGameObject;
    }

    public void update(final int delta) {
        double length, lengthDirX, lengthDirY;
        length = movingGameObject.getCurrentSpeed() * delta;


        lengthDirX = Math.cos(movingGameObject.getDirection() + movingGameObject.getRelativeDirection()) * length;
        lengthDirY = Math.sin(movingGameObject.getDirection() + movingGameObject.getRelativeDirection()) * length;

        movingGameObject.setX(movingGameObject.getX() + lengthDirX);
        movingGameObject.setY(movingGameObject.getY() + lengthDirY);

    }

}

