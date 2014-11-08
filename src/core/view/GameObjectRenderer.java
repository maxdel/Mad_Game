package core.view;

import org.newdawn.slick.Graphics;

import core.model.GameObject;

public abstract class GameObjectRenderer {

    protected GameObject gameObject;

    public GameObjectRenderer(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public abstract void render(Graphics g);

    public abstract void render(Graphics g, final double viewX, final double viewY, final double viewDirection);

}