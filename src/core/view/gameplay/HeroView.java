package core.view.gameplay;

import core.model.gameplay.World;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.model.gameplay.GameObject;
import core.model.gameplay.Hero;
import core.model.gameplay.GameObjectState;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

/**
 * Provides functions for the definition of actors, as well as actor
 * operations, such as `receive`, `react`, `reply`, etc.
 */
public class HeroView extends GameObjectView {

    private GameObjectState previousState;

    public HeroView(GameObject hero) throws SlickException {
        super(hero);

        final int imageWidth = 50;
        final int imageHeight = 50;

        setAnimation("/res/Hero.png", imageWidth, imageHeight);
    }

    @Override
    public void render(Graphics g, final double viewX, final double viewY, final float viewDegreeAngle,
                       final int viewWidth, final int viewHeight) {
        Hero hero = getHero();
        if (hero.getCurrentState() != previousState) {
            switch (hero.getCurrentState()) {
                case WALK:
                    animation.start();
                    animation.setSpeed((float) hero.getCurrentSpeed() / 6F);
                    break;
                case STAND:
                    animation.stop();
                    animation.setCurrentFrame(4);
                    break;
                case RUN:
                    animation.start();
                    animation.setSpeed((float)hero.getCurrentSpeed() / 6F);
                    break;
            }
        }
        previousState = hero.getCurrentState();

        rotate(g, viewX, viewY, viewDegreeAngle, viewWidth, viewHeight, true);
        draw(viewX, viewY);



        // draw mask
        Shape temp = new Circle(gameObject.getMask().getX() + (float) gameObject.getX() - (float) viewX,
                gameObject.getMask().getY() + (float) gameObject.getY() - (float) viewY,
                gameObject.getMask().getBoundingCircleRadius());

        g.draw(temp);
        //--

        rotate(g, viewX, viewY, viewDegreeAngle, viewWidth, viewHeight, false);

        // For debug
        g.drawString("(" + String.valueOf((int) gameObject.getX()) + ";" + String.valueOf((int) gameObject.getY())
                + ") dir=" + String.valueOf((int) (gameObject.getDirection() / Math.PI * 180) % 360),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY));

    }

    private Hero getHero() {
        return (Hero) gameObject;
    }

}