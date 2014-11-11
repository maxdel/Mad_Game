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

        animation.setSpeed((float)hero.getMaximumSpeed() / 10F);

        previousState = "";
    }

    @Override
    public void render(Graphics g, final double viewX, final double viewY, final float viewDegreeAngle,
                       final int viewWidth, final int viewHeight) {
        Hero hero = (Hero)gameObject;
        if (!hero.getCurrentState().equals(previousState)) {
            if (hero.getCurrentState().equals("Walk")) {
                animation.start();
                animation.setSpeed((float)hero.getCurrentSpeed() / 6F);
            }
            else if (hero.getCurrentState().equals("Stand")) {
                animation.stop();
                animation.setCurrentFrame(4);
            } else if (hero.getCurrentState().equals("Run")) {
                animation.start();
                animation.setSpeed((float)hero.getCurrentSpeed() / 6F);
            }
        }
        previousState = hero.getCurrentState();


        rotate(g, viewX, viewY, viewDegreeAngle, viewWidth, viewHeight, true);
        draw(animation, animation.getWidth(), animation.getHeight(), viewX, viewY);
        rotate(g, viewX, viewY, viewDegreeAngle, viewWidth, viewHeight, false);

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