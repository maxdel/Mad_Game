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

    private ArrayList<GameObject> gameObjects;
    private Hero hero;
    private List<Loot> lootList;
    private CollisionManager collisionManager;

    private TiledMap tiledMap;

    private World() {
        // creating gameObjects
        gameObjects = new ArrayList<GameObject>();
        //hero = new Hero(200, 100, 0, 0.18F);
        lootList = new ArrayList<Loot>();
        collisionManager = CollisionManager.getInstance();

        /*gameObjects.add(hero);
        gameObjects.add(new Wall(100, 100, Math.PI / 4));
        gameObjects.add(new Wall(300, 300, 0));
        gameObjects.add(new Enemy(300, 200, 0, 0.06F));
        gameObjects.add(new Enemy(300, 100, 0, 0.03F));*/

        lootList.add(new Loot(50, 50, 0, ItemDB.getInstance().getItem("Sword"), 1));
        lootList.add(new Loot(50, 150, 0, ItemDB.getInstance().getItem("Silver arrow"), 1));
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
                    /*gameObjects.add(new Tree(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0));*/
                }
                // Objects
                String tileObjectName = tiledMap.getTileProperty(tiledMap.getTileId(i, j, 2), "name", "error");
                if (tileObjectName.equals("hero")) {
                    hero = new Hero(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0, 0.12F);
                    gameObjects.add(hero);
                } else if (tileObstacleName.equals("banditsword")) {

                } else if (tileObstacleName.equals("banditarcher")) {

                } else if (tileObstacleName.equals("skeletsword")) {

                } else if (tileObstacleName.equals("skeletmage")) {

                } else if (tileObstacleName.equals("vampire")) {

                }
            }
        }

/*        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                if (tiledMap.getTileId(i, j, 0) != 0) {
                    gameObjects.add(new Wall(i * 64 + 32, j * 64 + 32, 0));
                }
            }
        }*/
/*        for (int i = 0; i < tiledMap.getObjectCount(0); ++i) {
            gameObjects.add(new Wall(tiledMap.getObjectX(0, i), tiledMap.getObjectY(0, i), 0));
        }*/
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

        //collisionManager.update();
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

    public TiledMap getTiledMap() {
        return tiledMap;
    }

}