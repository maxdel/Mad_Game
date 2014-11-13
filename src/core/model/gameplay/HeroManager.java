package core.model.gameplay;

import org.newdawn.slick.geom.Rectangle;

/**
 * Contains methods to define hero behavior
 * */
public class HeroManager extends GameObjectMovingManager {

    public void walk(Hero hero, final double direction) {
        hero.setCurrentState(GameObjectState.WALK);
        hero.setCurrentSpeed(hero.getMaximumSpeed() / 2);
        hero.setRelativeDirection(direction);
    }

    public void run(Hero hero, final double direction) {
        hero.setCurrentState(GameObjectState.RUN);
        hero.setCurrentSpeed(hero.getMaximumSpeed());
        hero.setRelativeDirection(direction);
    }

    public void stand(Hero hero) {
        hero.setCurrentState(GameObjectState.STAND);
        hero.setCurrentSpeed(0);
    }

    public void rotate(Hero hero, final double angleOffset) {
        hero.setDirection(hero.getDirection() + angleOffset);
    }

}