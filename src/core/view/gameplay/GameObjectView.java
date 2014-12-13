package core.view.gameplay;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.MathAdv;
import core.model.gameplay.World;
import core.model.gameplay.gameobjects.GameObject;
import core.model.gameplay.gameobjects.Hero;

public abstract class GameObjectView {

    protected GameObject gameObject;
    protected Animation animation;

    private final int DRAW_RADIUS = 900;

    public GameObjectView(GameObject gameObject) {
        this.gameObject = gameObject;
        this.animation = null;
    }

    public void render(Graphics g, Camera camera) throws SlickException {
        Hero hero = World.getInstance().getHero();
        if (MathAdv.getDistance(gameObject.getX(), gameObject.getY(), hero.getX(), hero.getY()) < DRAW_RADIUS) {
            rotate(g, camera, true);
            draw(camera);
            rotate(g, camera, false);
        }
    }

    public void rotate(Graphics g, Camera camera, boolean isFront) {
        if (isFront) {
            g.rotate((float) camera.getCenterX(), (float) camera.getCenterY(), - camera.getDirectionDegrees());
            g.rotate((float) (gameObject.getX() - camera.getX()),
                    (float) (gameObject.getY() - camera.getY()),
                    (float) (gameObject.getDirection() / Math.PI * 180));
        } else {
            g.rotate((float) (gameObject.getX() - camera.getX()),
                    (float) (gameObject.getY() - camera.getY()),
                    (float) - (gameObject.getDirection() / Math.PI * 180));
            g.rotate((float) camera.getCenterX(), (float) camera.getCenterY(), camera.getDirectionDegrees());
        }
    }

    public void draw(Camera camera) {
        animation.draw((float) (gameObject.getX() - camera.getX() - animation.getWidth() / 2),
                (float) (gameObject.getY() - camera.getY() - animation.getHeight() / 2));
    }
    
}