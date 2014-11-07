package core.view;

import core.model.Hero;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class HeroRenderer extends GameObjectRenderer {

    private static final int ANIMATIONSPEED = 100;
    private Animation hero, movementUp, movementDown, movementLeft, movementRight, stillUp, stillDown, stillLeft, stillRight;
    private int w, h;

    public HeroRenderer(Hero heroEntity) throws SlickException {
        super(heroEntity);
        /**
         * Set the Image arrays needed for the animations.
         */
        Image[] animationUp = {new Image("res/hero20.png"), new Image("res/hero22.png")};
        Image[] animationDown = {new Image("res/hero00.png"), new Image("res/hero02.png")};
        Image[] animationLeft = {new Image("res/hero30.png"), new Image("res/hero32.png")};
        Image[] animationRight = {new Image("res/hero10.png"), new Image("res/hero12.png")};
        Image[] up = {new Image("res/hero21.png")};
        Image[] down = {new Image("res/hero01.png")};
        Image[] left = {new Image("res/hero31.png")};
        Image[] right = {new Image("res/hero11.png")};

        /**
         * Set the width and the height of Hero's image
         */
        w = down[0].getWidth();
        h = down[0].getHeight();

        /**
         * Set the Animations needed.
         */
        stillUp = new Animation(up, ANIMATIONSPEED);
        stillDown = new Animation(down, ANIMATIONSPEED);
        stillLeft = new Animation(left, ANIMATIONSPEED);
        stillRight = new Animation(right, ANIMATIONSPEED);
        movementUp = new Animation(animationUp, ANIMATIONSPEED);
        movementDown = new Animation(animationDown, ANIMATIONSPEED);
        movementLeft = new Animation(animationLeft, ANIMATIONSPEED);
        movementRight = new Animation(animationRight, ANIMATIONSPEED);

        hero = stillDown;
    }

    public void render(Graphics g) {
        g.rotate((float) gameObject.getX(), (float) gameObject.getY(),
                (float) (gameObject.getDirection() / Math.PI * 180 + 90));

        if (-Math.PI / 4 <= gameObject.getDirection() && gameObject.getDirection() < Math.PI / 4) {
            setHero(movementRight);
        } else if (Math.PI / 4 <= gameObject.getDirection() && gameObject.getDirection() < 3 * Math.PI / 4) {
            setHero(movementDown);
        } else if (3 * Math.PI / 4 <= gameObject.getDirection() && gameObject.getDirection() < 5 * Math.PI / 4) {
            setHero(movementLeft);
        } else if (7 * Math.PI / 4 <= gameObject.getDirection() && gameObject.getDirection() < 7 * Math.PI / 4) {
            setHero(movementUp);
        }

        hero.draw((float) gameObject.getX() - w/2, (float) gameObject.getY() - h/2);

        g.rotate((float) gameObject.getX(), (float) gameObject.getY(),
                (float) -(gameObject.getDirection() / Math.PI * 180 + 90));
    }



    public void setHero(Animation hero) {
        this.hero = hero;
    }


    public Animation getMovementUp() {
        return movementUp;
    }


    public Animation getMovementDown() {
        return movementDown;
    }

    public Animation getMovementLeft() {
        return movementLeft;
    }


    public Animation getMovementRight() {
        return movementRight;
    }


    public Animation getStillUp() {
        return stillUp;
    }

    public Animation getStillDown() {
        return stillDown;
    }


    public Animation getStillLeft() {
        return stillLeft;
    }


    public Animation getStillRight() {
        return stillRight;
    }

}