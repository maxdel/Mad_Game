package core.view.gameplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import core.model.gameplay.Enemy;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import core.model.gameplay.GameObject;
import core.model.gameplay.Hero;
import core.model.gameplay.Wall;

/*
* Renders game play game state
* */
public class GamePlayRenderer {

    private static GamePlayRenderer instance;

    private ArrayList<GameObject> gameObjects;
    private ArrayList<GameObjectRenderer> gameObjectRenderers;
    private HeroRenderer heroRenderer;
    private View view;

    private Map<Class, Class> renderersMap;

    private GamePlayRenderer() {

    }

    public GamePlayRenderer(ArrayList<GameObject> gameObjects, Hero hero) throws SlickException {
        renderersMap = new HashMap<Class, Class>();
        renderersMap.put(Wall.class, WallRenderer.class);

        this.gameObjects = gameObjects;

        heroRenderer = new HeroRenderer(hero);

        gameObjectRenderers = new ArrayList<GameObjectRenderer>();
        for (GameObject gameObject : gameObjects) {
            if (gameObject.getClass() == Wall.class) {
                gameObjectRenderers.add(new WallRenderer(gameObject, new Image("/res/Wall.png")));
            }
            else if (gameObject.getClass() == Enemy.class) {
                gameObjectRenderers.add(new EnemyRenderer(gameObject, new Image("/res/Enemy.png")));
            }
        }

        view = new View();
    }

    // Singleton pattern method
    public static GamePlayRenderer getInstance(ArrayList<GameObject> gameObjects, Hero hero) throws SlickException {
        if (instance == null) {
            instance = new GamePlayRenderer(gameObjects, hero);
        }
        return instance;
    }

    public void render(GameContainer gc, Graphics g) throws SlickException {
        updateView(gc);

        heroRenderer.render(g, view.getX(), view.getY(), view.getDirection(), view.getWidth(), view.getHeight());

        for (GameObjectRenderer gameObjectRenderer : gameObjectRenderers) {
            gameObjectRenderer.render(g, view.getX(), view.getY(), view.getDirection(), view.getWidth(), view.getHeight());
        }
    }

    private void updateView(GameContainer gc) {
        view.setWidth(gc.getWidth());
        view.setHeight(gc.getHeight());
        view.setDirection(heroRenderer.getHero().getDirection() + Math.PI / 2);
        view.setX(heroRenderer.getHero().getX() - view.getWidth() / 2);
        view.setY(heroRenderer.getHero().getY() - view.getHeight() / 2);
    }

}