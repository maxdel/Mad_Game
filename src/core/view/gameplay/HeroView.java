package core.view.gameplay;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.ResourceManager;
import core.model.gameplay.GameObject;
import core.model.gameplay.Hero;
import core.model.gameplay.GameObjectState;

/**
 * Provides functions for the definition of actors, as well as actor
 * operations, such as `receive`, `react`, `reply`, etc.
 */
public class HeroView extends GameObjectView {

    private GameObjectState previousState;

    public HeroView(GameObject hero, ResourceManager resourceManager) throws SlickException {
        super(hero, resourceManager);
        animation = ResourceManager.getInstance().getAnimation("hero");
    }

    @Override
    public void render(Graphics g, double viewX, double viewY, float viewDegreeAngle,
                       double viewCenterX, double viewCenterY, Hero hero1) throws SlickException {
        Hero hero = getHero();
        if (hero.getCurrentState() != previousState) {
            switch (hero.getCurrentState()) {
                case WALK:
                    animation.start();
                    animation.setSpeed((float) (hero.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("hero")));
                    break;
                case STAND:
                    animation.stop();
                    animation.setCurrentFrame(4);
                    break;
                case RUN:
                    animation.start();
                    animation.setSpeed((float) (hero.getAttribute().getCurrentSpeed() / ResourceManager.getInstance().getSpeedCoef("hero")));
                    break;
                case PICK_ITEM:
                    animation.start();
                    break;
            }
        }
        previousState = hero.getCurrentState();

        rotate(g, viewX, viewY, viewDegreeAngle, viewCenterX, viewCenterY, true);
        draw(viewX, viewY);

        // draw mask
        drawMask(g, viewX, viewY);

        rotate(g, viewX, viewY, viewDegreeAngle, viewCenterX, viewCenterY, false);

        // For debug
        g.drawString(String.valueOf((int) hero.getAttribute().getPAttack()) + "/" +
                        String.valueOf((int) hero.getAttribute().getMAttack()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 80));
        g.drawString(String.valueOf((int) hero.getAttribute().getPArmor()) + "/" +
                        String.valueOf((int) hero.getAttribute().getMArmor()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 60));
        g.drawString(String.valueOf((int) hero.getAttribute().getCurrentHP()) + "/" +
                        String.valueOf((int) hero.getAttribute().getMaximumHP()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 40));
        g.drawString(String.valueOf((int) hero.getAttribute().getCurrentMP()) + "/" +
                        String.valueOf((int) hero.getAttribute().getMaximumMP()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 20));
        g.drawString("(" + String.valueOf((int) gameObject.getX()) + ";" + String.valueOf((int) gameObject.getY())
                        + ") dir=" + String.valueOf((int) (gameObject.getDirection() / Math.PI * 180) % 360),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY));
    }

    private Hero getHero() {
        return (Hero) gameObject;
    }

}