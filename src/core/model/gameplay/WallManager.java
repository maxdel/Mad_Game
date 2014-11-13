package core.model.gameplay;

public class WallManager extends GameObjectManager {
    Wall wall;

    @Override
    public void update(GameObject gameObject, int delta) {
        wall = (Wall) gameObject;

        /* some code like:  wall.method(...); */
    }
}
