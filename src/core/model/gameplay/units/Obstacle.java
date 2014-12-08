package core.model.gameplay.units;

public  class Obstacle extends GameObjectSolid {

    public Obstacle(double x, double y, double direction, GameObjectSolidType type) {
        super(x, y, direction, type);
    }

    @Override
    public void update(int delta) {

    }
}
