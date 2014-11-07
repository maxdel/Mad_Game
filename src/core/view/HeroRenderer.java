package core.view;

import core.model.Hero;
import org.newdawn.slick.*;

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

    public void render(Graphics g) {
        //double rotateAngle = gameObject.getDirection() / Math.PI * 180 + 90;
        double rotateAngle = 90;
        //g.rotate((float) gameObject.getX(), (float) gameObject.getY(), (float) rotateAngle);

        double direction = gameObject.getDirection();
        while (direction < 0)
            direction += 2 * Math.PI;
        while (direction > 2 * Math.PI)
            direction -= 2 * Math.PI;

        animation.draw(320, 240 - imageHeight/2);

        //g.rotate((float) gameObject.getX(), (float) gameObject.getY(), (float) -rotateAngle);
    }

}