package core.view.gameplay;

import core.model.gameplay.Hero;
import org.newdawn.slick.*;

import core.ResourceManager;
import core.model.gameplay.GameObject;

public class EnemyView extends GameObjectView {

    public EnemyView(GameObject enemy, ResourceManager resourceManager) throws SlickException {
        super(enemy, resourceManager);
        animation = resourceManager.getAnimation("enemy");
    }

    @Override
    public void render(Graphics g, double viewX, double viewY, float viewDegreeAngle, int viewWidth, int viewHeight, Hero hero) {
        rotate(g, viewX, viewY, viewDegreeAngle, viewWidth, viewHeight, true);
        draw(viewX, viewY);

        // draw mask
        drawMask(g, viewX, viewY);

        // ----- For debug and FUN -----
        g.rotate((float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY),
                viewDegreeAngle);
        g.drawString("(" + String.valueOf((int)gameObject.getX()) + ";" + String.valueOf((int)gameObject.getY()) + ")",
                (float) (gameObject.getX() - viewX), (float) (gameObject.getY() - viewY));
        g.rotate((float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY),
                - viewDegreeAngle);
        // ----- END -----

        rotate(g, viewX, viewY, viewDegreeAngle, viewWidth, viewHeight, false);
    }

}