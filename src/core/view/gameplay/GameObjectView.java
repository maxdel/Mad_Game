package core.view.gameplay;

import org.newdawn.slick.*;

import core.model.gameplay.GameObject;

public abstract class GameObjectView {

    protected GameObject gameObject;
    protected Animation animation;

    public GameObjectView(GameObject gameObject) {
        this(gameObject, null);
    }

    public GameObjectView(GameObject gameObject, Animation animation) {
        this.gameObject = gameObject;
        this.animation = animation;
        /* Sets size of the game object in model (needed for forming mask)*/

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

    protected void setAnimation(String pass) throws SlickException {
        Image image = new Image(pass);
        SpriteSheet spriteSheet = new SpriteSheet(image, image.getWidth(), image.getHeight());
        animation = new Animation(spriteSheet, 1);
        gameObject.setSize(image.getWidth(), image.getHeight());
    }

    protected void setAnimation(final String pass, final int cropWidth, final int cropHeight) throws SlickException {
        SpriteSheet spriteSheet = new SpriteSheet(pass, cropWidth, cropHeight);
        animation = new Animation(spriteSheet, 1);
        gameObject.setSize(animation.getImage(1).getWidth(), animation.getImage(1).getHeight());
    }

}