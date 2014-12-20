package core.view.gameplay.ui;

import core.model.gameplay.items.ItemDB;
import core.model.gameplay.items.ItemInstanceKind;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;

import core.model.gameplay.gameobjects.Hero;
import core.model.gameplay.items.Item;
import core.resourcemanager.ResourceManager;

public class ItemView {

    private Image itemSubstrat;
    private Item item;
    private Image image;
    private TrueTypeFont itemNumberFont;

    public ItemView(ItemInstanceKind itemKind) {
        this.itemSubstrat = ResourceManager.getInstance().getImage("item_substrat");
        this.item = ItemDB.getInstance().getItem(itemKind);
        this.image = ResourceManager.getInstance().getItemImage(item.getInstanceKind());
        this.itemNumberFont = ResourceManager.getInstance().getFont("item_number_font");
    }

    public void render(int x, int y) {
        itemSubstrat.draw(x - image.getWidth() / 2 - 40, y - itemSubstrat.getHeight() / 2);
        image.draw(x - image.getWidth() / 2, y - image.getHeight() / 2);
        int itemNumber;
        if (Hero.getInstance().getInventory().getItemRecord(item) != null) {
            itemNumber = Hero.getInstance().getInventory().getItemRecord(item).getNumber();
        } else {
            itemNumber = 0;
        }
        itemNumberFont.drawString(x + 30, y - itemNumberFont.getHeight() / 2, String.valueOf(itemNumber));
    }

}