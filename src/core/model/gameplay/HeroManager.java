package core.model.gameplay;

/**
 * Contains methods to define hero behavior
 * */
public class HeroManager {

    private Hero hero;

    public HeroManager(final Hero hero) {
        this.hero = hero;
    }

    public void walk(final double direction) {
        hero.setCurrentState("Walk");
        hero.setCurrentSpeed(hero.getMaximumSpeed() / 2);
        hero.setRelativeDirection(direction);
    }

    public void run(final double direction) {
        hero.setCurrentState("Run");
        hero.setCurrentSpeed(hero.getMaximumSpeed());
        hero.setRelativeDirection(direction);
    }

    public void stand() {
        hero.setCurrentState("Stand");
        hero.setCurrentSpeed(0);
    }

    public void rotate(final double angleOffset) {
        hero.setDirection(hero.getDirection() + angleOffset);
    }

    public void update(final int delta) {
        double length, lengthDirX, lengthDirY;
        length = hero.getCurrentSpeed() * delta;

        lengthDirX = Math.cos(hero.getDirection() + hero.getRelativeDirection()) * length;
        lengthDirY = Math.sin(hero.getDirection() + hero.getRelativeDirection()) * length;

        hero.setX(hero.getX() + lengthDirX);
        hero.setY(hero.getY() + lengthDirY);
    }

}