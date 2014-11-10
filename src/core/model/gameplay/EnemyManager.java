package core.model.gameplay;

public class EnemyManager {

    private Enemy enemy;

    public EnemyManager(final Enemy enemy) {
        this.enemy = enemy;
    }

    public void followTarget(final double x, final double y) {
        enemy.setDirection(Math.atan2(y - enemy.getY(), x - enemy.getX()));
        enemy.setCurrentSpeed(enemy.getMaximumSpeed());
    }

    public void update(final int delta) {
        double length, lengthDirX, lengthDirY;
        length = enemy.getCurrentSpeed() * delta;

        lengthDirX = Math.cos(enemy.getDirection()) * length;
        lengthDirY = Math.sin(enemy.getDirection()) * length;

        enemy.setX(enemy.getX() + lengthDirX);
        enemy.setY(enemy.getY() + lengthDirY);
    }

}