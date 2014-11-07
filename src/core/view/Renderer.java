package core.view;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.model.GameObject;
import core.model.Hero;
import core.model.Wall;

public class Renderer {

    ArrayList<GameObjectRenderer> gameObjectRenderers;

    public Renderer(final ArrayList<GameObject> gameObjects) throws SlickException {
        gameObjectRenderers = new ArrayList<GameObjectRenderer>();
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Hero) {
                gameObjectRenderers.add(new HeroRenderer((Hero) gameObject));
            } else if (gameObject instanceof Wall) {
                gameObjectRenderers.add(new WallRenderer((Wall) gameObject));
            }
        }
    }

    public void render(Graphics g) throws SlickException {
        for (GameObjectRenderer gameObjectRenderer : gameObjectRenderers) {
            gameObjectRenderer.render(g);
        }
    }

}