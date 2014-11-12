package core.view.gameplay;

import core.view.ResourceManager;
import org.newdawn.slick.*;

import core.model.gameplay.GameObject;
import org.newdawn.slick.geom.Shape;

public abstract class GameObjectView {

    protected GameObject gameObject;
    protected Animation animation;
    protected ResourceManager resourceManager;

    public GameObjectView(GameObject gameObject, ResourceManager resourceManager) {
        this.gameObject = gameObject;
        this.resourceManager = resourceManager;
        this.animation = null;
    }

    public abstract void render(Graphics g, final double viewX, final double viewY, final float viewDegreeAngle,
                                final int viewWidth, final int viewHeight);

    public void rotate(Graphics g, final double viewX, final double viewY, final float viewDegreeAngle,
                       final int viewWidth, final int viewHeight, final boolean isFront) {
        if (isFront) {
            //Rotate around view center to set position on the View
            g.rotate(viewWidth / 2, viewHeight / 2, - viewDegreeAngle);
            //Rotate around gameObject coordinates to set direction of gameObject
            g.rotate((float) (gameObject.getX() - viewX),
                    (float) (gameObject.getY() - viewY),
                    (float)(gameObject.getDirection() / Math.PI * 180));
            // Coordinates to draw image according to position on the View
        } else {
            g.rotate((float) (gameObject.getX() - viewX),
                    (float) (gameObject.getY() - viewY),
                    -(float) (gameObject.getDirection() / Math.PI * 180));
            g.rotate(viewWidth / 2, viewHeight / 2, viewDegreeAngle);
        }
    }

    public void draw(final double viewX, final double viewY) {
        animation.draw((float) (gameObject.getX() - viewX - animation.getWidth() / 2),
                (float) (gameObject.getY() - viewY - animation.getHeight() / 2));
    }

    /*
       * Draws mask around a game object
       * */
    protected void drawMask(Graphics g, final double viewX, final double viewY) {
        Shape temp = gameObject.getMovedMask(gameObject.getMask(),
                (float) gameObject.getX() - (float) viewX,
                (float) gameObject.getY() - (float) viewY, gameObject.getDirection());
        g.draw(temp);
    }
}