package core.view.gameplay;

import core.model.gameplay.gameobjects.GameObject;
import core.model.gameplay.gameobjects.GameObjectSolid;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

import core.model.gameplay.CollisionManager;

public abstract class GameObjectSolidView extends GameObjectView {

    public GameObjectSolidView(GameObject gameObject) {
        super(gameObject);
    }

    /*
     * Draws mask around a game object
     * */
    protected void drawMask(Graphics g, double viewX, double viewY) {
        GameObjectSolid gameObjectSolid = (GameObjectSolid) gameObject;
        Shape mask = CollisionManager.getInstance().getUpdatedMask(gameObjectSolid.getMask(),
                (float) (gameObjectSolid.getX() - viewX), (float) (gameObjectSolid.getY() - viewY),
                gameObjectSolid.getDirection());
        g.draw(mask);
    }

}