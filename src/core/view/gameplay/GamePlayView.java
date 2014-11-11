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
    private Hero hero;
    private HeroView heroView;
    private Camera camera;

    public GamePlayView() {

    }

    public GamePlayView(GameContainer gc, ArrayList<GameObject> gameObjects, Hero hero) throws SlickException {
        this.gameObjects = gameObjects;
        gameObjectViews = new ArrayList<GameObjectView>();
        for (GameObject gameObject : gameObjects) {
            if (gameObject.getClass() == Wall.class) {
                gameObjectViews.add(new WallView(gameObject));
            }
            else if (gameObject.getClass() == Enemy.class) {
                gameObjectViews.add(new EnemyView(gameObject));
            }
        }

        this.hero = hero;
        heroView = new HeroView(hero);

        camera = new Camera(gc.getWidth(), gc.getHeight());
    }

    public void render(GameContainer gc, Graphics g) throws SlickException {
        camera.update(hero.getX(), hero.getY(), hero.getDirection());

        heroView.render(g, camera.getX(), camera.getY(), camera.getDirectionAngle(), camera.getWidth(), camera.getHeight());

        for (GameObjectView gameObjectView : gameObjectViews) {
            gameObjectView.render(g, camera.getX(), camera.getY(), camera.getDirectionAngle(), camera.getWidth(), camera.getHeight());
        }
    }

}