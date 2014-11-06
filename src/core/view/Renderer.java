package core.view;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import core.model.GameObject;
import core.model.Hero;
import core.model.Wall;


public class Renderer {

    ArrayList<GameObject> gameObjects;

    Image wallImage;
    Image heroImage;

    public Renderer(final ArrayList<GameObject> gameObjects) throws SlickException {
        this.gameObjects = gameObjects;

        wallImage = new Image("/res/Wall.png");
        heroImage = new Image("/res/Hero.png");
    }

    public void render(Graphics g) {
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Wall) {
                g.drawImage(wallImage, gameObject.getX(), gameObject.getY());
            } else if (gameObject instanceof Hero) {
                g.drawImage(heroImage, gameObject.getX(), gameObject.getY());
            }
        }
    }

}