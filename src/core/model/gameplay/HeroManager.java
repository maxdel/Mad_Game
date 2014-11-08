package core.model.gameplay;

/**
 * Contains methods to define hero behavior
 * */
public class HeroManager {

    private Hero hero;

    public HeroManager(final Hero hero) {
        this.hero = hero;
    }

    /**
     * Relatively moves hero in specified direction
     * @param direction relative direction to move in radians
     * @param delta time in ms from the previous update
     */
    public void move(final double direction, final int delta) {
        double length, lengthDirX, lengthDirY;
        length = hero.getSpeed() * delta;

        lengthDirX = Math.cos(hero.getDirection() + direction) * length;
        lengthDirY = Math.sin(hero.getDirection() + direction) * length;

        hero.setX(hero.getX() + lengthDirX);
        hero.setY(hero.getY() + lengthDirY);
    }

    public void rotate(final double angleOffset) {
        hero.setDirection(hero.getDirection() + angleOffset);
    }

}