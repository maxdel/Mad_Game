package core.view.gameplay;

import core.model.gameplay.Bandit;
import core.model.gameplay.Hero;
import org.newdawn.slick.*;

import core.ResourceManager;
import core.model.gameplay.GameObject;

public class BanditView extends GameObjectView {

    public BanditView(GameObject enemy, ResourceManager resourceManager) throws SlickException {
        super(enemy, resourceManager);
        animation = resourceManager.getAnimation("bandit");
    }

    @Override
    public void render(Graphics g, double viewX, double viewY, float viewDegreeAngle, double viewCenterX, double viewCenterY, Hero hero) {
        Bandit bandit = (Bandit) gameObject;

        rotate(g, viewX, viewY, viewDegreeAngle, viewCenterX, viewCenterY, true);
        draw(viewX, viewY);
        // draw mask
        drawMask(g, viewX, viewY);

        // ----- For debug and FUN -----
        g.rotate((float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY),
                (float) (viewDegreeAngle - bandit.getDirection() / Math.PI * 180));
        drawHealthbar(g, (int)(bandit.getX() - viewX), (int)(bandit.getY() - viewY) - 50, 60, 8, bandit.getAttribute().getCurrentHP(),
                bandit.getAttribute().getMaximumHP(), Color.red);
        drawHealthbar(g, (int) (bandit.getX() - viewX), (int) (bandit.getY() - viewY) - 38, 60, 8, bandit.getAttribute().getCurrentMP(),
                bandit.getAttribute().getMaximumMP(), Color.blue);
        g.drawString(String.valueOf((int) bandit.getAttribute().getPAttack()) + "/" +
                        String.valueOf((int) bandit.getAttribute().getMAttack()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 80));
        g.drawString(String.valueOf((int) bandit.getAttribute().getPArmor()) + "/" +
                        String.valueOf((int) bandit.getAttribute().getMArmor()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 60));
        g.drawString(String.valueOf((int) bandit.getAttribute().getCurrentHP()) + "/" +
                        String.valueOf((int) bandit.getAttribute().getMaximumHP()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 40));
        g.drawString(String.valueOf((int) bandit.getAttribute().getCurrentMP()) + "/" +
                        String.valueOf((int) bandit.getAttribute().getMaximumMP()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 20));
        g.drawString("(" + String.valueOf((int) gameObject.getX()) + ";" + String.valueOf((int) gameObject.getY()) + ")",
                (float) (gameObject.getX() - viewX), (float) (gameObject.getY() - viewY));
        g.rotate((float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY),
                - (float) (viewDegreeAngle - bandit.getDirection() / Math.PI * 180));
        // ----- END -----

        rotate(g, viewX, viewY, viewDegreeAngle, viewCenterX, viewCenterY, false);
    }

}