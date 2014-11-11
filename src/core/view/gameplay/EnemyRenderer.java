package core.view.gameplay;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import core.model.gameplay.GameObject;

public class EnemyRenderer extends GameObjectRenderer {

    Image enemyImage;

    public EnemyRenderer(GameObject enemy, Image enemyImage) {
        super(enemy);
        this.enemyImage = enemyImage;
    }

    @Override
    public void render(Graphics g, double viewX, double viewY, float viewDegreeAngle, int viewWidth, int viewHeight) {
        super.rotate(g, viewX, viewY, viewDegreeAngle, viewWidth, viewHeight,
                enemyImage, enemyImage.getWidth(), enemyImage.getHeight());
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
        g.rotate(viewWidth / 2, viewHeight / 2, viewDegreeAngle);
    }

}