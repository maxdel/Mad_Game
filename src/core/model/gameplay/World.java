package core.model.gameplay;

import java.util.ArrayList;
import java.util.List;

import core.model.gameplay.gameobjects.ai.*;
import org.newdawn.slick.SlickException;

import core.MathAdv;
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
                    Hero.getInstance().setX(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2);
                    Hero.getInstance().setY(tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2);
                    gameObjectList.add(Hero.getInstance());
                } else if (tileObjectName.equals("vampire")) {
                    VampireAI vampireAI = new VampireAI();
                    Bot bot = new Bot(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0, GameObjInstanceKind.VAMPIRE,
                            vampireAI);
                    vampireAI.setOwner(bot);
                    gameObjectList.add(bot);
                } else if (tileObjectName.equals("banditsword")) {
                    BanditAI banditAI = new BanditAI();
                    Bot bot = new Bot(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0, GameObjInstanceKind.BANDIT,
                            banditAI);
                    banditAI.setOwner(bot);
                    gameObjectList.add(bot);
                } else if (tileObjectName.equals("skeletsword")) {
                    SkeletonAI skeletonAI = new SkeletonAI();
                    Bot bot = new Bot(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0, GameObjInstanceKind.SKELETON,
                            skeletonAI);
                    skeletonAI.setOwner(bot);
                    gameObjectList.add(bot);
                } else if (tileObjectName.equals("banditarcher")) {
                    BanditArcherAI banditArcherAI = new BanditArcherAI();
                    Bot bot = new Bot(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0, GameObjInstanceKind.BANDITARCHER,
                            banditArcherAI);
                    banditArcherAI.setOwner(bot);
                    gameObjectList.add(bot);
                } else if (tileObjectName.equals("skeletonmage")) {
                    SkeletonMageAI skeletonMageAI = new SkeletonMageAI();
                    Bot bot = new Bot(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0, GameObjInstanceKind.SKELETONMAGE,
                            skeletonMageAI);
                    skeletonMageAI.setOwner(bot);
                    gameObjectList.add(bot);
                } else if (tileObjectName.equals("fireelemental")) {
                    BanditArcherAI banditArcherAI = new BanditArcherAI();
                    Bot bot = new Bot(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0, GameObjInstanceKind.FIREELEMENTAL,
                            banditArcherAI);
                    banditArcherAI.setOwner(bot);
                    gameObjectList.add(bot);
                } else if (tileObjectName.equals("waterelemental")) {
                    BanditArcherAI banditArcherAI = new BanditArcherAI();
                    Bot bot = new Bot(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0, GameObjInstanceKind.WATERELEMENTAL,
                            banditArcherAI);
                    banditArcherAI.setOwner(bot);
                    gameObjectList.add(bot);
                } else if (tileObjectName.equals("banditboss")) {
                    Bot bot = new Bot(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0, GameObjInstanceKind.BANDITBOSS,
                            null);
                    bot.setBotAI(new BanditBossAI(bot));
                    gameObjectList.add(bot);
                }
            }
        }

        gameObjectList.add(new Loot(Hero.getInstance().getX() + 40, Hero.getInstance().getY() - 70, Math.PI / 4,
                ItemDB.getInstance().getItem(ItemInstanceKind.SWORD), 1));
    }

    // Singleton pattern method
    public static World getInstance(boolean reset) {
        if (instance == null || reset) {
            Hero.getInstance(true);
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
            if (MathAdv.getDistance(gameObject.getX(), gameObject.getY(), Hero.getInstance().getX(), Hero.getInstance().getY()) < UPDATE_RADIUS) {
                gameObject.update(delta);
            }
        }
    }

    public List<GameObject> getGameObjectList() {
        return gameObjectList;
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