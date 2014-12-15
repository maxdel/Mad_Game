package core.model.gameplay;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.SlickException;

import core.MathAdv;
import core.model.gameplay.gameobjects.ai.MeleeAI;
import core.model.gameplay.gameobjects.ai.RangedAI;
import core.model.gameplay.gameobjects.ai.VampireAI;
import core.model.gameplay.items.ItemDB;
import core.model.gameplay.gameobjects.*;
import core.model.gameplay.items.ItemInstanceKind;
import core.resourcemanager.tilemapadv.TiledMapAdv;

/**
 * Main model class, that imitates game world.
 * Contains all game objects.
 */
public class World {

    private static World instance;

    private List<GameObject> gameObjectList;
    private List<GameObject> gameObjectToDeleteList;
    private List<GameObject> gameObjectToAddList;
    private Hero hero;
    private TiledMapAdv tiledMap;

    private final int UPDATE_RADIUS = 1000;

    private World() {
        gameObjectList = new ArrayList<>();
        gameObjectToDeleteList = new ArrayList<>();
        gameObjectToAddList = new ArrayList<>();

        try {
            tiledMap = new TiledMapAdv("/res/map.tmx");
        } catch (SlickException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < tiledMap.getObjectGroupCount(); ++i) {
            for (int j = 0; j < tiledMap.getObjectCount(i); ++j) {
                if (tiledMap.getObjectName(i, j).equals("wall")) {
                    double length;
                    length = Math.sqrt(Math.pow(tiledMap.getObjectWidth(i, j) / 2, 2) +
                            Math.pow(tiledMap.getObjectHeight(i, j) / 2, 2));
                    double rotation;
                    rotation = tiledMap.getObjectRotation(i, j) * Math.PI / 180;
                    gameObjectList.add(new Obstacle(
                            tiledMap.getObjectX(i, j) + MathAdv.lengthDirX(-Math.PI / 4 + rotation, length),
                            tiledMap.getObjectY(i, j) + MathAdv.lengthDirY(-Math.PI / 4 + rotation, length),
                                    rotation, GameObjInstanceKind.WALL)
                    );
                }
            }
        }

        for (int i = 0; i < tiledMap.getWidth(); ++i) {
            for (int j = 0; j < tiledMap.getHeight(); ++j) {
                // Obstacles
                String tileObstacleName = tiledMap.getTileProperty(tiledMap.getTileId(i, j, 1), "name", "error");
                if (tileObstacleName.equals("stonewall")) {
                    gameObjectList.add(new Obstacle(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0,
                            GameObjInstanceKind.WALL));
                } else if (tileObstacleName.equals("tree")) {
                    gameObjectList.add(new Obstacle(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0,
                            GameObjInstanceKind.TREE));
                }
                // Objects
                String tileObjectName = tiledMap.getTileProperty(tiledMap.getTileId(i, j, 2), "name", "error");
                if (tileObjectName.equals("hero")) {
                    hero = new Hero(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0);
                    gameObjectList.add(hero);
                } else if (tileObjectName.equals("vampire")) {
                    VampireAI vampireAI = new VampireAI();
                    Bot bot = new Bot(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0, GameObjInstanceKind.VAMPIRE,
                            vampireAI);
                    vampireAI.setOwner(bot);
                    gameObjectList.add(bot);
                } else if (tileObjectName.equals("banditsword")) {
                    MeleeAI meleeAI = new MeleeAI();
                    Bot bot = new Bot(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0, GameObjInstanceKind.BANDIT,
                            meleeAI);
                    meleeAI.setOwner(bot);
                    gameObjectList.add(bot);
                } else if (tileObjectName.equals("skeletsword")) {
                    MeleeAI meleeAI = new MeleeAI();
                    Bot bot = new Bot(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0, GameObjInstanceKind.SKELETON,
                            meleeAI);
                    meleeAI.setOwner(bot);
                    gameObjectList.add(bot);
                } else if (tileObjectName.equals("banditarcher")) {
                    RangedAI rangedAI = new RangedAI();
                    Bot bot = new Bot(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0, GameObjInstanceKind.BANDITARCHER,
                            rangedAI);
                    rangedAI.setOwner(bot);
                    gameObjectList.add(bot);
                } else if (tileObjectName.equals("skeletonmage")) {
                    RangedAI rangedAI = new RangedAI();
                    Bot bot = new Bot(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0, GameObjInstanceKind.SKELETONMAGE,
                            rangedAI);
                    rangedAI.setOwner(bot);
                    gameObjectList.add(bot);
                }
            }
        }

        gameObjectList.add(new Loot(hero.getX() + 40, hero.getY() - 70, Math.PI / 4,
                ItemDB.getInstance().getItem(ItemInstanceKind.SWORD), 1));
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

    public static boolean isInstanceIsNull() {
        return instance == null;
    }

    public void update(int delta) {
        for (GameObject gameObject : gameObjectToAddList) {
            gameObjectList.add(gameObject);
        }
        gameObjectToAddList.clear();
        for (GameObject gameObject : gameObjectToDeleteList) {
            gameObjectList.remove(gameObject);
        }
        gameObjectToDeleteList.clear();

        for (GameObject gameObject : gameObjectList) {
            if (MathAdv.getDistance(gameObject.getX(), gameObject.getY(), hero.getX(), hero.getY()) < UPDATE_RADIUS) {
                gameObject.update(delta);
            }
        }
    }

    public List<GameObject> getGameObjectList() {
        return gameObjectList;
    }

    public Hero getHero() {
        return hero;
    }

    public TiledMapAdv getTiledMap() {
        return tiledMap;
    }

    public List<GameObject> getGameObjectToDeleteList() {
        return gameObjectToDeleteList;
    }

    public List<GameObject> getGameObjectToAddList() {
        return gameObjectToAddList;
    }

}