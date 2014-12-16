package core.model.gameplay.gameobjects;

/**
 * Represents hero
 * */
public class Hero extends Unit {

    private static Hero instance;

    private Hero(double x, double y, double direction) {
        super(x, y, direction, GameObjInstanceKind.HERO);
    }

    public static Hero getInstance(boolean reset) {
        if (instance == null || reset) {
            instance = new Hero(0, 0, 0);
        }
        return instance;
    }

    public static Hero getInstance() {
        return getInstance(false);
    }

}