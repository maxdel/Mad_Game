package core.view.gameplay;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import core.model.gameplay.Enemy;
import core.model.gameplay.GameObject;
import core.model.gameplay.Hero;
import core.model.gameplay.Wall;

/*
* Renders game play game state
* */
public class GamePlayRenderer {

    private ArrayList<GameObject> gameObjects;
    private ArrayList<GameObjectRenderer> gameObjectRenderers;
    private HeroRenderer heroRenderer;
    private View view;

    public GamePlayRenderer() {

    }

    public GamePlayRenderer(GameContainer gc, ArrayList<GameObject> gameObjects, Hero hero) throws SlickException {
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

        view = new View(gc.getWidth(), gc.getHeight());
    }


    public void render(GameContainer gc, Graphics g) throws SlickException {
        view.update(heroRenderer.getHero().getX(), heroRenderer.getHero().getY(), heroRenderer.getHero().getDirection());

        heroRenderer.render(g, view.getX(), view.getY(), view.getDirectionAngle(), view.getWidth(), view.getHeight());

        for (GameObjectRenderer gameObjectRenderer : gameObjectRenderers) {
            gameObjectRenderer.render(g, view.getX(), view.getY(), view.getDirectionAngle(), view.getWidth(), view.getHeight());
        }
    }


}