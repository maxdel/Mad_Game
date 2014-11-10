package core.view.gameplay;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.SlickException;

import core.model.gameplay.Hero;

/**
 * Provides functions for the definition of actors, as well as actor
 * operations, such as `receive`, `react`, `reply`, etc.
 */
public class HeroRenderer extends GameObjectRenderer {

    private int imageWidth, imageHeight;
    private Animation animation;
    private String previousState;

    private Hero hero;

    public HeroRenderer(Hero hero) throws SlickException {
        super(hero);

        this.hero = hero;

        imageWidth = 50;
        imageHeight = 50;

        SpriteSheet spriteSheet = new SpriteSheet("/res/Hero.png", imageWidth, imageHeight);
        animation = new Animation(spriteSheet, 1);
        animation.setSpeed((float)gameObject.getMaximumSpeed() / 10F);

        previousState = "";
    }

    @Override
    public void render(Graphics g, final double viewX, final double viewY, final double viewDirection,
                       final int viewWidth, final int viewHeight) {
        Hero hero = (Hero)gameObject;
        if (!hero.getCurrentState().equals(previousState)) {
            if (hero.getCurrentState().equals("Walk")) {
                animation.start();
                animation.setSpeed((float)gameObject.getCurrentSpeed() / 6F);
            }
            else if (hero.getCurrentState().equals("Stand")) {
                animation.stop();
                animation.setCurrentFrame(4);
            } else if (hero.getCurrentState().equals("Run")) {
                animation.start();
                animation.setSpeed((float)gameObject.getCurrentSpeed() / 6F);
            }
        }
        previousState = hero.getCurrentState();

        float viewDegreeAngle = (float) (viewDirection / Math.PI * 180);

        //Rotate around view center to set position on the View
        g.rotate(viewWidth / 2, viewHeight / 2, - viewDegreeAngle);
        //Rotate around gameObject coordinates to set direction of gameObject
        g.rotate((float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY),
                (float)(gameObject.getDirection() / Math.PI * 180));
        // Coordinates to draw image according to position on the View
        animation.draw((float) (gameObject.getX() - viewX - animation.getWidth() / 2),
                (float) (gameObject.getY() - viewY - animation.getHeight() / 2));
        g.rotate((float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY),
                -(float) (gameObject.getDirection() / Math.PI * 180));
        g.rotate(viewWidth / 2, viewHeight / 2, viewDegreeAngle);

        // For debug
        g.drawString("(" + String.valueOf((int) gameObject.getX()) + ";" + String.valueOf((int) gameObject.getY())
                + ") dir=" + String.valueOf((int) (gameObject.getDirection() / Math.PI * 180) % 360),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY));
    }

    public Hero getHero() {
        return (Hero) gameObject;
    }

}