package core.model.gameplay;


public class CollisionDetector {

    private static CollisionDetector instance;

    private CollisionDetector() {

    }

    public static CollisionDetector getInstance() {
        if (instance == null) {
            instance = new CollisionDetector();
        }
        return instance;
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

    public boolean isPlaceFree(GameObject gameObject, double x, double y) {
        double originalX = gameObject.getX();
        double originalY = gameObject.getY();
        gameObject.setX(x);
        gameObject.setY(y);
        boolean isCollided = false;
        for (GameObject currentGameObject: World.getInstance(false).getGameObjects()) {
            if (gameObject != currentGameObject && gameObject.collidesWith(currentGameObject)) {
                isCollided = true;
                break;
            }
        }
        gameObject.setX(originalX);
        gameObject.setY(originalY);
        return !isCollided;
    }

}