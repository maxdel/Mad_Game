package core.view.gameplay;

import core.model.gameplay.GameObject;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class EnemyRenderer extends GameObjectRenderer {

    Image enemyImage;

    public EnemyRenderer(GameObject enemy, Image enemyImage) {
        super(enemy);
        this.enemyImage = enemyImage;
    }

    @Override
    public void render(Graphics g, double viewX, double viewY, double viewDirection, int viewWidth, int viewHeight) {
        float viewDegreeAngle = (float) (viewDirection / Math.PI * 180);

        //Rotate around view center to set position on the View
        g.rotate(viewWidth / 2, viewHeight / 2, - viewDegreeAngle);
        //Rotate around gameObject coordinates to set direction of gameObject
        g.rotate((float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY),
                (float)(gameObject.getDirection() / Math.PI * 180));
        // Coordinates to draw image according to position on the View
        enemyImage.draw((float) (gameObject.getX() - viewX - enemyImage.getWidth() / 2),
                (float) (gameObject.getY() - viewY - enemyImage.getHeight() / 2));
        g.rotate((float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY),
                -(float) (gameObject.getDirection() / Math.PI * 180));
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