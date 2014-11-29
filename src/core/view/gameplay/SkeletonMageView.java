package core.view.gameplay;

import core.model.gameplay.*;
import org.newdawn.slick.*;

import core.ResourceManager;

public class SkeletonMageView extends GameObjectView {

    public SkeletonMageView(GameObject enemy, ResourceManager resourceManager) throws SlickException {
        super(enemy, resourceManager);
        animation = resourceManager.getAnimation("skeletonMage");
    }

    @Override
    public void render(Graphics g, double viewX, double viewY, float viewDegreeAngle, double viewCenterX, double viewCenterY, Hero hero) {
        SkeletonMage skeletonMage = (SkeletonMage) gameObject;

        rotate(g, viewX, viewY, viewDegreeAngle, viewCenterX, viewCenterY, true);
        draw(viewX, viewY);
        // draw mask
        drawMask(g, viewX, viewY);

        // ----- For debug and FUN -----
        g.rotate((float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY),
                (float) (viewDegreeAngle - skeletonMage.getDirection() / Math.PI * 180));
        drawHealthbar(g, (int)(skeletonMage.getX() - viewX), (int)(skeletonMage.getY() - viewY) - 50, 60, 8, skeletonMage.getAttribute().getCurrentHP(),
                skeletonMage.getAttribute().getMaximumHP(), Color.red);
        drawHealthbar(g, (int) (skeletonMage.getX() - viewX), (int) (skeletonMage.getY() - viewY) - 38, 60, 8, skeletonMage.getAttribute().getCurrentMP(),
                skeletonMage.getAttribute().getMaximumMP(), Color.blue);
        g.rotate((float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY),
                - (float) (viewDegreeAngle - skeletonMage.getDirection() / Math.PI * 180));
        // ----- END -----

        rotate(g, viewX, viewY, viewDegreeAngle, viewCenterX, viewCenterY, false);
    }

}