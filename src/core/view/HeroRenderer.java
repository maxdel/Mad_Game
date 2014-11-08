package core.view;

import core.model.Hero;
import org.newdawn.slick.*;

/**
 * Provides functions for the definition of actors, as well as actor
 * operations, such as `receive`, `react`, `reply`, etc.
 */
public class HeroRenderer extends GameObjectRenderer {

    private final int ANIMATIONSPEED = 100;
    private Animation animation;
    private int imageWidth, imageHeight;

    public HeroRenderer(Hero hero) throws SlickException {
        super(hero);

        imageWidth = 50;
        imageHeight = 50;

        SpriteSheet spriteSheet = new SpriteSheet("/res/Hero.png", imageWidth, imageHeight);
        animation = new Animation(spriteSheet, ANIMATIONSPEED);
    }

    @Override
    public void render(Graphics g) {
        //double rotateAngle = gameObject.getDirection() / Math.PI * 180 + 90;
        double rotateAngle = 90;
        //g.rotate((float) gameObject.getX(), (float) gameObject.getY(), (float) rotateAngle);

        double direction = gameObject.getDirection();
        while (direction < 0)
            direction += 2 * Math.PI;
        while (direction > 2 * Math.PI)
            direction -= 2 * Math.PI;

        animation.draw(320 - imageWidth/2, 240 - imageHeight/2);
        g.drawString("(" + String.valueOf((int)gameObject.getX()) + ";" + String.valueOf((int)gameObject.getY())
                        + ") dir=" + String.valueOf((int)(gameObject.getDirection() / Math.PI * 180) % 360), 320, 240);

        //g.rotate((float) gameObject.getX(), (float) gameObject.getY(), (float) -rotateAngle);
    }

    @Override
    public void render(Graphics g, double viewX, double viewY, final double direction) {
        float rotateAngle = (float) (direction / Math.PI * 180);

        float xx = 320;
        float yy = 240;

        //g.rotate(xx, yy, rotateAngle);
        animation.draw((float) (gameObject.getX() - imageWidth / 2 + viewX),
                (float) (gameObject.getY() - imageHeight / 2 + viewY));
        g.drawString("(" + String.valueOf((int)gameObject.getX()) + ";" + String.valueOf((int)gameObject.getY())
                        + ") dir=" + String.valueOf((int)gameObject.getDirection()), (float) gameObject.getX(),
                (float) gameObject.getY());
        //g.rotate(xx, yy, -rotateAngle);
    }

}