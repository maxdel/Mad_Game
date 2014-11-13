package core.model.gameplay;

public class WallManager extends GameObjectManager {

    private Wall wall;

    @Override
    public void update(GameObject gameObject, int delta) {
        wall = (Wall) gameObject;

        /* some code like:  wall.method(...); */
    }

    /* test*/
    public void collidedWith(Wall me, Hero other) {
        wall = me;
        wall.setX(wall.getX() + 30);
        wall.setY(wall.getY() + 30);
    }

    public void collidedWith(Wall me, Enemy other) {
        //
    }
}
