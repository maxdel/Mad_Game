package core.view;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.model.GameObject;
import core.model.Hero;
import core.model.Wall;

public class Renderer {

    ArrayList<GameObject> gameObjects;

    HeroRepresentation heroRepresentation;
    WallRepresentation wallRepresentation;

    public Renderer(final ArrayList<GameObject> gameObjects) throws SlickException {
        heroRepresentation = new HeroRepresentation((Hero) gameObjects.get(1));
        this.gameObjects = gameObjects;
        }

    public void render(Graphics g) throws SlickException {
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Wall) {
                wallRepresentation = new WallRepresentation((Wall) gameObject);
                wallRepresentation.render(g);
            }
        }

        // MUST BE RENDERED LAST
        heroRepresentation.render(g);
    }

    public HeroRepresentation getHeroRepresentation() {
        return heroRepresentation;
    }

}