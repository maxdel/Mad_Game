package core.model;

import java.util.ArrayList;

public class World {

    private ArrayList<GameObject> gameObjects;

    public World() {
        gameObjects = new ArrayList<GameObject>();

        gameObjects.add(new Wall(100, 100, 0, 0));
    }

    public void update() {

    }

}