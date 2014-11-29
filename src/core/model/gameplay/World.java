package core.model.gameplay;

import core.model.gameplay.inventory.ItemDB;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Main model class, that imitates game world.
 * Contains all game objects.
 */
public class World {

    private static World instance;

    private List<GameObject> gameObjects;
    private List<GameObject> toDeleteList;
    private List<GameObject> toAddList;
    private Hero hero;
    private List<Loot> lootList;
    private CollisionManager collisionManager;

    private TiledMap tiledMap;

    private World() {
        gameObjects = new ArrayList<GameObject>();
        toDeleteList = new ArrayList<GameObject>();
        toAddList = new ArrayList<GameObject>();
        lootList = new ArrayList<Loot>();
        collisionManager = CollisionManager.getInstance();

        lootList.add(new Loot(50, 50, 0, ItemDB.getInstance().getItem("Sword"), 1));
        lootList.add(new Loot(50, 150, 0, ItemDB.getInstance().getItem("Arrow"), 1));
        lootList.add(new Loot(450, 50, 0, ItemDB.getInstance().getItem("Apple"), 2));
        lootList.add(new Loot(50, -50, 0, ItemDB.getInstance().getItem("Apple"), 5));

        try {
            tiledMap = new TiledMap("/res/map.tmx");
        } catch (SlickException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < tiledMap.getWidth(); ++i) {
            for (int j = 0; j < tiledMap.getHeight(); ++j) {
                // Obstacles
                String tileObstacleName = tiledMap.getTileProperty(tiledMap.getTileId(i, j, 1), "name", "error");
                if (tileObstacleName.equals("stonewall")) {
                    gameObjects.add(new Wall(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0));
                } else if (tileObstacleName.equals("tree")) {
                    gameObjects.add(new Tree(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0));
                }
                // Objects
                String tileObjectName = tiledMap.getTileProperty(tiledMap.getTileId(i, j, 2), "name", "error");
                if (tileObjectName.equals("hero")) {
                    hero = new Hero(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0.2F);
                    gameObjects.add(hero);
                    hero.getAttribute().setCurrentHP(5);
                    hero.getAttribute().setCurrentMP(7);
                } else if (tileObjectName.equals("banditsword")) {
                    gameObjects.add(new Enemy(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0.1F));
                } else if (tileObjectName.equals("banditarcher")) {

                } else if (tileObjectName.equals("skeletsword")) {

                } else if (tileObjectName.equals("skeletmage")) {

                } else if (tileObjectName.equals("vampire")) {

                }
            }
        }
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
        for (GameObject gameObject : toAddList) {
            gameObjects.add(gameObject);
        }
        toAddList.clear();
        for (GameObject gameObject : gameObjects) {
            gameObject.update(delta);
        }
        for (GameObject gameObject : toDeleteList) {
            gameObjects.remove(gameObject);
        }
        toDeleteList.clear();
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public Hero getHero() {
        return hero;
    }

    public List<Loot> getLootList() {
        return lootList;
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    protected List<GameObject> getToDeleteList() {
        return toDeleteList;
    }

    protected List<GameObject> getToAddList() {
        return toAddList;
    }

}