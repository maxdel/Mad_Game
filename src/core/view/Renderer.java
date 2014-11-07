package core.view;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.model.GameObject;
import core.model.Hero;
import core.model.Wall;

public class Renderer {

    private ArrayList<GameObjectRenderer> gameObjectRenderers;

    private int mouseX, mouseY;
    private int oldMouseX, oldMouseY;

    private double globalRenderAngle;

    public Renderer(final ArrayList<GameObject> gameObjects) throws SlickException {
        gameObjectRenderers = new ArrayList<GameObjectRenderer>();
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Hero) {
                gameObjectRenderers.add(new HeroRenderer((Hero) gameObject));
            } else if (gameObject instanceof Wall) {
                gameObjectRenderers.add(new WallRenderer((Wall) gameObject));
            }
        }

        globalRenderAngle = 0;
    }

    public void render(Graphics g) throws SlickException {
        float x = 0, y = 0;
        for (GameObjectRenderer gameObjectRenderer : gameObjectRenderers) {
            if (gameObjectRenderer instanceof HeroRenderer) {
                //globalRenderAngle = gameObjectRenderer.gameObject.getDirection();
                x = (float) gameObjectRenderer.gameObject.getX();
                y = (float) gameObjectRenderer.gameObject.getY();
            }
        }

        globalRenderAngle += (mouseX - oldMouseX);

        float rotateAngle = (float) (globalRenderAngle / Math.PI * 180);

        g.rotate(x, y, rotateAngle);
        for (GameObjectRenderer gameObjectRenderer : gameObjectRenderers) {
            if (gameObjectRenderer instanceof HeroRenderer) {
                g.rotate(x, y, -rotateAngle);
                gameObjectRenderer.render(g);
                g.rotate(x, y, rotateAngle);
            } else {
                gameObjectRenderer.render(g);
            }
        }
        g.rotate(x, y, -rotateAngle);
    }

    public void setMouseX(int mouseX) {
        oldMouseX = this.mouseX;
        this.mouseX = mouseX;
    }

    public void setMouseY(int mouseY) {
        oldMouseY = this.mouseY;
        this.mouseY = mouseY;
    }
}