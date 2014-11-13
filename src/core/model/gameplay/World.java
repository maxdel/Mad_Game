package core.model.gameplay;

import core.controller.gameplay.GameObjectMovingController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Main model class, that imitates game world.
 * Contains all game objects.
 */
public class World {

    private static World instance;

    private ArrayList<GameObject> gameObjects;
    private Hero hero;
    private CollisionDetector collisionDetector;
    private Map<Class<?>, GameObjectManager> gameObjManagers;
    private ArrayList<Class<?>> selfUpdatedTypes;



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

        // creating managers for all objects
        gameObjManagers = new HashMap<Class<?>, GameObjectManager>();
        gameObjManagers.put(Wall.class, new WallManager());
        gameObjManagers.put(Hero.class, new HeroManager());
        gameObjManagers.put(Enemy.class, new EnemyManager());

        //crating self updated types array-list
        selfUpdatedTypes = new ArrayList<Class<?>>();
        selfUpdatedTypes.add(Wall.class);


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
        for (GameObject gameObj: gameObjects) {
            try {
                for (Class<?> classType : selfUpdatedTypes)
                if (gameObj.getClass().equals(classType)){
                    gameObjManagers.get(gameObj.getClass().getSimpleName()).update(gameObj, delta);
                }
            }
            catch (NullPointerException npe) {
                continue; // passing, if object type useless
            }
        }

        collisionDetector.update();
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public Hero getHero() {
        return hero;
    }

    public Map<Class<?>, GameObjectManager> getGameObjManagers() {
        return gameObjManagers;
    }
}