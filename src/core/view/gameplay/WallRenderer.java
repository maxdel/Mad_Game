package core.view.gameplay;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import core.model.gameplay.GameObject;

public class WallRenderer extends GameObjectRenderer {

    Image wallImage;

    public WallRenderer(GameObject wall, Image wallImage) {
        super(wall);
        this.wallImage = wallImage;
    }

    @Override
    public void render(Graphics g, final double viewX, final double viewY, final float viewDegreeAngle,
                       final int viewWidth, final int viewHeight) {


        super.rotate(g, viewX, viewY, viewDegreeAngle, viewWidth, viewHeight,
                wallImage, wallImage.getWidth(), wallImage.getHeight());

        // ----- For debug and FUN -----
        g.rotate((float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY),
                viewDegreeAngle);
        g.drawString("(" + String.valueOf(gameObject.getX()) + ";" + String.valueOf(gameObject.getY()) + ")",
                (float) (gameObject.getX() - viewX), (float) (gameObject.getY() - viewY));
        g.rotate((float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY),
                - viewDegreeAngle);
        // ----- END -----
        g.rotate(viewWidth / 2, viewHeight / 2, viewDegreeAngle);
    }

}