package core.view.gameplay;

import core.model.gameplay.Bandit;
import core.model.gameplay.BanditArcher;
import core.model.gameplay.Hero;
import org.newdawn.slick.*;

import core.ResourceManager;
import core.model.gameplay.GameObject;

public class BanditArcherView extends GameObjectView {

    public BanditArcherView(GameObject enemy, ResourceManager resourceManager) throws SlickException {
        super(enemy, resourceManager);
        animation = resourceManager.getAnimation("banditArcher");
    }

    @Override
    public void render(Graphics g, double viewX, double viewY, float viewDegreeAngle, double viewCenterX, double viewCenterY, Hero hero) {
        BanditArcher banditArcher = (BanditArcher) gameObject;

        rotate(g, viewX, viewY, viewDegreeAngle, viewCenterX, viewCenterY, true);
        draw(viewX, viewY);
        // draw mask
        drawMask(g, viewX, viewY);

        // ----- For debug and FUN -----
        g.rotate((float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY),
                (float) (viewDegreeAngle - banditArcher.getDirection() / Math.PI * 180));
        drawHealthbar(g, (int)(banditArcher.getX() - viewX), (int)(banditArcher.getY() - viewY) - 50, 60, 8, banditArcher.getAttribute().getCurrentHP(),
                banditArcher.getAttribute().getMaximumHP(), Color.red);
        drawHealthbar(g, (int) (banditArcher.getX() - viewX), (int) (banditArcher.getY() - viewY) - 38, 60, 8, banditArcher.getAttribute().getCurrentMP(),
                banditArcher.getAttribute().getMaximumMP(), Color.blue);
        /*g.drawString(String.valueOf((int) banditArcher.getAttribute().getPAttack()) + "/" +
                        String.valueOf((int) banditArcher.getAttribute().getMAttack()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 80));
        g.drawString(String.valueOf((int) banditArcher.getAttribute().getPArmor()) + "/" +
                        String.valueOf((int) banditArcher.getAttribute().getMArmor()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 60));
        g.drawString(String.valueOf((int) banditArcher.getAttribute().getCurrentHP()) + "/" +
                        String.valueOf((int) banditArcher.getAttribute().getMaximumHP()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 40));
        g.drawString(String.valueOf((int) banditArcher.getAttribute().getCurrentMP()) + "/" +
                        String.valueOf((int) banditArcher.getAttribute().getMaximumMP()),
                (float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY - 20));
        g.drawString("(" + String.valueOf((int) gameObject.getX()) + ";" + String.valueOf((int) gameObject.getY()) + ")",
                (float) (gameObject.getX() - viewX), (float) (gameObject.getY() - viewY));*/
        g.rotate((float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY),
                - (float) (viewDegreeAngle - banditArcher.getDirection() / Math.PI * 180));
        // ----- END -----

        rotate(g, viewX, viewY, viewDegreeAngle, viewCenterX, viewCenterY, false);
    }

}