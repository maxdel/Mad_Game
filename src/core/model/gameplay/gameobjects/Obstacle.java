package core.model.gameplay.gameobjects;

public class Obstacle extends GameObjectSolid {

    public Obstacle(double x, double y, double direction, GameObjInstanceKind type) {
        super(x, y, direction, type);
    }

    @Override
    public void update(int delta) {

    }
}
