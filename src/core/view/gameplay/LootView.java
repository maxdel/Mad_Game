package core.view.gameplay;

import org.newdawn.slick.*;

import core.model.gameplay.gameobjects.GameObject;
import core.resourcemanager.ResourceManager;
import core.model.gameplay.gameobjects.Loot;
import core.model.gameplay.World;

public class LootView extends GameObjectView {

    TrueTypeFont ttf;

    protected LootView(GameObject loot) {
        super(loot);
        animation = new Animation((SpriteSheet) ResourceManager.getInstance().getItemImage(((Loot) loot).getItem().getName()), 1);
        ttf = ResourceManager.getInstance().getFont("lootfont");
    }

    public void render(Graphics g, Camera camera)
            throws SlickException {
        rotate(g, camera, true);
        draw(camera);
        rotate(g, camera, false);

        if (World.getInstance().getHero().getItemToPick() == gameObject) {
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
            String text = String.valueOf(loot.getItem().getName());
            if (loot.getNumber() > 1) {
                text = text + "(" + String.valueOf(loot.getNumber()) + ")";
            }
            ttf.drawString((int) (inViewX - ttf.getWidth(text) / 2F),
                    (int) (inViewY - ttf.getHeight(text) / 2F - 18), text);
        }
    }

}