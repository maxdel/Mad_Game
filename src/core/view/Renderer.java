package core.view;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.model.GameObject;
import core.model.Hero;
import core.model.Wall;

public class Renderer {

    private ArrayList<GameObjectRenderer> gameObjectRenderers;

    View view;

    public Renderer(final ArrayList<GameObject> gameObjects) throws SlickException {
        gameObjectRenderers = new ArrayList<GameObjectRenderer>();
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Hero) {
                gameObjectRenderers.add(new HeroRenderer((Hero) gameObject));
            } else if (gameObject instanceof Wall) {
                gameObjectRenderers.add(new WallRenderer((Wall) gameObject));
            }
        }

        view = new View();
    }

    public void render(Graphics g) throws SlickException {
        float x = 0, y = 0, dir = 0;
        for (GameObjectRenderer gameObjectRenderer : gameObjectRenderers) {
            if (gameObjectRenderer instanceof HeroRenderer) {
                dir = (float) gameObjectRenderer.gameObject.getDirection();
                view.setX(gameObjectRenderer.gameObject.getX() - 320);
                view.setY(gameObjectRenderer.gameObject.getY() - 240);
            }
        }

        for (GameObjectRenderer gameObjectRenderer : gameObjectRenderers) {
            if (gameObjectRenderer instanceof HeroRenderer) {
                gameObjectRenderer.render(g);
            } else {
                gameObjectRenderer.render(g, view.getX(), view.getY(), dir);
            }
        }
    }

}