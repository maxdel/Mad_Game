package core.model.gameplay;

public class EnemyManager extends GameObjectMovingManager {

    private Enemy enemy;

    public EnemyManager(final GameObjectMoving enemy) {
        super(enemy);
        this.enemy = (Enemy) enemy;
    }

    public void followTarget(final double x, final double y, final int delta) {
        double direction = Math.atan2(y - gameObjectMoving.getY(), x - gameObjectMoving.getX());
        double speed = gameObjectMoving.getMaximumSpeed();
        if (CollisionDetector.getInstance().isPlaceFree(enemy, enemy.getX() + lengthDirX(direction, speed * delta), enemy.getY() + lengthDirY(direction, speed * delta))) {
            enemy.setCurrentState(GameObjectState.RUN);
            enemy.setDirection(direction);
            enemy.setCurrentSpeed(speed);
        } else {
            stand();
        }
    }

    public void stand() {
        enemy.setCurrentState(GameObjectState.STAND);
        enemy.setCurrentSpeed(0);
    }

}