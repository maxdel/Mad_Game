package core.model;

import java.util.ArrayList;

public class World {

    private ArrayList<GameObject> gameObjects;

    private HeroController heroController;

    public World() {
        gameObjects = new ArrayList<GameObject>();

        Hero hero = new Hero(200, 100, 0, 0.2f);

        gameObjects.add(new Wall(100, 100, 0, 0));
        gameObjects.add(hero);

        heroController = new HeroController(hero);
    }

    public void update() {
        // ???
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public HeroController getHeroController() {
        return heroController;
    }

}