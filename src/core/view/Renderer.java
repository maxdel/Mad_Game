package core.view;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import core.model.GameObject;
import core.model.Wall;
import core.model.World;

public class Renderer {

    World world;

    Image wallImage;

    public Renderer(World world) throws SlickException {
        this.world = world;

        wallImage = new Image("/res/Wall.png");
    }

    public void render(Graphics g) {
        for (GameObject gameObject : world.getGameObjects()) {
            if (gameObject instanceof Wall) {
                g.drawImage(wallImage, gameObject.getX(), gameObject.getY());
            }
        }
        g.drawString("Hello, IDEA git plugin3!", 300, 200);
        g.drawString("Hello, IDEA git plugin10!", 300, 200);
    }

}
