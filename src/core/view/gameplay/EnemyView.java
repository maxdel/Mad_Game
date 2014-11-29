package core.view.gameplay;

import core.model.gameplay.Enemy;
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
    public void render(Graphics g, double viewX, double viewY, float viewDegreeAngle, double viewCenterX, double viewCenterY, Hero hero) {
        Enemy enemy = (Enemy) gameObject;

        rotate(g, viewX, viewY, viewDegreeAngle, viewCenterX, viewCenterY, true);
        draw(viewX, viewY);
        // draw mask
        drawMask(g, viewX, viewY);

        // ----- For debug and FUN -----
        g.rotate((float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY),
                (float) (viewDegreeAngle - enemy.getDirection() / Math.PI * 180));
        drawHealthbar(g, (int)(enemy.getX() - viewX), (int)(enemy.getY() - viewY) - 50, 60, 8, enemy.getAttribute().getCurrentHP(),
                enemy.getAttribute().getMaximumHP(), Color.red);
        drawHealthbar(g, (int) (enemy.getX() - viewX), (int) (enemy.getY() - viewY) - 38, 60, 8, enemy.getAttribute().getCurrentMP(),
                enemy.getAttribute().getMaximumMP(), Color.blue);
        g.drawString(String.valueOf((int) enemy.getAttribute().getPAttack()) + "/" +
                        String.valueOf((int) enemy.getAttribute().getMAttack()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 80));
        g.drawString(String.valueOf((int) enemy.getAttribute().getPArmor()) + "/" +
                        String.valueOf((int) enemy.getAttribute().getMArmor()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 60));
        g.drawString(String.valueOf((int) enemy.getAttribute().getCurrentHP()) + "/" +
                        String.valueOf((int) enemy.getAttribute().getMaximumHP()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 40));
        g.drawString(String.valueOf((int) enemy.getAttribute().getCurrentMP()) + "/" +
                        String.valueOf((int) enemy.getAttribute().getMaximumMP()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 20));
        g.drawString("(" + String.valueOf((int) gameObject.getX()) + ";" + String.valueOf((int) gameObject.getY()) + ")",
                (float) (gameObject.getX() - viewX), (float) (gameObject.getY() - viewY));
        g.rotate((float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY),
                - (float) (viewDegreeAngle - enemy.getDirection() / Math.PI * 180));
        // ----- END -----

        rotate(g, viewX, viewY, viewDegreeAngle, viewCenterX, viewCenterY, false);
    }

}