package core.view.gameplay;

import core.model.gameplay.Hero;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.ResourceManager;
import core.model.gameplay.GameObject;
import org.newdawn.slick.geom.Vector2f;

public class WallView extends GameObjectView {

    public WallView(GameObject wall, ResourceManager resourceManager) throws SlickException {
        super(wall, resourceManager);
        animation = resourceManager.getAnimation("wall");
    }

    @Override
    public void render(Graphics g, final double viewX, final double viewY, final float viewDegreeAngle,
                       final int viewWidth, final int viewHeight, Hero hero) {
        // TODO fix magic number
        Vector2f v = new Vector2f((float)(hero.getX() - gameObject.getX()), (float)(hero.getY() - gameObject.getY()));
        if (v.length() >= 1000) {
            return;
        }


        rotate(g, viewX, viewY, viewDegreeAngle, viewWidth, viewHeight, true);
        draw(viewX, viewY);

        // draw mask
        g.rotate((float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY),
                -(float) (gameObject.getDirection() / Math.PI * 180));
        drawMask(g, viewX, viewY);
        g.rotate((float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY),
                (float) (gameObject.getDirection() / Math.PI * 180));

        // ----- For debug and FUN -----
        /*g.rotate((float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY),
                viewDegreeAngle);
        g.drawString("(" + String.valueOf(gameObject.getX()) + ";" + String.valueOf(gameObject.getY()) + ")",
                (float) (gameObject.getX() - viewX), (float) (gameObject.getY() - viewY));
        g.rotate((float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY),
                - viewDegreeAngle);*/

        // ----- END -----
        rotate(g, viewX, viewY, viewDegreeAngle, viewWidth, viewHeight, false);
    }

}