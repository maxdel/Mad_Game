package core.view.gameplay;

import core.model.gameplay.units.Hero;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.model.gameplay.resource_manager.ResourceManager;
import core.model.gameplay.units.GameObjectSolid;
import org.newdawn.slick.geom.Vector2f;

public class WallView extends GameObjectView {

    public WallView(GameObjectSolid wall, ResourceManager resourceManager) throws SlickException {
        super(wall, resourceManager);
        animation = resourceManager.getAnimation("wall");
    }

    @Override
    public void render(Graphics g, double viewX, double viewY, float viewDegreeAngle,
                       double viewCenterX, double viewCenterY, Hero hero) {
        // TODO fix magic number
        Vector2f v = new Vector2f((float)(hero.getX() - gameObjectSolid.getX()), (float)(hero.getY() - gameObjectSolid.getY()));
        if (v.length() >= 1000) {
            return;
        }


        rotate(g, viewX, viewY, viewDegreeAngle, viewCenterX, viewCenterY, true);
        draw(viewX, viewY);

        // draw mask
        g.rotate((float) (gameObjectSolid.getX() - viewX),
                (float) (gameObjectSolid.getY() - viewY),
                -(float) (gameObjectSolid.getDirection() / Math.PI * 180));
        drawMask(g, viewX, viewY);
        g.rotate((float) (gameObjectSolid.getX() - viewX),
                (float) (gameObjectSolid.getY() - viewY),
                (float) (gameObjectSolid.getDirection() / Math.PI * 180));

        // ----- For debug and FUN -----
        /*g.rotate((float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY),
                viewDegreeAngle);
        g.drawString("(" + String.valueOf(gameObject.getX()) + ";" + String.valueOf(gameObject.getY()) + ")",
                (float) (gameObject.getX() - viewX), (float) (gameObject.getY() - viewY));
        g.rotate((float) (gameObject.getX() - viewX),
                (float) (gameObject.getY() - viewY),
                - viewDegreeAngle);*/

        // ----- END -----
        rotate(g, viewX, viewY, viewDegreeAngle, viewCenterX, viewCenterY, false);
    }

}