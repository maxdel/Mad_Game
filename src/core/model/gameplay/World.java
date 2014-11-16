package core.model.gameplay;

import core.model.gameplay.inventory.ItemDB;

import java.util.ArrayList;
import java.util.List;

/**
 * Main model class, that imitates game world.
 * Contains all game objects.
 */
public class World {

    private static World instance;

    private ArrayList<GameObject> gameObjects;
    private Hero hero;
    private List<Loot> lootList;
    private CollisionManager collisionManager;

    private World() {
        // creating gameObjects
        gameObjects = new ArrayList<GameObject>();
        hero = new Hero(200, 100, 0, 0.18F);
        lootList = new ArrayList<Loot>();
        collisionManager = CollisionManager.getInstance();

        gameObjects.add(hero);
        gameObjects.add(new Wall(100, 100, Math.PI / 4));
        gameObjects.add(new Wall(300, 300, 0));
        gameObjects.add(new Enemy(300, 200, 0, 0.06F));
        gameObjects.add(new Enemy(300, 100, 0, 0.03F));

        lootList.add(new Loot(50, 50, 0, ItemDB.getInstance().getItem("Sword"), 1));
        lootList.add(new Loot(50, 150, 0, ItemDB.getInstance().getItem("Silver arrow"), 1));
        lootList.add(new Loot(450, 50, 0, ItemDB.getInstance().getItem("Apple"), 2));
        lootList.add(new Loot(50, -50, 0, ItemDB.getInstance().getItem("Apple"), 5));
    }

    // Singleton pattern method
    public static World getInstance(boolean reset) {
        if (instance == null || reset) {
            instance = new World();
        }
        return instance;
    }
    public static World getInstance() {
        return getInstance(false);
    }

    public static void deleteInstance() {
        instance = null;
    }

    public void update(int delta) {
        for (GameObject gameObject: gameObjects) {
            try {
                gameObject.update(delta);
            }
            catch (NullPointerException npe) {
                continue; // passing, if object type useless
            }
        }

        collisionManager.update();
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public Hero getHero() {
        return hero;
    }

    public List<Loot> getLootList() {
        return lootList;
    }

}