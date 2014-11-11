package core.model.gameplay;

/**
 * Contains methods to define hero behavior
 * */
public class HeroManager extends MovingGameObjectManager {

    private Hero hero;

    public HeroManager(final MovingGameObject hero) {
        super(hero);
        this.hero = (Hero) hero;
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

}