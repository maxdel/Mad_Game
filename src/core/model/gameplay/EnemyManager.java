package core.model.gameplay;

public class EnemyManager extends GameObjectMovingManager {

    private Enemy enemy;

    public EnemyManager(final GameObjectMoving enemy) {
        super(enemy);
        this.enemy = (Enemy) enemy;
    }

    public void followTarget(final double x, final double y) {
        enemy.setDirection(Math.atan2(y - gameObjectMoving.getY(), x - gameObjectMoving.getX()));
        enemy.setCurrentSpeed(gameObjectMoving.getMaximumSpeed());
    }

}