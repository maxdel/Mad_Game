package core.view.gameplay;

import org.newdawn.slick.Graphics;

import core.model.gameplay.GameObject;
import org.newdawn.slick.Renderable;

public abstract class GameObjectRenderer {

    protected GameObject gameObject;

    public GameObjectRenderer(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public abstract void render(Graphics g, final double viewX, final double viewY, final float viewDegreeAngle,
                                final int viewWidth, final int viewHeight);


    /*
    * Do rotation of game object
    * Returns view angle in degrees
    * */
    public void rotate(Graphics g, double viewX, double viewY, float viewDegreeAngle, int viewWidth, int viewHeight,
                        final boolean isFront) {
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

    public void draw(Renderable picture, final int pictureWidth, final int pictureHeight, double viewX, double viewY) {
        picture.draw((float) (gameObject.getX() - viewX - pictureWidth / 2),
                (float) (gameObject.getY() - viewY - pictureHeight / 2));
    }

}