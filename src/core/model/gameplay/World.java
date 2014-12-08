package core.model.gameplay;

import core.model.gameplay.items.ItemDB;
import core.model.gameplay.items.Loot;
import core.model.gameplay.units.*;
import core.resourcemanager.MadTiledMap;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;
import java.util.List;

/**
 * Main model class, that imitates game world.
 * Contains all game objects.
 */
public class World {

    private static World instance;

    private List<Obstacle> obstacles;
    private List<Obstacle> toDeleteList;
    private List<Obstacle> toAddList;
    private Hero hero;
    private List<Loot> lootList;
    private CollisionManager collisionManager;

    private MadTiledMap tiledMap;

    private World() {
        obstacles = new ArrayList<Obstacle>();
        toDeleteList = new ArrayList<Obstacle>();
        toAddList = new ArrayList<Obstacle>();
        lootList = new ArrayList<Loot>();
        collisionManager = CollisionManager.getInstance();

        try {
            tiledMap = new MadTiledMap("/res/map.tmx");
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
                    obstacles.add(new Wall(
                            tiledMap.getObjectX(i, j) + lengthDirX(-Math.PI/4 + rotation, length),
                            tiledMap.getObjectY(i, j) + lengthDirY(-Math.PI/4 + rotation, length),
                                    rotation)
                    );
                }
            }
        }

        for (int i = 0; i < tiledMap.getWidth(); ++i) {
            for (int j = 0; j < tiledMap.getHeight(); ++j) {
                // Obstacles
                String tileObstacleName = tiledMap.getTileProperty(tiledMap.getTileId(i, j, 1), "name", "error");
                if (tileObstacleName.equals("stonewall")) {
                    obstacles.add(new Wall(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0));
                } else if (tileObstacleName.equals("tree")) {
                    obstacles.add(new Tree(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0));
                }
                // Objects
                String tileObjectName = tiledMap.getTileProperty(tiledMap.getTileId(i, j, 2), "name", "error");
                if (tileObjectName.equals("hero")) {
                    hero = new Hero(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0.18F);
                    obstacles.add(hero);
                } else if (tileObjectName.equals("banditsword")) {
                    obstacles.add(new Bandit(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0.1F));
                } else if (tileObjectName.equals("banditarcher")) {
                    obstacles.add(new BanditArcher(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0.1F));
                } else if (tileObjectName.equals("skeletsword")) {
                    obstacles.add(new Skeleton(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0.15F));
                } else if (tileObjectName.equals("skeletmage")) {
                    obstacles.add(new SkeletonMage(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0.15F));
                } else if (tileObjectName.equals("vampire")) {
                    obstacles.add(new Vampire(tiledMap.getTileWidth() * i + tiledMap.getTileWidth() / 2,
                            tiledMap.getTileHeight() * j + tiledMap.getTileHeight() / 2, 0.1F));
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
        for (Obstacle obstacle : toAddList) {
            obstacles.add(obstacle);
        }
        toAddList.clear();
        for (Obstacle obstacle : obstacles) {
            Vector2f v = new Vector2f((float)(hero.getX() - obstacle.getX()), (float)(hero.getY() - obstacle.getY()));
            if (v.length() < 1000) {
                obstacle.update(delta);
            }
        }
        for (Obstacle obstacle : toDeleteList) {
            obstacles.remove(obstacle);
        }
        toDeleteList.clear();
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public Hero getHero() {
        return hero;
    }

    public List<Loot> getLootList() {
        return lootList;
    }

    public MadTiledMap getTiledMap() {
        return tiledMap;
    }

    public List<Obstacle> getToDeleteList() {
        return toDeleteList;
    }

    public List<Obstacle> getToAddList() {
        return toAddList;
    }

    protected double lengthDirX(double direction, double length) {
        return Math.cos(direction) * length;
    }

    protected double lengthDirY(double direction, double length) {
        return Math.sin(direction) * length;
    }

}