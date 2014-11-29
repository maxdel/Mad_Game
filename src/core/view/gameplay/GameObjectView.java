package core.view.gameplay;

import core.model.gameplay.Hero;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

import core.ResourceManager;
import core.model.gameplay.CollisionManager;
import core.model.gameplay.GameObject;
import org.newdawn.slick.geom.Vector2f;

public abstract class GameObjectView {

    protected GameObject gameObject;
    protected Animation animation;
    protected ResourceManager resourceManager;

    public GameObjectView(GameObject gameObject, ResourceManager resourceManager) {
        this.gameObject = gameObject;
        this.resourceManager = resourceManager;
        this.animation = null;
    }

    public void render(Graphics g, double viewX, double viewY, float viewDegreeAngle,
                       double viewCenterX, double viewCenterY, Hero hero) throws SlickException {
        Vector2f v = new Vector2f((float)(hero.getX() - gameObject.getX()), (float)(hero.getY() - gameObject.getY()));

        if (v.length() < 1500) {
            rotate(g, viewX, viewY, viewDegreeAngle, viewCenterX, viewCenterY, true);
            draw(viewX, viewY);
            rotate(g, viewX, viewY, viewDegreeAngle, viewCenterX, viewCenterY, false);
        }
    }

    public void rotate(Graphics g, double viewX, double viewY, float viewDegreeAngle,
                       double viewCenterX, double viewCenterY, boolean isFront) {
        if (isFront) {
            g.rotate((float)viewCenterX, (float)viewCenterY, - viewDegreeAngle);
            g.rotate((float) (gameObject.getX() - viewX),
                    (float) (gameObject.getY() - viewY),
                    (float)(gameObject.getDirection() / Math.PI * 180));
        } else {
            g.rotate((float) (gameObject.getX() - viewX),
                    (float) (gameObject.getY() - viewY),
                    (float) - (gameObject.getDirection() / Math.PI * 180));
            g.rotate((float)viewCenterX, (float)viewCenterY, viewDegreeAngle);
        }
    }

    public void draw(double viewX, double viewY) {
        animation.draw((float) (gameObject.getX() - viewX - animation.getWidth() / 2),
                (float) (gameObject.getY() - viewY - animation.getHeight() / 2));
    }

    protected void drawHealthbar(Graphics g, int x, int y, int width, int height, double current, double maximum,
                                 Color color) {
        Color tempColor = g.getColor();
        g.setColor(Color.white);
        g.fillRect(x - width / 2, y - height / 2, width, height);
        g.setColor(color);
        g.fillRect(x - width / 2, y - height / 2, width * (float)(current / maximum), height);
        g.setColor(Color.darkGray);
        g.drawRect(x - width / 2, y - height / 2, width, height);

        g.setColor(tempColor);
    }

    /*
     * Draws mask around a game object
     * */
    protected void drawMask(Graphics g, double viewX, double viewY) {
        Shape mask = CollisionManager.getInstance().getUpdatedMask(gameObject, (float) gameObject.getX() - (float) viewX,
                (float) gameObject.getY() - (float) viewY, gameObject.getDirection());
        g.draw(mask);
    }

}