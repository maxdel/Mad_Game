package core.view.gameplay.gameobject;

import core.model.gameplay.gameobjects.Hero;
import core.view.gameplay.Camera;
import org.newdawn.slick.*;

import core.model.gameplay.gameobjects.GameObject;
import core.resourcemanager.ResourceManager;
import core.model.gameplay.gameobjects.Loot;
import core.model.gameplay.World;

public class LootView extends GameObjectView {

    TrueTypeFont ttf;

    public LootView(GameObject loot) {
        super(loot);
        animation = new Animation();
        animation.addFrame(
                ResourceManager.getInstance().getItemImage(((Loot) loot).getItem().getInstanceKind()).getScaledCopy(0.5f), 1);
        ttf = ResourceManager.getInstance().getFont("lootfont");
    }

    public void render(Graphics g, Camera camera)
            throws SlickException {
        rotate(g, camera, true);
        draw(camera);
        rotate(g, camera, false);

        if (Hero.getInstance().getItemToPick() == gameObject) {
            // On the screen without rotation
            double x;
            double y;
            x = gameObject.getX() - camera.getX();
            y = gameObject.getY() - camera.getY();
            // On the screen where (0;0) in center of this screen
            double centredX;
            double centredY;
            centredX = x - camera.getCenterX();
            centredY = y - camera.getCenterY();
            // With rotation around the center (0;0) on viewAngle
            double inViewX;
            double inViewY;
            double viewRagianAngle = camera.getDirection();
            inViewX = centredX * Math.cos(-viewRagianAngle) - centredY * Math.sin(-viewRagianAngle) + camera.getCenterX();
            inViewY = centredX * Math.sin(-viewRagianAngle) + centredY * Math.cos(-viewRagianAngle) + camera.getCenterY();

            Loot loot = (Loot) gameObject;
            String text = String.valueOf(loot.getItem().getInstanceKind().toString());
            if (loot.getNumber() > 1) {
                text = text + "(" + String.valueOf(loot.getNumber()) + ")";
            }
            ttf.drawString((int) (inViewX - ttf.getWidth(text) / 2F),
                    (int) (inViewY - ttf.getHeight(text) / 2F - 18), text);
        }
    }

}