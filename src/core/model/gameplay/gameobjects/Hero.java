package core.model.gameplay.gameobjects;

/**
 * Represents hero
 * */
public class Hero extends Unit {

    public Hero(double x, double y, double direction) {
        super(x, y, direction, GameObjectType.HERO);
    }

}