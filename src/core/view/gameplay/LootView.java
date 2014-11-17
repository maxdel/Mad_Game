package core.view.gameplay;

import core.ResourceManager;
import core.model.gameplay.Loot;
import core.model.gameplay.World;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import java.awt.Font;

public class LootView {

    private Loot loot;
    private Image image;
    TrueTypeFont ttf;

    protected LootView(Loot loot) {
        this.loot = loot;
        image = ResourceManager.getInstance().getItemImage(loot.getItem().getName());
        Font font = new Font("sfd", Font.PLAIN, 10);
        ttf = new TrueTypeFont(font, true);
    }

    public void render(Graphics g, double viewX, double viewY, float viewDegreeAngle, int viewWidth, int viewHeight)
            throws SlickException {
        rotate(g, viewX, viewY, viewDegreeAngle, viewWidth, viewHeight, true);
        draw(viewX, viewY, g);

        /*// ----- For debug and FUN -----
        if (World.getInstance().getHero().getSelectedLoot() == loot) {
            g.rotate((float) (loot.getX() - viewX),
                    (float) (loot.getY() - viewY),
                    (float) (viewDegreeAngle - loot.getDirection() / Math.PI * 180));
            String text = String.valueOf(loot.getItem().getName());
            ttf.drawString((float) (loot.getX() - viewX - ttf.getWidth(text) / 2F),
                    (float) (loot.getY() - viewY - ttf.getHeight(text) / 2F - 18), text);
            g.rotate((float) (loot.getX() - viewX),
                    (float) (loot.getY() - viewY),
                    (float) (- viewDegreeAngle + loot.getDirection() / Math.PI * 180));
        }
        // ----- END -----*/
        rotate(g, viewX, viewY, viewDegreeAngle, viewWidth, viewHeight, false);

        if (World.getInstance().getHero().getSelectedLoot() == loot) {
            // On the screen without rotation
            double x;
            double y;
            x = loot.getX() - viewX;
            y = loot.getY() - viewY;
            // On the screen where (0;0) in center of this screen
            double centredX;
            double centredY;
            centredX = x - viewWidth / 2;
            centredY = y - viewHeight / 2;
            // With rotation around the center (0;0) on viewAngle
            double inViewX;
            double inViewY;
            double viewRagianAngle = viewDegreeAngle / 180 * Math.PI;
            inViewX = centredX * Math.cos(-viewRagianAngle) - centredY * Math.sin(-viewRagianAngle) + viewWidth / 2;
            inViewY = centredX * Math.sin(-viewRagianAngle) + centredY * Math.cos(-viewRagianAngle) + viewHeight / 2;

            String text = String.valueOf(loot.getItem().getName());
            if (loot.getNumber() > 1) {
                text = text + "(" + String.valueOf(loot.getNumber()) + ")";
            }
            ttf.drawString((int) (inViewX - ttf.getWidth(text) / 2F),
                    (int) (inViewY - ttf.getHeight(text) / 2F - 18), text);
        }
    }

    private void rotate(Graphics g, final double viewX, final double viewY, final float viewDegreeAngle,
                       final int viewWidth, final int viewHeight, final boolean isFront) {
        if (isFront) {
            g.rotate(viewWidth / 2, viewHeight / 2, - viewDegreeAngle);
            g.rotate((float) (loot.getX() - viewX),
                    (float) (loot.getY() - viewY),
                    (float)(loot.getDirection() / Math.PI * 180));
        } else {
            g.rotate((float) (loot.getX() - viewX),
                    (float) (loot.getY() - viewY),
                    (float) - (loot.getDirection() / Math.PI * 180));
            g.rotate(viewWidth / 2, viewHeight / 2, viewDegreeAngle);
        }
    }

    private void draw(double viewX, double viewY, Graphics g) {
        if (World.getInstance().getHero().getSelectedLoot() == loot) {
            ResourceManager.getInstance().getImage("Selected loot").draw((float) (loot.getX() - viewX - image.getWidth() / 4),
                    (float) (loot.getY() - viewY - image.getHeight() / 4), 0.5F);
        }
        image.draw((float) (loot.getX() - viewX - image.getWidth() / 4),
                (float) (loot.getY() - viewY - image.getHeight() / 4), 0.5F);
    }

    protected Loot getLoot() {
        return loot;
    }

}