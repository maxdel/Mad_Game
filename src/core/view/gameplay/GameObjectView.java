package core.view.gameplay;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

import core.ResourceManager;
import core.model.gameplay.CollisionManager;
import core.model.gameplay.GameObject;

public abstract class GameObjectView {

    protected GameObject gameObject;
    protected Animation animation;
    protected ResourceManager resourceManager;

    public GameObjectView(GameObject gameObject, ResourceManager resourceManager) {
        this.gameObject = gameObject;
        this.resourceManager = resourceManager;
        this.animation = null;
    }

    public void render(Graphics g, final double viewX, final double viewY, final float viewDegreeAngle,
                                final int viewWidth, final int viewHeight) throws SlickException {
        rotate(g, viewX, viewY, viewDegreeAngle, viewWidth, viewHeight, true);
        draw(viewX, viewY);
        rotate(g, viewX, viewY, viewDegreeAngle, viewWidth, viewHeight, false);
    }

    public void rotate(Graphics g, final double viewX, final double viewY, final float viewDegreeAngle,
                       final int viewWidth, final int viewHeight, final boolean isFront) {
        if (isFront) {
            g.rotate(viewWidth / 2, viewHeight / 2, - viewDegreeAngle);
            g.rotate((float) (gameObject.getX() - viewX),
                    (float) (gameObject.getY() - viewY),
                    (float)(gameObject.getDirection() / Math.PI * 180));
        } else {
            g.rotate((float) (gameObject.getX() - viewX),
                    (float) (gameObject.getY() - viewY),
                    (float) - (gameObject.getDirection() / Math.PI * 180));
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
        Shape mask = CollisionManager.getInstance().getUpdatedMask(gameObject, (float) gameObject.getX() - (float) viewX,
                (float) gameObject.getY() - (float) viewY, gameObject.getDirection());
        g.draw(mask);
    }

}