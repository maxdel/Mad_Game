package core.view.gameplay;

import core.model.gameplay.Enemy;
import core.model.gameplay.Wall;
import org.newdawn.slick.*;

import core.model.gameplay.GameObject;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class WallView extends GameObjectView {

    public WallView(GameObject wall) throws SlickException {
        super(wall);

        setAnimation("/res/Wall.png");
    }

    @Override
    public void render(Graphics g, final double viewX, final double viewY, final float viewDegreeAngle,
                       final int viewWidth, final int viewHeight) {
        rotate(g, viewX, viewY, viewDegreeAngle, viewWidth, viewHeight, true);
        draw(viewX, viewY);

        // draw mask
        Shape temp = new Rectangle(gameObject.getMask().getX() + (float) gameObject.getX() - (float) viewX,
                gameObject.getMask().getY() + (float) gameObject.getY() - (float) viewY,
                gameObject.getMask().getWidth(), gameObject.getMask().getHeight());

        g.draw(temp);
        //--

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
        rotate(g, viewX, viewY, viewDegreeAngle, viewWidth, viewHeight, false);
    }

}