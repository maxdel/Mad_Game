package core.view;

import java.util.ArrayList;

import org.newdawn.slick.*;

import core.model.Enemy;
import core.model.GameObject;
import core.model.Hero;
import core.model.Wall;

/*
* Renders game play game state
* */
public class GamePlayView {

    private ArrayList<GameObjectView> gameObjectViews;
    private Hero hero;
    private HeroView heroView;
    private Camera camera;

    public GamePlayView(GameContainer gc, ArrayList<GameObject> gameObjects, Hero hero, ResourceManager resourceManager)
            throws SlickException {

        this.hero = hero;
        heroView = new HeroView(hero, resourceManager);

        gameObjectViews = new ArrayList<GameObjectView>();
        for (GameObject gameObject : gameObjects) {
            if (gameObject.getClass() == Wall.class) {
                gameObjectViews.add(new WallView(gameObject, resourceManager));
            } else if (gameObject.getClass() == Enemy.class) {
                gameObjectViews.add(new EnemyView(gameObject, resourceManager));
            } else if (gameObject.getClass() == Hero.class) {
                gameObjectViews.add(heroView);
            }
        }

        camera = new Camera(gc.getWidth(), gc.getHeight());
    }

    public void render(GameContainer gc, Graphics g) throws SlickException {
        camera.update(hero.getX(), hero.getY(), hero.getDirection());

        for (GameObjectView gameObjectView : gameObjectViews) {
            gameObjectView.render(g, camera.getX(), camera.getY(), camera.getDirectionAngle(), camera.getWidth(), camera.getHeight());
        }
    }

}