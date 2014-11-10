package core.model.gameplay;

public class EnemyManager {

    private Enemy enemy;

    private int time;

    public EnemyManager(final Enemy enemy) {
        this.enemy = enemy;
        time = 0;
    }

    private void move(final double x, final double y, final int delta) {
        enemy.setDirection(Math.atan2(y - enemy.getY(), x - enemy.getX()));

        double length, lengthDirX, lengthDirY;
        length = enemy.getCurrentSpeed() * delta;

        lengthDirX = Math.cos(enemy.getDirection()) * length;
        lengthDirY = Math.sin(enemy.getDirection()) * length;

        enemy.setX(enemy.getX() + lengthDirX);
        enemy.setY(enemy.getY() + lengthDirY);
    }

    public void update(final double x, final double y, final int delta) {
        enemy.setCurrentSpeed(enemy.getMaximumSpeed());
        time += delta;
        //move(time / 1000 * Math.PI / 2, delta);
        move(x, y, delta);
    }

}