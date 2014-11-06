package core.view;

import core.model.Hero;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import core.model.GameObject;
import core.model.Wall;
import core.model.World;

public class Renderer {

    World world;

    Image wallImage;
    Image heroImage;

    public Renderer(World world) throws SlickException {
        this.world = world;

        wallImage = new Image("/res/Wall.png");
        heroImage = new Image("/res/Hero.png");
    }

    public void render(Graphics g) {
        for (GameObject gameObject : world.getGameObjects()) {
            if (gameObject instanceof Wall) {
                g.drawImage(wallImage, gameObject.getX(), gameObject.getY());
            } else if (gameObject instanceof Hero) {
                g.drawImage(heroImage, gameObject.getX(), gameObject.getY());
            }
        }
    }

}
