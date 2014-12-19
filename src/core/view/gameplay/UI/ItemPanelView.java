package core.view.gameplay.ui;

import core.resourcemanager.ResourceManager;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class ItemPanelView {

    private SpinnerView weaponSpinner;
    private SpinnerView armorSpinner;

    public ItemPanelView(GameContainer gc) {
        this.armorSpinner = new SpinnerView(40,
                new Image[]{ ResourceManager.getInstance().getImage("skill fake"),
                        ResourceManager.getInstance().getImage("skill fake"),
                        ResourceManager.getInstance().getImage("skill fake") }, 3, Math.PI, 200);
        this.weaponSpinner = new SpinnerView(40,
                new Image[]{ ResourceManager.getInstance().getImage("skill fake"),
                        ResourceManager.getInstance().getImage("skill fake"),
                        ResourceManager.getInstance().getImage("skill fake") }, 3, Math.PI, 200);
    }

    public void update(int delta) {
        weaponSpinner.update(delta);
        armorSpinner.update(delta);
    }

    public void render(GameContainer gc, Graphics g) {
        weaponSpinner.render(gc.getWidth() - 70, gc.getHeight() - 70);
        armorSpinner.render(gc.getWidth() - 70, gc.getHeight() - 200);
    }

}