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
        enemy.setDirection(direction);
        if (CollisionDetector.getInstance().isPlaceFree(enemy, enemy.getX() + lengthDirX(direction, speed * delta), enemy.getY() + lengthDirY(direction, speed * delta))) {
            run(speed);
        } else {
            stand();
        }
    }

    private void run(double speed) {
        enemy.setCurrentState(GameObjectState.RUN);
        enemy.setCurrentSpeed(speed);
    }

    public void stand() {
        enemy.setCurrentState(GameObjectState.STAND);
        enemy.setCurrentSpeed(0);
    }

}