package core.view.gameplay;

import core.model.gameplay.units.Hero;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.resourcemanager.ResourceManager;
import core.model.gameplay.units.Obstacle;
import org.newdawn.slick.geom.Vector2f;

public class WallView extends GameObjectView {

    public WallView(Obstacle wall, ResourceManager resourceManager) throws SlickException {
        super(wall, resourceManager);
        animation = resourceManager.getAnimation("wall");
    }

    @Override
    public void render(Graphics g, double viewX, double viewY, float viewDegreeAngle,
                       double viewCenterX, double viewCenterY, Hero hero) {
        // TODO fix magic number
        Vector2f v = new Vector2f((float)(hero.getX() - obstacle.getX()), (float)(hero.getY() - obstacle.getY()));
        if (v.length() >= 1000) {
            return;
        }


        rotate(g, viewX, viewY, viewDegreeAngle, viewCenterX, viewCenterY, true);
        draw(viewX, viewY);

        // draw mask
        g.rotate((float) (obstacle.getX() - viewX),
                (float) (obstacle.getY() - viewY),
                -(float) (obstacle.getDirection() / Math.PI * 180));
        drawMask(g, viewX, viewY);
        g.rotate((float) (obstacle.getX() - viewX),
                (float) (obstacle.getY() - viewY),
                (float) (obstacle.getDirection() / Math.PI * 180));

        // ----- For debug and FUN -----
        /*g.rotate((float) (gameObjectSolid.getX() - viewX),
                (float) (gameObjectSolid.getY() - viewY),
                viewDegreeAngle);
        g.drawString("(" + String.valueOf(gameObjectSolid.getX()) + ";" + String.valueOf(gameObjectSolid.getY()) + ")",
                (float) (gameObjectSolid.getX() - viewX), (float) (gameObjectSolid.getY() - viewY));
        g.rotate((float) (gameObjectSolid.getX() - viewX),
                (float) (gameObjectSolid.getY() - viewY),
                - viewDegreeAngle);*/

        // ----- END -----
        rotate(g, viewX, viewY, viewDegreeAngle, viewCenterX, viewCenterY, false);
    }

}