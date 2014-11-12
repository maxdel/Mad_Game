package core.model.gameplay;


public class CollisionDetector {

    public CollisionDetector() {
    }

/*
    * Checks for collisions between all game objects (may be only in camera range, later !)
    * and change model objects fields calling methods collited with methods from managers.
    *
    * Must be optimized.
    * */
    public void update() {
        // for hero
        for (GameObject gameObject: World.getInstance(false).getGameObjects()) {
            if (World.getInstance(false).getHero().collidesWith(gameObject)) {
                World.getInstance(false).getHero().collidedWith(gameObject);
                gameObject.collidedWith(World.getInstance(false).getHero());
            }
        }

        //for others
        for (GameObject pivotObj: World.getInstance(false).getGameObjects()) {
            for (GameObject currentObj: World.getInstance(false).getGameObjects()) {
                if (pivotObj != currentObj && pivotObj.collidesWith(currentObj)) {
                    pivotObj.collidedWith(currentObj);
                    currentObj.collidedWith(pivotObj);
                }
            }
        }

    }

}
