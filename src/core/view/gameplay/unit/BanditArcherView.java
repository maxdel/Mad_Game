package core.view.gameplay.unit;

import core.model.gameplay.gameobjects.Bot;
import core.model.gameplay.gameobjects.ai.Cell;
import core.view.gameplay.Camera;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.model.gameplay.gameobjects.GameObject;
import core.resourcemanager.ResourceManager;

public class BanditArcherView extends UnitView {

    public BanditArcherView(GameObject banditArcher) throws SlickException {
        super(banditArcher);
        animation = ResourceManager.getInstance().getAnimation("bandit_archer");
    }

    @Override
    public void render(Graphics g, Camera camera) throws SlickException {
        super.render(g, camera);
        Bot unit = (Bot) gameObject;
        Cell previousCell = null;
        /*rotate(g, camera, true);
        g.rotate((float) (gameObject.getX() - camera.getX()),
                (float) (gameObject.getY() - camera.getY()),
                (float) (camera.getDirectionDegrees() - unit.getDirection() / Math.PI * 180));*/
        g.rotate((float) camera.getCenterX(), (float) camera.getCenterY(), -camera.getDirectionDegrees());
        for(Cell cell : ((Bot) gameObject).getBotAI().getPath()) {
            if (previousCell != null) {
                g.drawLine((float) (previousCell.x - camera.getX()),
                        (float) (previousCell.y - camera.getY()),
                        (float) (cell.x - camera.getX()),
                        (float) (cell.y - camera.getY()));
            }
            previousCell = cell;
        }
        g.rotate((float) camera.getCenterX(), (float) camera.getCenterY(), camera.getDirectionDegrees());
        /*g.rotate((float) (gameObject.getX() - camera.getX()),
                (float) (gameObject.getY() - camera.getY()),
                - (float) (camera.getDirectionDegrees() - unit.getDirection() / Math.PI * 180));
        rotate(g, camera, false);*/
    }

}