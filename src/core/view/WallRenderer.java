package core.view;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import core.model.Wall;

public class WallRenderer extends GameObjectRenderer {

    Image wallImage;

    public WallRenderer(Wall wall) {
        super(wall);

        try {
            wallImage = new Image("/res/Wall.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(wallImage, (float)gameObject.getX() - wallImage.getWidth() / 2,
                (float)gameObject.getY() - wallImage.getHeight() / 2);
    }
}