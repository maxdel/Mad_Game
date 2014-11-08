package core.model;

import java.util.ArrayList;

/**
 * Main model class, that imitates game world.
 * Contains all game objects.
 */
public class World {

    private ArrayList<GameObject> gameObjects;

    private HeroManager heroManager;

    public World() {
        gameObjects = new ArrayList<GameObject>();

        Hero hero = new Hero(200, 100, 0, 0.12f);

        gameObjects.add(new Wall(100, 100, 0, 0));
        gameObjects.add(hero);
        gameObjects.add(new Wall(300, 300, 0, 0));

        heroManager = new HeroManager(hero);
    }

    public void update() {
        // ???
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public HeroManager getHeroManager() {
        return heroManager;
    }

}