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
                g.drawImage(wallImage, (float)gameObject.getX(), (float)gameObject.getY());
            } else if (gameObject instanceof Hero) {
                g.rotate((float)gameObject.getX(), (float)gameObject.getY(),
                        (float)(gameObject.getDirection() / Math.PI * 180));
                g.drawImage(heroImage, (float) gameObject.getX() - heroImage.getWidth() / 2,
                        (float) gameObject.getY() - heroImage.getHeight() / 2);
                g.rotate(0, 0, 0); //???
            }
        }
    }

}