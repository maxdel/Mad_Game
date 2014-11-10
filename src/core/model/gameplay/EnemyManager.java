package core.model.gameplay;

public class EnemyManager {

    private Enemy enemy;

    public EnemyManager(final Enemy enemy) {
        this.enemy = enemy;
    }

    private void followTarget(final double x, final double y) {
        enemy.setDirection(Math.atan2(y - enemy.getY(), x - enemy.getX()));
        enemy.setCurrentSpeed(enemy.getMaximumSpeed());
    }

    public void update(final double x, final double y, final int delta) {
        followTarget(x, y);

        double length, lengthDirX, lengthDirY;
        length = enemy.getCurrentSpeed() * delta;

        lengthDirX = Math.cos(enemy.getDirection()) * length;
        lengthDirY = Math.sin(enemy.getDirection()) * length;

        enemy.setX(enemy.getX() + lengthDirX);
        enemy.setY(enemy.getY() + lengthDirY);
    }

}