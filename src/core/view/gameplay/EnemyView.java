package core.view.gameplay;

import core.model.gameplay.Enemy;
import org.newdawn.slick.*;

import core.model.gameplay.GameObject;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

public class EnemyView extends GameObjectView {

    public EnemyView(GameObject enemy) throws SlickException {
        super(enemy);

        setAnimation("/res/Enemy.png");
    }

    @Override
    public void render(Graphics g, double viewX, double viewY, float viewDegreeAngle, int viewWidth, int viewHeight) {
        rotate(g, viewX, viewY, viewDegreeAngle, viewWidth, viewHeight, true);
        draw(viewX, viewY);

        // draw mask
        Shape temp = new Circle(gameObject.getMask().getX() + (float) gameObject.getX() - (float) viewX,
                gameObject.getMask().getY() + (float) gameObject.getY() - (float) viewY,
                gameObject.getMask().getBoundingCircleRadius());

        g.draw(temp);
        // --

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