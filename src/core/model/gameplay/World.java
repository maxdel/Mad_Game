package core.model.gameplay;

import java.util.ArrayList;

/**
 * Main model class, that imitates game world.
 * Contains all game objects.
 */
public class World {

    private static World instance;

    private ArrayList<GameObject> gameObjects;
    private Hero hero;
    private HeroManager heroManager;
    private ArrayList<GameObjectMovingManager> gameObjectMovingManagersManagers;
    private CollisionDetector collisionDetector;

    private World() {
        gameObjects = new ArrayList<GameObject>();

        gameObjects.add(new Wall(100, 100, Math.PI / 4));
        gameObjects.add(new Wall(300, 300, 0));

        Enemy enemy1 = new Enemy(300, 200, 0, 0.06F);
        Enemy enemy2 = new Enemy(300, 100, 0, 0.03F);

        gameObjects.add(enemy1);
        gameObjects.add(enemy2);

        hero = new Hero(200, 100, 0, 0.18F);

        gameObjects.add(hero);

        gameObjectMovingManagersManagers = new ArrayList<GameObjectMovingManager>();
        gameObjectMovingManagersManagers.add(new EnemyManager(enemy1));
        gameObjectMovingManagersManagers.add(new EnemyManager(enemy2));
        heroManager = new HeroManager(hero);
        gameObjectMovingManagersManagers.add(heroManager);


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

    public void update() {
        collisionDetector.update();
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public HeroManager getHeroManager() {
        return heroManager;
    }

    public Hero getHero() {
        return hero;
    }

    public ArrayList<GameObjectMovingManager> getGameObjectMovingManagersManagers() {
        return gameObjectMovingManagersManagers;
    }

}