package core.view;

import core.model.GameObject;
import org.newdawn.slick.Graphics;

public abstract class GameObjectRepresentation {

    protected GameObject gameObject;

    public GameObjectRepresentation(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public abstract void render(Graphics g);

}
