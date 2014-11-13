package core.model.gameplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main model class, that imitates game world.
 * Contains all game objects.
 */
public class World {

    private static World instance;

    private List<GameObject> gameObjects;
    private Hero hero;
    private CollisionDetector collisionDetector;
    private Map<String, GameObjectManager> stationaryObjManagers;

    private World() {

        // creating gameObjects
        gameObjects = new ArrayList<GameObject>();

        gameObjects.add(new Wall(100, 100, Math.PI / 4));
        gameObjects.add(new Wall(300, 300, 0));

        Enemy enemy1 = new Enemy(300, 200, 0, 0.06F);
        Enemy enemy2 = new Enemy(300, 100, 0, 0.03F);

        gameObjects.add(enemy1);
        gameObjects.add(enemy2);

        hero = new Hero(200, 100, 0, 0.18F);

        gameObjects.add(hero);
        //--

        // creating managers for stationary objects
        stationaryObjManagers = new HashMap<String, GameObjectManager>();
        stationaryObjManagers.put(Wall.class.getSimpleName(), new WallManager());

        // creating collision detector
        collisionDetector = CollisionDetector.getInstance();
    }

    // Singleton pattern method
    public static World getInstance(boolean reset) {
        if (instance == null || reset) {
            instance = new World();
        }
        return instance;
    }

    public static void deleteInstance() {
        instance = null;
    }

    public void update(int delta) {
        for (GameObject gameObject: gameObjects) {
            try {
                stationaryObjManagers.get(gameObject.getClass().getSimpleName()).update(gameObject, delta);
            }
            catch (NullPointerException npe) {
                continue; // passing, if object type useless
            }
        }

        collisionDetector.update();
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public Hero getHero() {
        return hero;
    }

}