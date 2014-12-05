package core.model.gameplay;

import core.model.gameplay.items.ItemDB;
import core.model.gameplay.items.Loot;
import core.model.gameplay.units.*;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Main model class, that imitates game world.
 * Contains all game objects.
 */
public class World {

    private static World instance;

    private List<GameObjectSolid> gameObjectSolids;
    private List<GameObjectSolid> toDeleteList;
    private List<GameObjectSolid> toAddList;
    private Hero hero;
    private List<Loot> lootList;
    private CollisionManager collisionManager;

    private TiledMap tiledMap;

    private World() {
        gameObjectSolids = new ArrayList<GameObjectSolid>();
        toDeleteList = new ArrayList<GameObjectSolid>();
        toAddList = new ArrayList<GameObjectSolid>();
        lootList = new ArrayList<Loot>();
        collisionManager = CollisionManager.getInstance();

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
                    gameObjectSolids.add(new Wall(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0));
                } else if (tileObstacleName.equals("tree")) {
                    gameObjectSolids.add(new Tree(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0));
                }
                // Objects
                String tileObjectName = tiledMap.getTileProperty(tiledMap.getTileId(i, j, 2), "name", "error");
                if (tileObjectName.equals("hero")) {
                    hero = new Hero(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0.2F);
                    gameObjectSolids.add(hero);
                } else if (tileObjectName.equals("banditsword")) {
                    gameObjectSolids.add(new Bandit(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0.1F));
                } else if (tileObjectName.equals("banditarcher")) {
                    gameObjectSolids.add(new BanditArcher(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0.1F));
                } else if (tileObjectName.equals("skeletsword")) {
                    gameObjectSolids.add(new Skeleton(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0.15F));
                } else if (tileObjectName.equals("skeletmage")) {
                    gameObjectSolids.add(new SkeletonMage(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0.15F));
                } else if (tileObjectName.equals("vampire")) {

                }
            }
        }

        lootList.add(new Loot(hero.getX() + 40, hero.getY() - 70, Math.PI / 4, ItemDB.getInstance().getItem("Sword"), 1));
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
        for (GameObjectSolid gameObjectSolid : toAddList) {
            gameObjectSolids.add(gameObjectSolid);
        }
        toAddList.clear();
        for (GameObjectSolid gameObjectSolid : gameObjectSolids) {
            Vector2f v = new Vector2f((float)(hero.getX() - gameObjectSolid.getX()), (float)(hero.getY() - gameObjectSolid.getY()));
            if (v.length() < 1000) {
                gameObjectSolid.update(delta);
            }
        }
        for (GameObjectSolid gameObjectSolid : toDeleteList) {
            gameObjectSolids.remove(gameObjectSolid);
        }
        toDeleteList.clear();
    }

    public List<GameObjectSolid> getGameObjectSolids() {
        return gameObjectSolids;
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

    public List<GameObjectSolid> getToDeleteList() {
        return toDeleteList;
    }

    public List<GameObjectSolid> getToAddList() {
        return toAddList;
    }

}