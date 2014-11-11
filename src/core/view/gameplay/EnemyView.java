package core.view.gameplay;

import core.model.gameplay.Enemy;
import org.newdawn.slick.*;

import core.model.gameplay.GameObject;

public class EnemyView extends GameObjectView {

    public EnemyView(GameObject enemy) throws SlickException {
        super(enemy);

        Image image = new Image("/res/Enemy.png");
        SpriteSheet spriteSheet = new SpriteSheet(image, image.getWidth(), image.getHeight());
        animation = new Animation(spriteSheet, 1);
    }

    @Override
    public void render(Graphics g, double viewX, double viewY, float viewDegreeAngle, int viewWidth, int viewHeight) {
        rotate(g, viewX, viewY, viewDegreeAngle, viewWidth, viewHeight, true);
        draw(viewX, viewY);
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