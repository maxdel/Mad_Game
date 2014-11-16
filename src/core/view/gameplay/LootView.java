package core.view.gameplay;

import core.ResourceManager;
import core.model.gameplay.Loot;
import core.model.gameplay.World;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class LootView {

    private Loot loot;
    private Image image;

    protected LootView(Loot loot) {
        this.loot = loot;
        image = ResourceManager.getInstance().getItemImage(loot.getItem().getName());
    }

    public void render(Graphics g, double viewX, double viewY, float viewDegreeAngle, int viewWidth, int viewHeight)
            throws SlickException {
        rotate(g, viewX, viewY, viewDegreeAngle, viewWidth, viewHeight, true);
        draw(viewX, viewY);
        rotate(g, viewX, viewY, viewDegreeAngle, viewWidth, viewHeight, false);
    }

    public void rotate(Graphics g, final double viewX, final double viewY, final float viewDegreeAngle,
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

    public void draw(final double viewX, final double viewY) {
        if (World.getInstance().getHero().getSelectedLoot() == loot) {
            ResourceManager.getInstance().getImage("Selected loot").draw((float) (loot.getX() - viewX - image.getWidth() / 2),
                    (float) (loot.getY() - viewY - image.getHeight() / 2), 0.5F);
        }
        image.draw((float) (loot.getX() - viewX - image.getWidth() / 2),
                (float) (loot.getY() - viewY - image.getHeight() / 2), 0.5F);
    }

    protected Loot getLoot() {
        return loot;
    }

}