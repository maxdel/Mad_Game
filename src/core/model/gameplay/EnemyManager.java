package core.model.gameplay;

public class EnemyManager extends GameObjectMovingManager {

    public void followTarget(Enemy enemy, final double x, final double y, final int delta) {
        double direction = Math.atan2(y - enemy.getY(), x - enemy.getX());
        double speed = enemy.getMaximumSpeed();
        enemy.setDirection(direction);
        if (CollisionDetector.getInstance().isPlaceFree(enemy, enemy.getX() + lengthDirX(direction, speed * delta), enemy.getY() + lengthDirY(direction, speed * delta))) {
            run(enemy, speed);
        } else {
            stand(enemy);
        }
    }


    public void run(Enemy enemy, double speed) {
        enemy.setCurrentState(GameObjectState.RUN);
        enemy.setCurrentSpeed(speed);
    }

    public void stand(Enemy enemy) {
        enemy.setCurrentState(GameObjectState.STAND);
        enemy.setCurrentSpeed(0);
    }

}