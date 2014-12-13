package core.view.gameplay.gameobjectsolid;

import core.model.gameplay.gameobjects.GameObject;
import core.model.gameplay.gameobjects.GameObjectSolid;
import core.view.gameplay.gameobject.GameObjectView;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

import core.model.gameplay.CollisionManager;

public abstract class GameObjectSolidView extends GameObjectView {

    public GameObjectSolidView(GameObject gameObject) {
        super(gameObject);
    }

    /**
     * Draws mask around a game object
     */
    protected void drawMask(Graphics g, double cameraX, double cameraY) {
        GameObjectSolid gameObjectSolid = (GameObjectSolid) gameObject;
        Shape mask = CollisionManager.getInstance().getUpdatedMask(gameObjectSolid.getMask(),
                (float) (gameObjectSolid.getX() - cameraX), (float) (gameObjectSolid.getY() - cameraY),
                gameObjectSolid.getDirection());
        g.draw(mask);
    }

}