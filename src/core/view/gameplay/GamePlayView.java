package core.view.gameplay;

import java.util.ArrayList;

import org.newdawn.slick.*;

import core.model.gameplay.Enemy;
import core.model.gameplay.GameObject;
import core.model.gameplay.Hero;
import core.model.gameplay.Wall;

/*
* Renders game play game state
* */
public class GamePlayView {

    private ArrayList<GameObject> gameObjects;
    private ArrayList<GameObjectView> gameObjectViews;
    private HeroView heroRenderer;
    private Camera camera;

    public GamePlayView() {

    }

    public GamePlayView(GameContainer gc, ArrayList<GameObject> gameObjects, Hero hero) throws SlickException {
        this.gameObjects = gameObjects;

        heroRenderer = new HeroView(hero);

        gameObjectViews = new ArrayList<GameObjectView>();
        for (GameObject gameObject : gameObjects) {
            if (gameObject.getClass() == Wall.class) {
                gameObjectViews.add(new WallView(gameObject));
            }
            else if (gameObject.getClass() == Enemy.class) {
                gameObjectViews.add(new EnemyView(gameObject));
            }
        }

        camera = new Camera(gc.getWidth(), gc.getHeight());
    }


    public void render(GameContainer gc, Graphics g) throws SlickException {
        camera.update(heroRenderer.getHero().getX(), heroRenderer.getHero().getY(), heroRenderer.getHero().getDirection());

        heroRenderer.render(g, camera.getX(), camera.getY(), camera.getDirectionAngle(), camera.getWidth(), camera.getHeight());

        for (GameObjectView gameObjectView : gameObjectViews) {
            gameObjectView.render(g, camera.getX(), camera.getY(), camera.getDirectionAngle(), camera.getWidth(), camera.getHeight());
        }
    }


}