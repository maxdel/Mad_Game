package core.view;

import core.model.Wall;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class WallRepresentation extends GameObjectRepresentation {

    Image wallImage;

    public WallRepresentation(Wall wall) {
        super(wall);

        try {
            wallImage = new Image("/res/Wall.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(wallImage, (float)gameObject.getX(), (float)gameObject.getY());
    }
}
