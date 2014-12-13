package core.view.gameplay;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.model.gameplay.gameobjects.GameObject;
import core.model.gameplay.gameobjects.Unit;

public abstract class UnitView extends GameObjectSolidView {

    public UnitView(GameObject unit) {
        super(unit);
    }

    protected void drawHealthbar(Graphics g, int x, int y, int width, int height, double current, double maximum,
                                 Color color) {
        Color tempColor = g.getColor();
        g.setColor(Color.white);
        g.fillRect(x - width / 2, y - height / 2, width, height);
        g.setColor(color);
        g.fillRect(x - width / 2, y - height / 2, width * (float)(current / maximum), height);
        g.setColor(Color.darkGray);
        g.drawRect(x - width / 2, y - height / 2, width, height);
        g.setColor(tempColor);
    }

    @Override
    public void render(Graphics g, Camera camera) throws SlickException {
        Unit unit = (Unit) gameObject;

        rotate(g, camera, true);

        draw(camera);
        animation.draw((float) (gameObject.getX() - camera.getX() - animation.getWidth() / 2),
                (float) (gameObject.getY() - camera.getY() - animation.getHeight() / 2));
        drawMask(g, camera.getX(), camera.getY());

        g.rotate((float) (gameObject.getX() - camera.getX()),
                (float) (gameObject.getY() - camera.getY()),
                (float) (camera.getDirectionDegrees() - unit.getDirection() / Math.PI * 180));
        drawHealthbar(g, (int)(unit.getX() - camera.getX()), (int)(unit.getY() - camera.getY()) - 46, 60, 6,
                unit.getAttribute().getHP().getCurrent(),
                unit.getAttribute().getHP().getMaximum(), Color.red);
        drawHealthbar(g, (int) (unit.getX() - camera.getX()), (int) (unit.getY() - camera.getY()) - 38, 60, 6,
                unit.getAttribute().getMP().getCurrent(),
                unit.getAttribute().getMP().getMaximum(), Color.blue);
        g.rotate((float) (gameObject.getX() - camera.getX()),
                (float) (gameObject.getY() - camera.getY()),
                - (float) (camera.getDirectionDegrees() - unit.getDirection() / Math.PI * 180));

        rotate(g, camera, false);
    }
    
}